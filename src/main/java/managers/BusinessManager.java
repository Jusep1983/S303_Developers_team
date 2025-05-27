package managers;

import daos.PlayerDAOImpl;
import dtos.RoomDTO;
import entities.Player;
import entities.Ticket;
import entities.enums.Difficulty;
import utils.ValidateInputs;

import java.util.List;

public class BusinessManager {

    PlayerDAOImpl playerDAO = new PlayerDAOImpl();
    RoomManager roomManager = new RoomManager();

    private void sellTicket(RoomDTO room, Player player) {
        Difficulty difficulty = room.getDifficulty();
        Ticket ticket = new Ticket(difficulty.getPriceByDifficulty());
        player.addTicket(ticket);
        playerDAO.update(player);
    }

    public Player createPlayer(String name) {
        String email = ValidateInputs.validateString("Enter a valid email: ");
        Player player = new Player(name, email);

        playerDAO.save(player);
        return player;
    }

    public void processSale(Player player) {
        RoomDTO room = roomManager.getRoomDTO("play");
        sellTicket(room, player);
        TicketPrinter.printTicket(player, room);
    }

    public Player selectOrCreatePlayer() {
        PlayerDAOImpl playerDAOImpl = new PlayerDAOImpl();
        List<Player> players = playerDAOImpl.findAll();
        String name = ValidateInputs.validateString("Enter the name of the player: ");
        for (Player player : players) {
            if (name.equals(player.getName())) {return player;}
        }
        return createPlayer(name);
    }

    public Player selectPlayer() {
        PlayerDAOImpl playerDAOImpl = new PlayerDAOImpl();
        List<Player> players = playerDAOImpl.findAll();
        String name = ValidateInputs.validateString("Enter the name of the player: ");
        for (Player player : players) {
            if (name.equals(player.getName())) {
                return player;
            }
        }
        System.out.println("Player does not exist.");
        return selectPlayer();
    }
}