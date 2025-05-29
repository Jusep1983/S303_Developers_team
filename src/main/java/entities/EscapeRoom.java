package entities;

import daos.*;
import daos.interfaces.PlayerDAO;
import lombok.Getter;
import managers.*;

@Getter
public class EscapeRoom {

    private static EscapeRoom escapeRoom;
    private final EscapeRoomManager escapeRoomManager;
    private final RoomManager roomManager;
    private final PlayerDAO playerDAO;
    private final PlayerManager playerManager;
    private final RoomDAOImpl roomDAO;
    private final TicketDAOImpl ticketDAO;
    private final BusinessManager businessManager;
    private final ClueDAOImpl clueDAO;
    private final ClueManager clueManager;
    private final DecorationDAOImpl decorationDAO;
    private final DecorationManager decorationManager;

    private EscapeRoom() {
        this.escapeRoomManager = new EscapeRoomManager();
        this.roomManager = new RoomManager();
        this.playerDAO = new PlayerDAOImpl();
        this.playerManager = new PlayerManager(playerDAO);
        this.roomDAO = new RoomDAOImpl();
        this.ticketDAO = new TicketDAOImpl();
        this.businessManager = new BusinessManager(playerDAO, roomManager);
        this.clueDAO = new ClueDAOImpl();
        this.clueManager = new ClueManager(roomManager, clueDAO);
        this.decorationDAO = new DecorationDAOImpl();
        this.decorationManager = new DecorationManager(roomManager, decorationDAO);
    }

    public static synchronized EscapeRoom getEscapeRoom() {
        if (escapeRoom == null) {
            escapeRoom = new EscapeRoom();
        }
        return escapeRoom;
    }
}
