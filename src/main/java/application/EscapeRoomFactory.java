package application;

import daos.*;
import daos.interfaces.PlayerDAO;
import entities.EscapeRoom;
import managers.*;
import menus.NotificationMenu;

public class EscapeRoomFactory {

    private static EscapeRoom instance;

    private EscapeRoomFactory() {}

    public static synchronized EscapeRoom getInstance() {
        if (instance == null) {
            instance = create();
        }
        return instance;
    }

    public static EscapeRoom create() {
        EscapeRoomManager escapeRoomManager = new EscapeRoomManager();
        RoomManager roomManager = new RoomManager();
        PlayerDAO playerDAO = new PlayerDAOImpl();
        PlayerManager playerManager = new PlayerManager(playerDAO);
        RoomDAOImpl roomDAO = new RoomDAOImpl();
        TicketDAOImpl ticketDAO = new TicketDAOImpl();
        BusinessManager businessManager = new BusinessManager(playerDAO, roomManager);
        ClueDAOImpl clueDAO = new ClueDAOImpl();
        ClueManager clueManager = new ClueManager(roomManager, clueDAO);
        DecorationDAOImpl decorationDAO = new DecorationDAOImpl();
        DecorationManager decorationManager = new DecorationManager(roomManager, decorationDAO);
        NewsletterManager newsletterManager = new NewsletterManager();
        NotificationMenu notificationMenu = new NotificationMenu();
        CertificatePrinter certificatePrinter = new CertificatePrinter();

        return new EscapeRoom(
                escapeRoomManager,
                roomManager,
                playerManager,
                roomDAO,
                ticketDAO,
                businessManager,
                clueManager,
                decorationManager,
                newsletterManager,
                notificationMenu,
                certificatePrinter
        );
    }
}
