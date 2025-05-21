package managers;

import java.util.Map;

public class EscapeRoomManager {

    public Map<String, Integer> getInventoryCount() {
        return Map.of(
                "Rooms: ", getRoomCount(),
                "Clues: ", getClueCount(),
                "Decorations: ", getDecorationCount()
        );
    }

    public void addRoom() {}
    public void editRoom() {}
    public int getInventoryValue() {}
    public int getClueCount() {}
    public int getDecorationCount() {}
    public int getRoomCount() {}
}
