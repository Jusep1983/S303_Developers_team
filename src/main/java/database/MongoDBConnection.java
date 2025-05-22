package database;

import com.mongodb.MongoException;
import com.mongodb.client.*;
import org.bson.Document;

// The connection instance to Mongo DB is made through a singleton
public class MongoDBConnection {
    private static MongoClient mongoClient;

    private MongoDBConnection() {
    }

    public static MongoDatabase getInstance() {
        if (mongoClient == null) {
            try {
                mongoClient = MongoClients.create("mongodb://localhost:27017");
                Document ping = new Document("ping", 1);
                mongoClient.getDatabase("escapeRoom").runCommand(ping);
            } catch (MongoException e) {
                System.out.println("Error creating MongoDB client: " + e.getMessage());
                return null;
            }
        }
        return mongoClient.getDatabase("escapeRoom");
    }

}
