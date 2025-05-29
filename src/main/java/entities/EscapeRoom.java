package entities;

import daos.*;
import managers.*;
import menus.NotificationMenu;

public record EscapeRoom(EscapeRoomManager escapeRoomManager, RoomManager roomManager, PlayerManager playerManager,
                         RoomDAOImpl roomDAO, TicketDAOImpl ticketDAO, BusinessManager businessManager,
                         ClueManager clueManager, DecorationManager decorationManager, NewsletterManager newsletterManager, NotificationMenu notificationMenu, CertificatePrinter certificatePrinter) {}
