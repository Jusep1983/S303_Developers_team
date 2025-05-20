package entities;

public class EscapeRoom {

    private static EscapeRoom escapeRoom;

    private EscapeRoom() {}

    public static EscapeRoom getEscapeRoom() {
        if (escapeRoom == null) {
            escapeRoom = new EscapeRoom();
        }
        return escapeRoom;
    }
}
