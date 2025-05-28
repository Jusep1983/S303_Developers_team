package observer;

import entities.Newsletter;
import entities.Player;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;



public class TestNewsletter {
    private Newsletter newsletter;
    private TestObserver observer1;

    private TestPlayer player1;
    private TestPlayer player2;
    private NotificationService notificationService = new NotificationService();



    @BeforeEach
    void setUp() {

        List<Player> players = new ArrayList<>();
        newsletter = new Newsletter(players);
        observer1 = new TestObserver();

        player1 = new TestPlayer("Zohra", "z.bel@me.com", true);
        player2 = new TestPlayer("Romina", "romi2@example.com", false);

        newsletter.getAllPlayers().add(player1);
        newsletter.getAllPlayers().add(player2);

    }

    @Test
    void constructor_ShouldInstantiateAllPlayersList(){
        assertTrue(newsletter.getAllPlayers().contains(player1));
    }

    @Test
    void constructor_ShouldNotAddPlayersToObserversList(){
        assertTrue(newsletter.getObservers().isEmpty());
    }
    @Test
    void addObserver_ShouldAddAnObserverToObserversList(){
        newsletter.addObserver(observer1);
        assertTrue(newsletter.getObservers().contains(observer1));
    }

    @Test
    void removeObserver_ShouldRemoveAnObserverFromObserversList(){
        newsletter.addObserver(observer1);
        newsletter.removeObserver(observer1);
        assertTrue(newsletter.getObservers().isEmpty());
    }

    @Test
    void notifyObservers_ShouldUpdateObservers(){
        newsletter.addObserver(observer1);
        newsletter.setLastNews("you have a new message");
        newsletter.notifyObservers();

        assertEquals("you have a new message", observer1.getLastMessage());
    }

    @Test
    void notifyAllPlayers_ShouldUpdateAllPlayers(){
        newsletter.setLastNews("We have a message for all players");
        notificationService.notifyAllPlayers();

        assertAll(
                () -> assertEquals("We have a message for all players", player1.getLastMessage()),
                () -> assertEquals("We have a message for all players", player2.getLastMessage())

        );
    }

    @Test
    void notifySusbcribed_ShouldUpdateSubscribed(){
        newsletter.setLastNews("We have a message for our subscribed players");
        notificationService.notifySubscribedPlayers();

        assertAll(
                () -> assertEquals("We have a message for our subscribed players", player1.getLastMessage()),
                () -> assertNotEquals("We have a message for our subscribed players", player2.getLastMessage())
        );
    }



    static class TestObserver implements Observer {
        private String lastMessage;

        @Override
        public void update(String message) {
            this.lastMessage = message;
        }

        public String getLastMessage(){
            return lastMessage;
        }
    }


    @Getter@Setter
    static class TestPlayer extends Player{
        private String lastMessage;

        public TestPlayer(String name, String email, boolean isSubscribed){
            super( name, email, isSubscribed);
        }

        @Override
        public void update(String message) {
            this.lastMessage = message;
        }
        public String getLastMessage(){
            return lastMessage;
        }

    }
}
