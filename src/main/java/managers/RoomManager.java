package managers;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import database.MongoDBConnection;
import dtos.ClueDTO;
import dtos.DecorationDTO;
import dtos.RoomDTO;
import entities.Clue;
import entities.Decoration;
import entities.Room;
import entities.enums.Difficulty;
import entities.enums.Theme;
import org.bson.Document;
import org.bson.types.ObjectId;
import utils.ValidateInputs;

import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    MongoCollection<Document> escapeRoomCollection = MongoDBConnection.getEscapeRoomCollection();

    public static Room createRoom() {

        String name = ValidateInputs.validateString("Enter the name of the room to create: ");
        int price = ValidateInputs.validateIntegerBetweenOnRange(
                "Enter the room price in €: ", 1, 999999
        );
        Difficulty difficulty = ValidateInputs.validateEnum(
                Difficulty.class, "Enter difficulty (EASY, MEDIUM, HARD): "
        );

        return Room.builder()
                .id(new ObjectId())
                .name(name)
                .price(price)
                .difficulty(difficulty)
                .decorations(new ArrayList<>())
                .clues(new ArrayList<>())
                .build();
    }

    public void deleteRoomByUserSelection() {
        List<RoomDTO> rooms = getAllRoomsDTO();
        int choice = ChosenDTORoom("delete");
        if (choice == 0) {
            System.out.println("Going back...");
        } else {
            ObjectId idToDelete = rooms.get(choice - 1).getId();
            String nameRoom = rooms.get(choice - 1).getName();
            deleteById(idToDelete, nameRoom);
        }
    }

    private void deleteById(ObjectId id, String name) {
        escapeRoomCollection.deleteOne(Filters.eq("_id", id));
        System.out.println(">> Room '" + name + "' successfully deleted.");
    }


    public static Decoration createDecoration() {
        String name = ValidateInputs.validateString("Enter the name of the decoration to create: ");
        int price = ValidateInputs.validateIntegerBetweenOnRange(
                "Enter the decoration price in €: ", 1, 999999
        );

        Decoration.Material material = ValidateInputs.validateEnum(
                Decoration.Material.class, "Enter material (PLASTIC, METAL, GLASS, WOOD): "
        );
        return Decoration.builder()
                .id(new ObjectId())
                .price(price)
                .name(name)
                .material(material)
                .build();
    }

    /**
     * @deprecated Metodo no eficiente, carga el documento al completo
     * getAllRoomsDTO() es más eficiente
     */
    @Deprecated
    public void showAllRooms() {
        List<Document> rooms = this.escapeRoomCollection.find().into(new ArrayList<>());
        for (int i = 0; i < rooms.size(); i++) {
            Document roomDoc = rooms.get(i);
            String nameRoom = roomDoc.getString("name");
            System.out.println((i + 1) + ". " + nameRoom);
        }
    }

    public int ChosenDTORoom(String action) {
        List<RoomDTO> rooms = getAllRoomsDTO();
        if (rooms.isEmpty()) {
            System.out.println("There are no registered rooms");
            return 0;
        } else {
            for (int i = 0; i < rooms.size(); i++) {
                System.out.println((i + 1) + ". " + rooms.get(i).getName());
            }
            System.out.println("0. Go back");
            return ValidateInputs.validateIntegerBetweenOnRange(
                    "Choose the room you want to " + action + ": ", 0, rooms.size()
            );
        }
    }


    public List<RoomDTO> getAllRoomsDTO() {
        List<RoomDTO> rooms = new ArrayList<>();
        try (MongoCursor<Document> cursor = escapeRoomCollection
                .find()
                .projection(Projections.include("name", "difficulty"))
                .iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                rooms.add(new RoomDTO(
                        doc.getObjectId("_id"),
                        doc.getString("name"),
                        Difficulty.valueOf(doc.getString("difficulty"))));
            }
        } catch (MongoException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Invalid index selected.");
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
        return rooms;
    }

    public void addDecorationToRoom() {
        List<RoomDTO> rooms = getAllRoomsDTO();
        int roomChoice = ChosenDTORoom("add decoration");
        if (roomChoice == 0) {
            System.out.println("Going back...");
        } else {
            RoomDTO room = rooms.get(roomChoice - 1);
            Decoration decoration = createDecoration();
            Document decorationDoc = new Document("_id", decoration.getId())
                    .append("price", decoration.getPrice())
                    .append("name", decoration.getName())
                    .append("material", decoration.getMaterial().toString());
            escapeRoomCollection.updateOne(
                    Filters.eq("_id", room.getId()),
                    new Document("$push", new Document("decorations", decorationDoc))
            );

            System.out.println(">> Decoration '" + decoration.getName() + "' added to room '" + room.getName() + "'");
        }
    }

    public List<ClueDTO> getAllCluesDTO(ObjectId roomId) {
        List<ClueDTO> clueDTOS = new ArrayList<>();
        try {
            Document roomDoc = escapeRoomCollection.find(Filters.eq("_id", roomId))
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

    public void deleteClueFromRoom() {
        List<RoomDTO> rooms = getAllRoomsDTO();
        int roomChoice = ChosenDTORoom("delete clue");
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

                escapeRoomCollection.updateOne(
                        Filters.eq("_id", roomId),
                        new Document("$pull", new Document("clues", new Document("_id", clue.getId())))
                );
                System.out.println(">> Clue '" + clue.getName() + "' successfully deleted.");
            }
        }
    }

    public List<DecorationDTO> getAllDecorationsDTO(ObjectId roomId) {
        List<DecorationDTO> decorationsDAO = new ArrayList<>();
        try {
            Document roomDoc = escapeRoomCollection.find(Filters.eq("_id", roomId))
                    .projection(Projections.include("decorations"))
                    .first();

            if (roomDoc != null && roomDoc.containsKey("decorations")) {
                List<Document> decorations = roomDoc.getList("decorations", Document.class);
                for (Document decoration : decorations) {
                    decorationsDAO.add(new DecorationDTO(
                            decoration.getObjectId("_id"),
                            decoration.getString("name")
                    ));
                }
            }
        } catch (MongoException e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return decorationsDAO;
    }

    public int ChosenDTODecoration(String action, ObjectId roomId) {
        List<DecorationDTO> decorationsDTO = getAllDecorationsDTO(roomId);
        if (decorationsDTO.isEmpty()) {
            System.out.println("No decorations to " + action + " in this room.");
            return 0;
        } else {
            for (int i = 0; i < decorationsDTO.size(); i++) {
                System.out.println((i + 1) + ". " + decorationsDTO.get(i).getName());
            }
            System.out.println("0. Go back");
            return ValidateInputs.validateIntegerBetweenOnRange(
                    "Choose the decoration you want to " + action + ": ", 0, decorationsDTO.size());
        }
    }

    public void deleteDecorationFromRoom() {
        List<RoomDTO> rooms = getAllRoomsDTO();
        int roomChoice = ChosenDTORoom("delete decoration");
        if (roomChoice == 0) {
            System.out.println("Going back...");
        } else {
            RoomDTO room = rooms.get(roomChoice - 1);
            ObjectId roomId = room.getId();

            int decorationChoice = ChosenDTODecoration("delete", roomId);
            if (decorationChoice == 0) {
                System.out.println("Going back...");
            } else {
                List<DecorationDTO> decorationsDTO = getAllDecorationsDTO(roomId);
                DecorationDTO decorationDTO = decorationsDTO.get(decorationChoice - 1);

                escapeRoomCollection.updateOne(
                        Filters.eq("_id", roomId),
                        new Document("$pull", new Document("decorations", new Document("_id", decorationDTO.getId())))
                );
                System.out.println(">> Decoration '" + decorationDTO.getName() + "' successfully deleted.");
            }
        }
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
        List<RoomDTO> rooms = getAllRoomsDTO();
        int roomChoice = ChosenDTORoom("add clue");
        if (roomChoice == 0) {
            System.out.println("Going back...");
        } else {
            RoomDTO room = rooms.get(roomChoice - 1);
            Clue clue = createClue();
            Document clueDoc = new Document("_id", clue.getId())
                    .append("price", clue.getPrice())
                    .append("name", clue.getName())
                    .append("theme", clue.getTheme().toString());
            escapeRoomCollection.updateOne(
                    Filters.eq("_id", room.getId()),
                    new Document("$push", new Document("clues", clueDoc))
            );
            System.out.println(">> Clue '" + clue.getName() + "' added to room '" + room.getName() + "'");
        }
    }

}
