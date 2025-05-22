package database;

import com.mongodb.MongoSecurityException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.*;
import org.bson.Document;

public class MongoDBConnection {
    private static MongoDatabase database;
    private static MongoClient mongoClient;

    static {
        try {
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            database = mongoClient.getDatabase("miBaseDeDatos");
        } catch (MongoTimeoutException e) {
            System.err.println("No se pudo conectar a MongoDB: tiempo de espera agotado.");
            e.printStackTrace();
        } catch (MongoSecurityException e) {
            System.err.println("Error de autenticación con MongoDB.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado al conectar con MongoDB.");
            e.printStackTrace();
        }
    }

    public static MongoDatabase getInstance() {
        return database;
    }

    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    public static MongoCollection<Document> getPlayersCollection() {
        return database.getCollection("players");
    }

    public static MongoCollection<Document> getEscapeRoomCollection() {
        return database.getCollection("escapeRoom");
    }
}
