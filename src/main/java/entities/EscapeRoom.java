package entities;

import java.util.List;


public class EscapeRoom {

    private static EscapeRoom escapeRoom;
    List<Room> rooms;
    List<Player> players;

    private EscapeRoom() {}

    public static EscapeRoom getEscapeRoom() {
        if (escapeRoom == null) {
            escapeRoom = new EscapeRoom();
        }
        return escapeRoom;
    }
}
