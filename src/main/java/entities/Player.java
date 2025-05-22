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


    @Override
    public void update(String message) {
        System.out.println("New message from DreamTeam Escape-Room :" + message);
    }
}
