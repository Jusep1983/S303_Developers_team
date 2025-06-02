package managers;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import database.MongoDBConnection;
import dtos.RoomDTO;
import entities.Room;
import entities.enums.Difficulty;
import lombok.Getter;
import org.bson.Document;
import org.bson.types.ObjectId;
import validation.ValidateInputs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class RoomManager {

    private final MongoCollection<Document> escapeRoomCollection;

    public RoomManager() {
        this.escapeRoomCollection = MongoDBConnection.getEscapeRoomCollection();
    }

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

    public Optional<RoomDTO> selectRoom(String action) {
        List<RoomDTO> rooms = getAllRoomsDTO();
        int choice = chooseRoomDTO(action);
        if (choice == 0) {
            System.out.println("Going back...");
            return Optional.empty();
        }
        return Optional.of(rooms.get(choice - 1));
    }

    private void deleteById(ObjectId id, String name) {
        this.escapeRoomCollection.deleteOne(Filters.eq("_id", id));
        System.out.println(">> Room '" + name + "' successfully deleted.");
    }

    public void deleteRoomByUserSelection() {
        selectRoom("delete").ifPresent(room -> deleteById(room.id(), room.name()));
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

    public int chooseRoomDTO(String action) {
        List<RoomDTO> rooms = getAllRoomsDTO();
        if (rooms.isEmpty()) {
            System.out.println("There are no registered rooms");
            return 0;
        } else {
            for (int i = 0; i < rooms.size(); i++) {
                System.out.println((i + 1) + ". " + rooms.get(i).name());
            }
            System.out.println("0. Go back");
            return ValidateInputs.validateIntegerBetweenOnRange(
                    "Choose the room you want to " + action + ": ", 0, rooms.size()
            );
        }
    }

    public List<RoomDTO> getAllRoomsDTO() {
        List<RoomDTO> rooms = new ArrayList<>();
        try (MongoCursor<Document> cursor = this.escapeRoomCollection
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
}
