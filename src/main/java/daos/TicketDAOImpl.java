package daos;

import com.mongodb.client.MongoCollection;
import database.MongoDBConnection;
import entities.Ticket;
import org.bson.Document;

public class TicketDAOImpl {

    private final MongoCollection<Document> escapeRoomCollection;

    public TicketDAOImpl() {
        escapeRoomCollection = MongoDBConnection.getEscapeRoomCollection();
    }

    public void save(Ticket ticket) {

    }
}
