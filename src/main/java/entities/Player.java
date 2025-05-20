package entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import observer.Observer;

@Data
@AllArgsConstructor
public class Player implements Observer {
    private String name;
    private String email;
    private boolean isSubscribed;


    @Override
    public void update(String message) {
        System.out.println("New message from DreamTeam Escape-Room :" + message);
    }
}
