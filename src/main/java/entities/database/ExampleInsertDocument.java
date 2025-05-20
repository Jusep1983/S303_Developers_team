package entities.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import java.util.Arrays;

public class ExampleInsertDocument {

    // Ejemplo demostración de como insertar un documento
    public void exampleDemo() {

        // Crear conexión mediante un singleton
        MongoClient client = MongoDBConnection.getInstance();

        if (client == null) {
            System.out.println("No se ha podido conectar al cliente");
        }else{
            // Obtener la base de datos
            MongoDatabase database = client.getDatabase("escapeRoom");

            // Obtener la colección (la crea si no existe)
            MongoCollection<Document> roomsCollection = database.getCollection("rooms");

            Document roomDoc = new Document("name", "sala espacio")
                    .append("decorations", Arrays.asList(
                            //Los documentos embebidos mongo no les genera id, por lo tanto, es generado manual
                            new Document("_id", new ObjectId()).append("name", "cuchara").append("material", "plástico"),
                            new Document("_id", new ObjectId()).append("name", "vaso").append("material", "metal")
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
