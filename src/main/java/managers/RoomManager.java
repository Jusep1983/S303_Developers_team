package managers;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import database.MongoDBConnection;
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

    private static Clue createClue() {
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

    private static Decoration createDecoration() {
        String name = ValidateInputs.validateString("Enter the name of the decoration to create: ");
        int price = ValidateInputs.validateIntegerBetweenOnRange(
                "Enter the decoration price in €: ", 1, 999999
        );

        Decoration.Material material = ValidateInputs.validateEnum(
                Decoration.Material.class, "Enter theme (PLASTIC, METAL, GLASS, WOOD): "
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

    public List<RoomDTO> getAllRoomsDTO() {
        List<RoomDTO> rooms = new ArrayList<>();
        try (MongoCursor<Document> cursor = escapeRoomCollection
                .find()
                .projection(Projections.include("name"))
                .iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                rooms.add(new RoomDTO(doc.getObjectId("_id"), doc.getString("name")));
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

    public void deleteRoomByUserSelection() {
        List<RoomDTO> rooms = getAllRoomsDTO();

        if (rooms.isEmpty()) {
            System.out.println("There are no rooms to delete.");
        } else {
            for (int i = 0; i < rooms.size(); i++) {
                System.out.println((i + 1) + ". " + rooms.get(i).getName());
            }
            System.out.println("0. Go back");
            int choice = ValidateInputs.validateIntegerBetweenOnRange(
                    "Choose the room you want to delete: ", 0, rooms.size()
            );
            if (choice == 0) {
                System.out.println("Going back...");
            } else {
                ObjectId idToDelete = rooms.get(choice - 1).getId();
                String nameRoom = rooms.get(choice - 1).getName();
                deleteById(idToDelete, nameRoom);
            }
        }
    }

    private void deleteById(ObjectId id, String name) {
        escapeRoomCollection.deleteOne(Filters.eq("_id", id));
        System.out.println(">> Room '" + name + "' successfully deleted.");
    }

    private void addClue(Clue clue) {
    }

    public void deleteClue(Room room) {
    }

    public void addDecoration(Room room) {
    }

    public void deleteDecoration(Room room) {
    }
}
