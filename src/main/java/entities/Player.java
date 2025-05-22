package entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import observer.Observer;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
public class Player implements Observer {

    @BsonId
    private ObjectId id;
    private String name;
    private String email;
    private boolean isSubscribed;

    public Player(String name, String email) {
        this.id = new ObjectId();
        this.name = name;
        this.email = email;
        this.isSubscribed = true;
    }

    @Override
    public void update(String message) {
        System.out.println("New message from DreamTeam Escape-Room :" + message);
    }
}
