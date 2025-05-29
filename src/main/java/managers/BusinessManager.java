package managers;

import daos.interfaces.PlayerDAO;
import dtos.RoomDTO;
import entities.Player;
import entities.Ticket;
import entities.enums.Difficulty;

public class BusinessManager {

    PlayerDAO playerDAO;
    RoomManager roomManager;

    public BusinessManager(PlayerDAO playerDAO, RoomManager roomManager) {
        this.playerDAO = playerDAO;
        this.roomManager = roomManager;
    }

    private void sellTicket(RoomDTO room, Player player) {
        Difficulty difficulty = room.difficulty();
        Ticket ticket = new Ticket(difficulty.getPriceByDifficulty(), room.name());
        player.addTicket(ticket);
        playerDAO.update(player);
    }

    public void processSale(Player player) {
        RoomDTO room = roomManager.getRoomDTO("play");
        sellTicket(room, player);
        TicketPrinter.printTicket(player, room);
    }
}