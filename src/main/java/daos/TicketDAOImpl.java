package daos;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import daos.interfaces.TicketDAO;
import database.MongoDBConnection;
import dtos.RoomDTO;
import entities.Ticket;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.List;

public class TicketDAOImpl implements TicketDAO {

    private final MongoCollection<Document> playersCollection;

    public TicketDAOImpl() {
        playersCollection = MongoDBConnection.getPlayersCollection();
    }

    @Override
    public int getTotalSales() {
        int totalSales = 0;
        Bson filter = Filters.exists("ticketsBought");
        FindIterable<Document> players = playersCollection.find(filter);
        for (Document player : players) {
            List<Document> boughtTickets = (List<Document>) player.get("ticketsBought");

            if (boughtTickets != null) {
                for (Document ticket : boughtTickets) {
                    if (ticket.containsKey("price")) {
                        totalSales += ticket.getInteger("price");
                    }
                }
            }
        }
        return totalSales;
    }

    public static Document ticketToDocument(Ticket ticket) {
        return new Document("_id", ticket.getId()).append("price", ticket.getPrice());
    }

    public static Ticket documentToTicket(Document doc) {
        ObjectId id = doc.getObjectId("_id");
        int price = doc.getInteger("price");
        String room = doc.getString("room");

        return new Ticket(id, price, room);
    }
}
