package managers;

import com.mongodb.MongoException;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import daos.interfaces.ClueDAO;
import dtos.ClueDTO;
import entities.Clue;
import entities.enums.Theme;
import org.bson.Document;
import org.bson.types.ObjectId;
import validation.ValidateInputs;

import java.util.ArrayList;
import java.util.List;

public class ClueManager {
    RoomManager roomManager;
    ClueDAO clueDao;

public ClueManager(RoomManager roomManager, ClueDAO clueDAO) {
        this.roomManager = roomManager;
        this.clueDao = clueDAO;
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
        roomManager.selectRoom("add clue").ifPresent(room -> {
            Clue clue = createClue();
            clueDao.save(clue, room.id());
            System.out.println(">> Clue '" + clue.getName() + "' added to room '" + room.name() + "'");
        });
    }

    public void deleteClueFromRoom() {
        roomManager.selectRoom("delete clue").ifPresent(room -> {
            ObjectId roomId = room.id();
            List<ClueDTO> clues = getAllCluesDTO(roomId);
            if (clues.isEmpty()) {
                System.out.println("No clues to delete in this room.");
                return;
            }

            displayClues(clues);
            int clueChoice = ValidateInputs.validateIntegerBetweenOnRange(
                    "Choose the clue you want to delete: ", 0, clues.size());

            if (clueChoice == 0) {
                System.out.println("Going back...");
            } else {
                ClueDTO clue = clues.get(clueChoice - 1);
                clueDao.delete(clue.id(), roomId);
                System.out.println(">> Clue '" + clue.name() + "' successfully deleted.");
            }
        });
    }

    private void displayClues(List<ClueDTO> clues) {
        for (int i = 0; i < clues.size(); i++) {
            System.out.println((i + 1) + ". " + clues.get(i).name());
        }
        System.out.println("0. Go back");
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

}
