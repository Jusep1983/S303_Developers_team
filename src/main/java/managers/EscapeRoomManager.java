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
    public int getInventoryValue() {return 0;}
    public int getClueCount() {return 0;}
    public int getDecorationCount() {return 0;}
    public int getRoomCount() {return 0;}
}
