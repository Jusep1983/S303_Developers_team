package managers;

import com.mongodb.MongoException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import database.MongoDBConnection;
import org.bson.Document;

import java.util.List;
import java.util.Map;

public class EscapeRoomManager {
    MongoCollection<Document> escapeRoomCollection = MongoDBConnection.getEscapeRoomCollection();

    public Map<String, Integer> getInventoryCount() {
        return Map.of(
                "Rooms", getRoomCount(),
                "Clues", getClueCount(),
                "Decorations", getDecorationCount()
        );
    }

    private int getRoomCount() {
        return (int) this.escapeRoomCollection.countDocuments();
    }

    private int getClueCount() {
        int totalClues = 0;
        try (MongoCursor<Document> cursor = this.escapeRoomCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document room = cursor.next();
                if (room.containsKey("clues")) {
                    totalClues += ((List<?>) room.get("clues")).size();
                }
            }
        } catch (MongoTimeoutException e) {
            System.err.println("Error: connection to MongoDB timed out.");
            e.printStackTrace();
        } catch (MongoException e) {
            System.err.println("Error: general MongoDB connection error.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error: unexpected exception occurred.");
            e.printStackTrace();
        }
        return totalClues;
    }

    private int getDecorationCount() {
        int totalDecorations = 0;
        try (MongoCursor<Document> cursor = escapeRoomCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document room = cursor.next();
                if (room.containsKey("decorations")) {
                    totalDecorations += ((List<?>) room.get("decorations")).size();
                }
            }
        } catch (MongoTimeoutException e) {
            System.err.println("Error: connection to MongoDB timed out.");
            e.printStackTrace();
        } catch (MongoException e) {
            System.err.println("Error: general MongoDB connection error.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error: unexpected exception occurred.");
            e.printStackTrace();
        }
        return totalDecorations;
    }

    // Quizás los metodos addRoom() y editRoom() debería ir en RoomManager
    public void addRoom(Document roomDoc) {
        this.escapeRoomCollection.insertOne(roomDoc);
    }
    public void editRoom() {
    }

    public void showAllAssets() {
        try (MongoCursor<Document> cursor = escapeRoomCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document room = cursor.next();
                printRoom(room);
                printClues(room);
                printDecorations(room);
            }
        } catch (MongoTimeoutException e) {
            System.err.println("Error: connection to MongoDB timed out.");
            e.printStackTrace();
        } catch (MongoException e) {
            System.err.println("Error: general MongoDB connection error.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error: unexpected exception occurred.");
            e.printStackTrace();
        }
    }

    private void printRoom(Document room) {
        System.out.println("\nRoom: ");
        System.out.println(room.getString("name"));
    }

    private void printClues(Document room) {
        if (room.containsKey("clues")) {
            List<Document> clues = (List<Document>) room.get("clues");
            System.out.println("\tClues: ");
            for (Document clue : clues) {
                System.out.println("\t\t" + clue.getString("name"));
            }
        }
    }

    private void printDecorations(Document room) {
        if (room.containsKey("decorations")) {
            List<Document> decorations = (List<Document>) room.get("decorations");
            System.out.println("\tDecorations: ");
            for (Document decoration : decorations) {
                System.out.println("\t\t" + decoration.getString("name"));
            }
        }
    }

    public int getInventoryValue() {
        int totalPrice = 0;
        try (MongoCursor<Document> cursor = escapeRoomCollection.find().iterator()) {
            while (cursor.hasNext()) {
                Document room = cursor.next();
                totalPrice += getRoomPrice(room);
                totalPrice += getCluesPrice(room);
                totalPrice += getDecorationsPrice(room);
            }
        } catch (MongoTimeoutException e) {
            System.err.println("Error: connection to MongoDB timed out.");
            e.printStackTrace();
        } catch (MongoException e) {
            System.err.println("Error: general MongoDB connection error.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error: unexpected exception occurred.");
            e.printStackTrace();
        }
        return totalPrice;
    }

    private int getRoomPrice(Document room) {
        if (room.containsKey("price")) {
            return room.getInteger("price");
        }
        return 0;
    }

    private int getCluesPrice(Document room) {
        int cluesPrice = 0;
        if (room.containsKey("clues")) {
            List<Document> clues = (List<Document>) room.get("clues");
            for (Document clue : clues) {
                if (clue.containsKey("price")) {
                    cluesPrice += clue.getInteger("price");
                }
            }
        }
        return cluesPrice;
    }

    private int getDecorationsPrice(Document room) {
        int decorationsPrice = 0;
        if (room.containsKey("decorations")) {
            List<Document> decorations = (List<Document>) room.get("decorations");
            for (Document decoration : decorations) {
                if (decoration.containsKey("price")) {
                    decorationsPrice += decoration.getInteger("price");
                }
            }
        }
        return decorationsPrice;
    }

}
