package managers;

import com.mongodb.MongoException;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import dtos.ClueDTO;
import dtos.RoomDTO;
import entities.Clue;
import entities.enums.Theme;
import org.bson.Document;
import org.bson.types.ObjectId;
import utils.ValidateInputs;

import java.util.ArrayList;
import java.util.List;

public class ClueManager {
    private final RoomManager roomManager;

    public ClueManager(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    public static Clue createClue() {
        String name = ValidateInputs.validateString("Enter the name of the clue to create: ");
        int price = ValidateInputs.validateIntegerBetweenOnRange(
                "Enter the clue price in €: ", 1, 999999
        );
        Theme theme = ValidateInputs.validateEnum(Theme.class, "Enter theme (PUZZLE, PASSWORD, SYMBOL): ");
        return Clue.builder()
                .id(new ObjectId())
                .price(price)
                .name(name)
                .theme(theme)
                .build();
    }

    public void addClueToRoom() {
        List<RoomDTO> rooms = this.roomManager.getAllRoomsDTO();
        int roomChoice = this.roomManager.ChosenDTORoom("add clue");
        if (roomChoice == 0) {
            System.out.println("Going back...");
        } else {
            RoomDTO room = rooms.get(roomChoice - 1);
            Clue clue = createClue();
            Document clueDoc = new Document("_id", clue.getId())
                    .append("price", clue.getPrice())
                    .append("name", clue.getName())
                    .append("theme", clue.getTheme().toString());
            this.roomManager.getEscapeRoomCollection().updateOne(
                    Filters.eq("_id", room.getId()),
                    new Document("$push", new Document("clues", clueDoc))
            );
            System.out.println(">> Clue '" + clue.getName() + "' added to room '" + room.getName() + "'");
        }
    }

    public void deleteClueFromRoom() {
        List<RoomDTO> rooms = this.roomManager.getAllRoomsDTO();
        int roomChoice = this.roomManager.ChosenDTORoom("delete clue");
        if (roomChoice == 0) {
            System.out.println("Going back...");
        } else {
            RoomDTO room = rooms.get(roomChoice - 1);
            ObjectId roomId = room.getId();

            int clueChoice = ChosenDTOClue("delete", roomId);
            if (clueChoice == 0) {
                System.out.println("Going back...");
            } else {
                List<ClueDTO> clues = getAllCluesDTO(roomId);
                ClueDTO clue = clues.get(clueChoice - 1);

                this.roomManager.getEscapeRoomCollection().updateOne(
                        Filters.eq("_id", roomId),
                        new Document("$pull", new Document("clues", new Document("_id", clue.getId())))
                );
                System.out.println(">> Clue '" + clue.getName() + "' successfully deleted.");
            }
        }
    }

    public List<ClueDTO> getAllCluesDTO(ObjectId roomId) {
        List<ClueDTO> clueDTOS = new ArrayList<>();
        try {
            Document roomDoc = this.roomManager.getEscapeRoomCollection().find(Filters.eq("_id", roomId))
                    .projection(Projections.include("clues"))
                    .first();

            if (roomDoc != null && roomDoc.containsKey("clues")) {
                List<Document> clues = roomDoc.getList("clues", Document.class);
                for (Document clue : clues) {
                    clueDTOS.add(new ClueDTO(
                            clue.getObjectId("_id"),
                            clue.getString("name")
                    ));
                }
            }
        } catch (MongoException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return clueDTOS;
    }

    public int ChosenDTOClue(String action, ObjectId roomId) {
        List<ClueDTO> clues = getAllCluesDTO(roomId);
        if (clues.isEmpty()) {
            System.out.println("No clues to " + action + " in this room.");
            return 0;
        } else {
            for (int i = 0; i < clues.size(); i++) {
                System.out.println((i + 1) + ". " + clues.get(i).getName());
            }
            System.out.println("0. Go back");
            return ValidateInputs.validateIntegerBetweenOnRange(
                    "Choose the clue you want to " + action + ": ", 0, clues.size());
        }
    }

}
