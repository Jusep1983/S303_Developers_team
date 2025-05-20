package database;

import com.mongodb.MongoException;
import com.mongodb.client.*;

// The connection instance to Mongo DB is made through a singleton
public class MongoDBConnection {
    private static MongoClient mongoClient;

    private MongoDBConnection() {
    }

    public static MongoClient getInstance() {
        if (mongoClient == null) {
            try {
                mongoClient = MongoClients.create("mongodb://localhost:27017");
            } catch (MongoException e) {
                System.out.println("Error creating MongoDB client: " + e.getMessage());
                return null;
            }
        }
        return mongoClient;
    }

}
