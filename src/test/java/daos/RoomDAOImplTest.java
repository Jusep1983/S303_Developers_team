package daos;

import com.mongodb.client.MongoCollection;
import entities.Clue;
import entities.Decoration;
import entities.Room;
import entities.enums.Difficulty;
import observer.NotificationService;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoomDAOImplTest {
    //crea un mock de las dependencias
    @Mock
    private MongoCollection<Document> mockEscapeRoomCollection;
    @Mock
    private NotificationService mockNotificationService;

    @InjectMocks
    //inyecta los mocks dentro de la instancia
    private RoomDAOImpl roomDAO;

    @BeforeEach
    void setUp(){
        // Activar las annotaciones Mockito
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void save_ShouldInsertDocAndNotifySuscribers(){
        List<Decoration> decos= new ArrayList<>();
        List<Clue> clues= new ArrayList<>();

        Room room = new Room(new ObjectId(), "NewMoon Mysteries", 1500, Difficulty.EASY, decos, clues);

        roomDAO.save(room);

        ArgumentCaptor<Document> captor = ArgumentCaptor.forClass(Document.class);
        // captura los argumentos que pasamos al método
        verify(mockEscapeRoomCollection).insertOne(captor.capture());

        Document doc = captor.getValue();
        assertEquals("NewMoon Mysteries", doc.getString("name"));
        assertEquals(1500, doc.getInteger("price"));
        assertEquals(Difficulty.EASY, doc.get("difficulty"));

        verify(mockNotificationService).notifyNewRoomToAllSubscribedPlayers(room);
    }

}
