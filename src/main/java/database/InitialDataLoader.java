package database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class InitialDataLoader {
    public static void loadInitialRoomsIfDatabaseIsEmpty(MongoDatabase database, String roomsJsonPath, String playersJsonPath ) {
        MongoCollection<Document> escapeRoomCollection = database.getCollection("escapeRoom");
        MongoCollection<Document> playersCollection = database.getCollection("players");


        if (escapeRoomCollection.countDocuments() != 0 && playersCollection.countDocuments() != 0) {
            System.out.println("Skipping initial data load.");
        } else {
            try {
                String jsonRoomsFile = new String(Files.readAllBytes(Paths.get(roomsJsonPath)));
                List<Document> roomsDocuments = Document.parse("{\"array\":" + jsonRoomsFile + "}").getList("array", Document.class);
                escapeRoomCollection.insertMany(roomsDocuments);
                System.out.println("Initial data inserted into 'escapeRoom' collection.");

                String jsonPlayersFile = new String(Files.readAllBytes(Paths.get(playersJsonPath)));
                List<Document> playersDocuments = Document.parse("{\"array\":" + jsonPlayersFile + "}").getList("array", Document.class);
                playersCollection.insertMany(playersDocuments);
                System.out.println("Initial data inserted into 'players' collection.");


            } catch (Exception e) {
                System.out.println("Error loading initial data: " + e.getMessage());
            }
        }
    }


    }

