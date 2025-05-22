package daos.interfaces;

import entities.Ticket;
import org.bson.types.ObjectId;
import org.bson.Document;

public interface TicketDAO {

    void save(Ticket ticket);
    Ticket findById(ObjectId id);
    void deleteById(ObjectId id);
    int getPrice(Document doc);
    int count();
}
