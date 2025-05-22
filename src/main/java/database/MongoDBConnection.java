package database;

import com.mongodb.MongoException;
import com.mongodb.client.*;
import org.bson.Document;

// The connection instance to Mongo DB is made through a singleton
public class MongoDBConnection {
    private static MongoDatabase database;
    private static MongoClient mongoClient;

    private MongoDBConnection() {
    }

    public static MongoDatabase getInstance() {
        if (database == null) {
            try {
                mongoClient = MongoClients.create("mongodb://localhost:27017");
                Document ping = new Document("ping", 1);
                mongoClient.getDatabase("EscapeRoom").runCommand(ping);
                return mongoClient.getDatabase("EscapeRoom");
            } catch (MongoException e) {
                System.out.println("Error creating MongoDB client: " + e.getMessage());
                return null;
            }
        }
        return database;
    }

    public static MongoCollection<Document> getPlayersCollection() {
        return database.getCollection("players");
    }

    public static MongoCollection<Document> getEscapeRoomCollection() {
        return database.getCollection("escapeRoom");
    }
}
