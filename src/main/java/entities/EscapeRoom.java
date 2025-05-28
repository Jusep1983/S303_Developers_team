package entities;

import daos.ClueDAOImpl;
import daos.DecorationDAOImpl;
import daos.RoomDAOImpl;
import daos.TicketDAOImpl;
import lombok.Getter;
import managers.*;

@Getter
public class EscapeRoom {

    private static EscapeRoom escapeRoom;
    private EscapeRoomManager escapeRoomManager = new EscapeRoomManager();
    private RoomManager roomManager = new RoomManager();
    private PlayerManager playerManager = new PlayerManager();
    private RoomDAOImpl roomDAOImpl = new RoomDAOImpl();
    private TicketDAOImpl ticketDAOImpl = new TicketDAOImpl();
    private BusinessManager businessManager = new BusinessManager();
    private ClueDAOImpl clueDao = new ClueDAOImpl();
    private ClueManager clueManager = new ClueManager(roomManager, clueDao);
    private DecorationDAOImpl decorationDAOImpl = new DecorationDAOImpl();
    private DecorationManager decorationManager = new DecorationManager(roomManager, decorationDAOImpl);

    private EscapeRoom() {
        this.escapeRoomManager = new EscapeRoomManager();
        this.roomManager = new RoomManager();
        this.playerManager = new PlayerManager();
        this.roomDAOImpl = new RoomDAOImpl();
        this.ticketDAOImpl = new TicketDAOImpl();
        this.businessManager = new BusinessManager();
        this.clueDao = new ClueDAOImpl();
        this.clueManager = new ClueManager(roomManager, clueDao);
        this.decorationDAOImpl = new DecorationDAOImpl();
        this.decorationManager = new DecorationManager(roomManager, decorationDAOImpl);
    }

    public static EscapeRoom getEscapeRoom() {
        if (escapeRoom == null) {
            escapeRoom = new EscapeRoom();
        }
        return escapeRoom;
    }
}
