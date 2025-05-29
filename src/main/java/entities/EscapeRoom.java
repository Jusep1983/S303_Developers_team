package entities;

import daos.*;
import managers.*;

public record EscapeRoom(EscapeRoomManager escapeRoomManager, RoomManager roomManager, PlayerManager playerManager,
                         RoomDAOImpl roomDAO, TicketDAOImpl ticketDAO, BusinessManager businessManager,
                         ClueManager clueManager, DecorationManager decorationManager) {}
