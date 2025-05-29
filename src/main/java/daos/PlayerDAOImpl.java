package daos;

import com.mongodb.client.MongoCollection;
import daos.interfaces.PlayerDAO;
import database.MongoDBConnection;
import dtos.PlayerDTO;
import entities.Player;
import entities.Ticket;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class PlayerDAOImpl implements PlayerDAO {

    private final MongoCollection<Document> playersCollection;

    public PlayerDAOImpl() {
        playersCollection = MongoDBConnection.getPlayersCollection();
    }

    @Override
    public void save(Player player) {
        Document doc = new Document(
                "_id", player.getId())
                .append("name", player.getName())
                .append("email", player.getEmail())
                .append("isSubscribed", player.isSubscribed());
        playersCollection.insertOne(doc);
    }

    @Override
    public void update(Player player) {
        List<Document> ticketDocs = player.getTicketsBought().stream().map(TicketDAOImpl::ticketToDocument).toList();
        Document updated = new Document("$set", new Document("name", player.getName())
                .append("email", player.getEmail())
                .append("isSubscribed", player.isSubscribed())
                .append("ticketsBought", ticketDocs));
        playersCollection.updateOne(new Document("_id", player.getId()), updated);
    }

    @Override
    public List<Player> findAll() {
        List<Player> players = new ArrayList<>();
        for (Document doc : playersCollection.find()) {
            players.add(documentToPlayer(doc));
        }
        return checkEmptyList(players);
    }

    private Player documentToPlayer(Document doc) {
        Player player = new Player(
                doc.getObjectId("_id"),
                doc.getString("name"),
                doc.getString("email")
        );
        player.setSubscribed(doc.getBoolean("isSubscribed", true));

        List<Document> ticketDocs = doc.getList("ticketsBought", Document.class);
        if (ticketDocs != null) {
            List<Ticket> tickets = new ArrayList<>();
            for (Document ticketDoc : ticketDocs) {
                tickets.add(TicketDAOImpl.documentToTicket(ticketDoc));
            }
            player.setTicketsBought(tickets);
        }
        return player;
    }

    private <T> List<T> checkEmptyList(List<T> list) {
        if (list.isEmpty()) {
            System.out.println("Players list is empty");
        }
        return list;
    }
}
