package daos;

import com.mongodb.client.MongoCollection;
import daos.interfaces.PlayerDAO;
import daos.interfaces.RoomDAO;
import database.MongoDBConnection;
import entities.*;
import entities.enums.Difficulty;
import observer.NotificationService;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class RoomDAOImpl implements RoomDAO {

    private final MongoCollection<Document> escapeRoomCollection;
    private final NotificationService notificationService;

    public RoomDAOImpl() {
        escapeRoomCollection = MongoDBConnection.getEscapeRoomCollection();
        PlayerDAOImpl playerDAO = new PlayerDAOImpl();
        List<Player> players = playerDAO.findAll();  // Récupère tous les joueurs
        Newsletter newsletter = new Newsletter(players);
        this.notificationService = new NotificationService(playerDAO, newsletter);
    }


    @Override
    public void save(Room room) {
        Document doc = new Document(
                "_id", room.getId())
                .append("name", room.getName())
                .append("price", room.getPrice())
                .append("difficulty", room.getDifficulty());
        escapeRoomCollection.insertOne(doc);

        notificationService.notifyNewRoomToAllSubscribedPlayers(room);
    }

    @Override
    public Room findById(ObjectId id) {
        Document doc = escapeRoomCollection.find(new Document("_id", id)).first();
        return doc != null ? documentToRoom(doc) : null;
    }

    @Override
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        for (Document doc : escapeRoomCollection.find()) {
            rooms.add(documentToRoom(doc));
        }
        return rooms;
    }

    @Override
    public void update(Room room) {
        Document updated = new Document("name", room.getName())
                    .append("price", room.getPrice())
                    .append("difficulty", room.getDifficulty())
                    .append("decorations", room.getDecorations())
                    .append("clues", room.getClues());
        escapeRoomCollection.updateOne(new Document("_id", room.getId()), updated);
    }

    @Override
    public void delete(Room room) {
        escapeRoomCollection.deleteOne(new Document("_id", room.getId()));
    }

    private Room documentToRoom(Document doc) {
        return new Room(
                doc.getObjectId("_id"),
                doc.getString("name"),
                doc.getInteger("price"),
                Difficulty.valueOf(doc.getString("difficulty").toUpperCase()),
                doc.getList("decorations", Decoration.class),
                doc.getList("clues", Clue.class));
    }
}
