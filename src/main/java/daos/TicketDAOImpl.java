package daos;

import com.mongodb.client.MongoCollection;
import daos.interfaces.TicketDAO;
import database.MongoDBConnection;
import entities.Ticket;
import org.bson.Document;
import org.bson.types.ObjectId;

public class TicketDAOImpl implements TicketDAO {

    private final MongoCollection<Document> escapeRoomCollection;

    public TicketDAOImpl() {
        escapeRoomCollection = MongoDBConnection.getEscapeRoomCollection();
    }

    @Override
    public void save(Ticket ticket) {
        Document doc = new Document("_id", ticket.getId())
                .append("price", ticket.getPrice());
        escapeRoomCollection.insertOne(doc);
    }

    @Override
    public Ticket findById(ObjectId id) {
        Document doc = escapeRoomCollection.find(new Document("_id", id)).first();
        return doc != null ? documentToTicket(doc) : null;
    }

    @Override
    public void deleteById(ObjectId id) {
        escapeRoomCollection.deleteOne(new Document("_id", id));
    }

    @Override
    public int count() {
        // TODO
        return 0;
    }

    @Override
    public int getPrice(Document doc) {
        return doc.getInteger("price");
    }

    public Ticket documentToTicket(Document doc) {
        return new Ticket(
                doc.getInteger("price")
        );
    }

    public static Document ticketToDocument(Ticket ticket) {
        return new Document("_id", ticket.getId()).append("price", ticket.getPrice());
    }
}
