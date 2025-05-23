package database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class InitialDataLoader {
    public static void loadInitialRoomsIfDatabaseIsEmpty(MongoDatabase database, String jsonFilePath ) {
        MongoCollection<Document> escapeRoomCollection = database.getCollection("escapeRoom");

        if (escapeRoomCollection.countDocuments() != 0) {
            System.out.println("Skipping initial data load.");
        } else {
            try {
                String jsonFile = new String(Files.readAllBytes(Paths.get(jsonFilePath)));

                List<Document> documents = Document.parse("{\"array\":" + jsonFile + "}").getList("array", Document.class);

                escapeRoomCollection.insertMany(documents);
                System.out.println("Initial data inserted into 'escapeRoom' collection.");

            } catch (Exception e) {
                System.out.println("Error loading initial data: " + e.getMessage());
            }
        }
    }


    }

