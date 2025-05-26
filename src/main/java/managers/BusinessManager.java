package managers;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import daos.PlayerDAOImpl;
import database.MongoDBConnection;
import dtos.PlayerDTO;
import dtos.RoomDTO;
import entities.Player;
import entities.Ticket;
import entities.enums.Difficulty;
import lombok.Getter;
import org.bson.Document;
import utils.ValidateInputs;

import java.util.ArrayList;
import java.util.List;

public class BusinessManager {

    PlayerDAOImpl playerDAO = new PlayerDAOImpl();
    MongoCollection<Document> playersCollection = MongoDBConnection.getPlayersCollection();
    RoomManager roomManager = new RoomManager();
    @Getter
    public int totalSales;

    private void sellTicket(RoomDTO room, Player player) {
        Difficulty difficulty = room.getDifficulty();
        Ticket ticket = new Ticket(difficulty.getPriceByDifficulty());
        player.addTicket(ticket);
        playerDAO.update(player);
        totalSales += ticket.getPrice();
    }

    public Player createPlayer(String name) {
        String email = ValidateInputs.validateString("Enter a valid email: ");
        Player player = new Player(name, email);

        playerDAO.save(player);
        return player;
    }

    public void processSale(Player player) {
        List<RoomDTO> rooms = roomManager.getAllRoomsDTO();
        int roomChoice = roomManager.ChosenDTORoom("play");
        if (roomChoice == 0) {
            System.out.println("Going back...");
        } else {
            RoomDTO room = rooms.get(roomChoice - 1);
            sellTicket(room, player);
            TicketPrinter.printTicket(player, room);
        }
    }

    public Player selectPlayer() {
        PlayerDAOImpl playerDAOImpl = new PlayerDAOImpl();
        List<Player> players = playerDAOImpl.findAll();
        String name = ValidateInputs.validateString("Enter the name of the player: ");
        for (Player player : players) {
            if (name.equals(player.getName())) {return player;}
        }
        return createPlayer(name);
    }
//
//    public List<PlayerDTO> getAllPlayersDTO() {
//        List<PlayerDTO> players = new ArrayList<>();
//        try (MongoCursor<Document> cursor = playersCollection
//                .find()
//                .projection(Projections.include("name"))
//                .iterator()) {
//            while (cursor.hasNext()) {
//                Document doc = cursor.next();
//                players.add(new PlayerDTO(
//                        doc.getObjectId("_id"),
//                        doc.getString("name")));
//            }
//        } catch (MongoException e) {
//            System.out.println("Database error: " + e.getMessage());
//        } catch (IndexOutOfBoundsException e) {
//            System.out.println("Invalid index selected.");
//        } catch (Exception e) {
//            System.out.println("Unexpected error: " + e.getMessage());
//        }
//        return players;
//    }
//
//    public int ChosenDTOPlayer() {
//        List<PlayerDTO> players = getAllPlayersDTO();
//        if (players.isEmpty()) {
//            System.out.println("There are no registered players");
//            return 0;
//        } else {
//            for (int i = 0; i < players.size(); i++) {
//                System.out.println((i + 1) + ". " + players.get(i).getName());
//            }
//            System.out.println("0. Go back");
//            return ValidateInputs.validateIntegerBetweenOnRange(
//                    "Choose the player you want to sell ticket to: ", 0, players.size()
//            );
//        }
//    }

}
