package managers;

import com.mongodb.MongoException;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import daos.interfaces.DecorationDAO;
import dtos.DecorationDTO;
import dtos.RoomDTO;
import entities.Decoration;
import entities.enums.Material;
import org.bson.Document;
import org.bson.types.ObjectId;
import validation.ValidateInputs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DecorationManager {

    private final RoomManager roomManager;
    private final DecorationDAO decorationDAO;

    public DecorationManager(RoomManager roomManager, DecorationDAO decorationDAO) {
        this.roomManager = roomManager;
        this.decorationDAO = decorationDAO;
    }

    public static Decoration createDecoration() {
        String name = ValidateInputs.validateString("Enter the name of the decoration to create: ");
        int price = ValidateInputs.validateIntegerBetweenOnRange(
                "Enter the decoration price in €: ", 1, 999999
        );

        Material material = ValidateInputs.validateEnum(
                Material.class, "Enter material (PLASTIC, METAL, GLASS, WOOD): "
        );
        return Decoration.builder()
                .id(new ObjectId())
                .price(price)
                .name(name)
                .material(material)
                .build();
    }

    public void addDecoration() {
        Decoration decoration = createDecoration();
        Optional<RoomDTO> optionalRoom = roomManager.selectRoom("add decoration");

        optionalRoom.ifPresent(room -> {
            decorationDAO.save(decoration, room.id());
            System.out.println(">> Decoration '" + decoration.getName() + "' added to room '" + room.name() + "'");
        });
    }

    public Optional<DecorationDTO> selectDecoration(String action, RoomDTO room) {
        List<DecorationDTO> decorations = getAllDecorationsDTO(room.id());
        int choice = chosenDTODecoration(action, decorations);
        if (choice == 0) {
            System.out.println("Going back...");
            return Optional.empty();
        }
        return Optional.of(decorations.get(choice - 1));
    }

    public void deleteDecoration() {
        String action = "delete";
        roomManager.selectRoom(action).ifPresent(room -> {
            Optional<DecorationDTO> decorationOpt = selectDecoration(action, room);

            decorationOpt.ifPresent(decoration -> {
                decorationDAO.delete(decoration.id(), room.id());
                System.out.println(">> Decoration '" + decoration.name() + "' successfully deleted from room '" + room.name() + "'.");
            });
        });
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

    public int chosenDTODecoration(String action, List<DecorationDTO> decorations) {
        if (decorations.isEmpty()) {
            System.out.println("No decorations to " + action + " in this room.");
            return 0;
        } else {
            for (int i = 0; i < decorations.size(); i++) {
                System.out.println((i + 1) + ". " + decorations.get(i).name());
            }
            System.out.println("0. Go back");
            return ValidateInputs.validateIntegerBetweenOnRange(
                    "Choose the decoration you want to " + action + ": ", 0, decorations.size());
        }
    }

}
