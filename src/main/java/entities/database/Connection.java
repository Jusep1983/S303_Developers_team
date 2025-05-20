package entities.database;

import com.mongodb.MongoException;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import java.util.Arrays;

public class Connection {
    public MongoClient createConnection() {

        try {
            return MongoClients.create("mongodb://localhost:27017");
        } catch (MongoException e) {
            System.out.println("Error al conectar a mongo db " + e.getMessage());
            return null;
        }

    }

    // Ejemplo demostración de como insertar un documento
    public void exampleDemo() {
        // Crear conexión
        Connection connection = new Connection();
        MongoClient client = connection.createConnection();

        if (client != null) {

            // Obtener la base de datos
            MongoDatabase database = client.getDatabase("escapeRoom");

            // Obtener la colección (la crea si no existe)
            MongoCollection<Document> roomsCollection = database.getCollection("rooms");
            ObjectId id = new ObjectId();  // generas un ObjectId tú mismo

            Document roomDoc = new Document("_id", id)
                    .append("name", "sala espacio")
                    .append("decorations", Arrays.asList(
                            new Document("name", "cuchara").append("material", "plastic"),
                            new Document("name", "vaso").append("material", "metal")
                    ));

            // Insertar documento
            roomsCollection.insertOne(roomDoc);

            // Recuperar y mostrar todos los documentos de rooms
            FindIterable<Document> results = roomsCollection.find();

            // Imprimir documento
            for (Document doc : results) {
                System.out.println(doc.toJson());
            }

            // Imprimir estilo json pretty
            for (Document doc : results) {
                String prettyJson = doc.toJson(JsonWriterSettings.builder().indent(true).build());
                System.out.println(prettyJson);
            }

            client.close();
        }
    }
}
