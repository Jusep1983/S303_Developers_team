package managers;

import daos.PlayerDAOImpl;
import daos.RoomDAOImpl;
import dtos.RoomDTO;
import entities.Player;
import entities.Room;
import entities.Ticket;
import entities.enums.Difficulty;
import lombok.Getter;
import utils.ValidateInputs;

import java.util.List;

public class BusinessManager {

    PlayerDAOImpl playerDAO = new PlayerDAOImpl();
    RoomDAOImpl roomDAO = new RoomDAOImpl();
    RoomManager roomManager = new RoomManager();
    @Getter
    public int totalSales;

    private void sellTicket(Room room, Player player) {
        Difficulty difficulty = room.getDifficulty();
        Ticket ticket = new Ticket(difficulty.getPriceByDifficulty());
        player.addTicket(ticket);
        playerDAO.update(player);
        totalSales += ticket.getPrice();
    }

    public Player createPlayer() {
        String name = ValidateInputs.validateString("Enter the name of the player: ");
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
            RoomDTO roomDTO = rooms.get(roomChoice - 1);
            Room room = roomDAO.findById(roomDTO.getId());
            sellTicket(room, player);
            TicketPrinter.printTicket(player, room);
        }
    }

}
