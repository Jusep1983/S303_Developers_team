package daos.interfaces;

import entities.Ticket;
import org.bson.types.ObjectId;

public interface TicketDAO {

    void save(Ticket ticket);
    Ticket findById(ObjectId id);
    void deleteById(ObjectId id);
    void getPrice(ObjectId id);
    int count();

}
