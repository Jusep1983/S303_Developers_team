package entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import observer.Observer;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Player implements Observer {

    @BsonId
    private ObjectId id;
    private String name;
    private String email;
    private boolean isSubscribed;
    private List<Ticket> ticketsBought;

    public Player(String name, String email) {
        this.id = new ObjectId();
        this.name = name;
        this.email = email;
        this.isSubscribed = true;
        this.ticketsBought = new ArrayList<>();
    }

    public Player(ObjectId id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.isSubscribed = true;
        this.ticketsBought = new ArrayList<>();
    }

    public void addTicket(Ticket ticket) {
        ticketsBought.add(ticket);
    }

    @Override
    public void update(String message) {
        System.out.println("New message from DreamTeam Escape-Room :" + message);
    }
}
