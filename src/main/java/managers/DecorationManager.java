package managers;

import com.mongodb.MongoException;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import dtos.DecorationDTO;
import dtos.RoomDTO;
import entities.Decoration;
import org.bson.Document;
import org.bson.types.ObjectId;
import utils.ValidateInputs;

import java.util.ArrayList;
import java.util.List;

public class DecorationManager {
    private final RoomManager roomManager;

    public DecorationManager(RoomManager roomManager) {
        this.roomManager = roomManager;
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

    public void addDecorationToRoom() {
        List<RoomDTO> rooms = this.roomManager.getAllRoomsDTO();
        int roomChoice = this.roomManager.ChosenDTORoom("add decoration");
        if (roomChoice == 0) {
            System.out.println("Going back...");
        } else {
            RoomDTO room = rooms.get(roomChoice - 1);
            Decoration decoration = createDecoration();
            Document decorationDoc = new Document("_id", decoration.getId())
                    .append("price", decoration.getPrice())
                    .append("name", decoration.getName())
                    .append("material", decoration.getMaterial().toString());
            this.roomManager.getEscapeRoomCollection().updateOne(
                    Filters.eq("_id", room.getId()),
                    new Document("$push", new Document("decorations", decorationDoc))
            );

            System.out.println(">> Decoration '" + decoration.getName() + "' added to room '" + room.getName() + "'");
        }
    }

    public void deleteDecorationFromRoom() {
        List<RoomDTO> rooms = this.roomManager.getAllRoomsDTO();
        int roomChoice = this.roomManager.ChosenDTORoom("delete decoration");
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

                this.roomManager.getEscapeRoomCollection().updateOne(
                        Filters.eq("_id", roomId),
                        new Document("$pull", new Document("decorations", new Document("_id", decorationDTO.getId())))
                );
                System.out.println(">> Decoration '" + decorationDTO.getName() + "' successfully deleted.");
            }
        }
    }

    public List<DecorationDTO> getAllDecorationsDTO(ObjectId roomId) {
        List<DecorationDTO> decorationsDAO = new ArrayList<>();
        try {
            Document roomDoc = this.roomManager.getEscapeRoomCollection().find(Filters.eq("_id", roomId))
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

}
