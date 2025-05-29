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
    private List<Ticket> ticketsBought = new ArrayList<>();

    public Player(String name, String email) {
        this.id = new ObjectId();
        this.name = name;
        this.email = email;
        this.isSubscribed = true;
    }

    public Player(String name, String email, boolean isSubscribed) {
        this.id = new ObjectId();
        this.name = name;
        this.email = email;
        this.isSubscribed = isSubscribed;

    }

    public Player(ObjectId id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.isSubscribed = true;
        this.ticketsBought = new ArrayList<>();
    }

    public List<Ticket> getTicketsBought() {
        if (ticketsBought == null) {
            ticketsBought = new ArrayList<>();
        }
        return ticketsBought;
    }

    public void addTicket(Ticket ticket) {
        if (ticketsBought == null) {
            ticketsBought = new ArrayList<>();
        }
        ticketsBought.add(ticket);
    }


    @Override
    public void update(String message) {
        System.out.println("Hello " + this.getName() + ". You have a new message :" + message);
    }
}
