package observer;

import daos.PlayerDAOImpl;
import daos.RoomDAOImpl;
import entities.Newsletter;
import entities.Player;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationServiceTest {

    private Newsletter newsletter;
    private TestObserver observer1;
    private TestPlayer zohra;
    private TestPlayer jose;
    private TestPlayer luis;
    private NotificationService notificationService;
    private PlayerDAOImpl playerDAO;
    private RoomDAOImpl roomDAO;

    @BeforeEach
    void setUp() {
        List<Player> players = new ArrayList<>();
        zohra = new TestPlayer("Zohra", "zohra@gmail.com", true);
        jose = new TestPlayer("Jose", "jose@gmail.com", true);
        luis = new TestPlayer("Luis", "luis@gmail.com", false);
        players.add(zohra);
        players.add(jose);
        players.add(luis);

        newsletter = new Newsletter(players);
        observer1 = new TestObserver();

        playerDAO = new PlayerDAOImpl() {
            @Override
            public List<Player> findAll() {
                return players;
            }
        };
        notificationService = new NotificationService(playerDAO, newsletter);
    }

    @Test
    void constructor_ShouldInstantiateAllPlayersList() {
        assertTrue(newsletter.getAllPlayers().contains(zohra));
    }

    @Test
    void constructor_ShouldNotAddPlayersToObserversList() {
        assertTrue(newsletter.getObservers().isEmpty());
    }

    @Test
    void addObserver_ShouldAddAnObserverToObserversList() {
        newsletter.addObserver(observer1);
        assertTrue(newsletter.getObservers().contains(observer1));
    }

    @Test
    void removeObserver_ShouldRemoveAnObserverFromObserversList() {
        newsletter.addObserver(observer1);
        newsletter.removeObserver(observer1);
        assertTrue(newsletter.getObservers().isEmpty());
    }

    @Test
    void notifyObservers_ShouldUpdateObservers() {
        newsletter.addObserver(observer1);
        newsletter.setLastNews("you have a new message");
        newsletter.notifyObservers();

        assertEquals("you have a new message", observer1.getLastMessage());
    }

    @Test
    void notifyAllPlayers_ShouldUpdateAllPlayers() {
        String lastNews = "We have a message for all players";
        notificationService.notifyAllPlayers(lastNews);

        assertAll(
                () -> assertEquals("We have a message for all players", zohra.getLastMessage()),
                () -> assertEquals("We have a message for all players", jose.getLastMessage()),
                () -> assertEquals("We have a message for all players", luis.getLastMessage())

        );
    }

    @Test
    void notifySusbcribed_ShouldUpdateSubscribed() {
        String lastNews = "We have a message for our subscribed players";
        notificationService.notifySubscribedPlayers(lastNews);

        assertAll(
                () -> assertEquals("We have a message for our subscribed players", zohra.getLastMessage()),
                () -> assertEquals("We have a message for our subscribed players", jose.getLastMessage()),
                () -> assertNotEquals("We have a message for our subscribed players", luis.getLastMessage())
        );
    }

    static class TestObserver implements Observer {
        private String lastNews;

        @Override
        public void update(String message) {
            this.lastNews = message;
        }

        public String getLastMessage() {
            return lastNews;
        }
    }

    @Getter
    @Setter
    static class TestPlayer extends Player {
        private String lastMessage;

        public TestPlayer(String name, String email, boolean isSubscribed) {
            super(name, email, isSubscribed);
        }

        @Override
        public void update(String message) {
            this.lastMessage = message;
        }
    }

}
