package entities;

import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@Data
public class Ticket {

    @BsonId
    private ObjectId id;
    private int price;
    private String room;

    public Ticket(int price, String room) {
        this.id = new ObjectId();
        this.price = price;
        this.room = room;
    }

    public Ticket(ObjectId id, int price, String room) {
        this.id = id;
        this.price = price;
        this.room = room;
    }

}