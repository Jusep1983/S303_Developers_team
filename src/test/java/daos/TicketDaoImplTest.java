package daos;

import com.mongodb.client.*;
import database.MongoDBConnection;
import entities.Ticket;
import entities.Player;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import daos.TicketDAOImpl;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TicketDaoImplTest {

    private MongoCollection<Document> playersCollection = MongoDBConnection.getPlayersCollection();
    private TestPlayer zohra;
    private TestPlayer jose;
    private TestPlayer luis;

    @BeforeEach
    void setUp(){
        playersCollection.deleteMany(new Document());
        List<TestPlayer> players = new ArrayList<>();
        List<Document> playersDocList = new ArrayList<>();
        zohra = new TestPlayer("Zohra", "zohra@gmail.com", true);
        jose = new TestPlayer("Jose", "jose@gmail.com", true);
        luis = new TestPlayer("Luis", "luis@gmail.com", false);
        players.add(zohra);
        players.add(jose);
        players.add(luis);

        zohra.setTicketsBought(List.of(new TestTicket(20, "Misión Estelar"), new TestTicket(15, "Misterio de Egipto")));
        jose.setTicketsBought(List.of(new TestTicket(10, "Viaje a la luna"), new TestTicket(15, "Misterio de Egipto")));
        luis.setTicketsBought(List.of(new TestTicket(20, "Misión Estelar")));


        for (TestPlayer player : players) {
            playersDocList.add(testPlayerToDoc(player));
        }
        playersCollection.insertMany(playersDocList);

    }

    @Test
    void getTotalSales_ShouldReturnTotalofAllTickets(){
        TicketDAOImpl ticketDAO = new TicketDAOImpl();
        int totalSales = ticketDAO.getTotalSales();
        System.out.println("Inserted docs: ");
        for (Document doc : playersCollection.find()) {
            System.out.println(doc.toJson());
        }
        assertEquals(80, totalSales);

    }

    private Document testPlayerToDoc(TestPlayer testPlayer){
        Document doc = new Document("name", testPlayer.getName())
                .append("email", testPlayer.getEmail())
                .append("isSubscribed", testPlayer.isSubscribed());

        List<Document> ticketDocs = new ArrayList<>();
        for (Ticket testTicket : testPlayer.getTicketsBought()) {
            ticketDocs.add(new Document("price", testTicket.getPrice())
                    .append("room", testTicket.getRoom()));
        }

        doc.append("ticketsBought", ticketDocs);
        return doc;
    }



    @Getter @Setter
    static class TestTicket extends Ticket {
        public TestTicket(int price, String room){
            super(price, room);
        }
    }

    @Getter @Setter
    static class TestPlayer extends Player{
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
