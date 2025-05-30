package managers;

import daos.interfaces.DecorationDAO;
import dtos.DecorationDTO;
import dtos.RoomDTO;
import entities.Decoration;
import entities.enums.Difficulty;
import entities.enums.Material;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validation.ValidateInputs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DecorationManagerTest {

    private DecorationManager manager;
    private RoomDTO mockRoom;

    @BeforeEach
    void setUp() {
        // Se crean mocks
        RoomManager mockRoomManager = mock(RoomManager.class);
        DecorationDAO mockDecorationDAO = mock(DecorationDAO.class);
        // Se espía el manager real con dependencias mockeadas
        manager = spy(new DecorationManager(mockRoomManager, mockDecorationDAO));

        mockRoom = new RoomDTO(new ObjectId(), "Test Room", Difficulty.HARD);
        List<DecorationDTO> decorations = List.of(
                new DecorationDTO(new ObjectId(), "Deco1"),
                new DecorationDTO(new ObjectId(), "Deco2")
        );
        // Cuando se llama a getAllDecorationsDTO con el ID de la sala mock, devuelve la lista anterior
        doReturn(decorations).when(manager).getAllDecorationsDTO(mockRoom.id());
    }

    @Test
    void givenValidData_whenCreateDecorationFromData_thenReturnsDecorationWithCorrectFields() {
        DecorationManager decorationManager = new DecorationManager(null, null);
        Decoration decoration = decorationManager.createDecorationFromData("Butaca", 300, Material.WOOD);

        assertNotNull(decoration.getId());
        assertEquals("Butaca", decoration.getName());
        assertEquals(300, decoration.getPrice());
        assertEquals(Material.WOOD, decoration.getMaterial());
    }

    @Test
    void givenEmptyDecorationList_whenChooseDecoration_thenReturnsZero() {
        DecorationManager decorationManager = new DecorationManager(null, null);
        List<DecorationDTO> emptyList = new ArrayList<>();
        int choice = decorationManager.chooseDecoration("delete", emptyList);

        assertEquals(0, choice);
    }

    @Test
    void givenDecorationsList_whenChooseDecoration_thenReturnsSelectedChoice() {
        DecorationManager manager = new DecorationManager(null, null);

        // Se mockea el metodo estático ValidateInputs.validateIntegerBetweenOnRange para devolver siempre 2
        try (var mockedValidate = mockStatic(ValidateInputs.class)) {
            mockedValidate.when(() -> ValidateInputs.validateIntegerBetweenOnRange(anyString(), anyInt(), anyInt()))
                    .thenReturn(2);

            List<DecorationDTO> decorations = List.of(
                    new DecorationDTO(new ObjectId(), "Deco1"),
                    new DecorationDTO(new ObjectId(), "Deco2")
            );

            int choice = manager.chooseDecoration("delete", decorations);

            assertEquals(2, choice);
        }
    }

    @Test
    void givenDecorationsAndChoiceZero_whenSelectDecoration_thenReturnsEmpty() {
        // Simula que el usuario elige 0
        doReturn(0).when(manager).chooseDecoration(anyString(), anyList());

        Optional<DecorationDTO> result = manager.selectDecoration("delete", mockRoom);

        assertTrue(result.isEmpty());
    }

    @Test
    void givenDecorationsAndChoiceOne_whenSelectDecoration_thenReturnsDecoration() {
        doReturn(1).when(manager).chooseDecoration(anyString(), anyList());

        Optional<DecorationDTO> result = manager.selectDecoration("delete", mockRoom);

        assertTrue(result.isPresent());
        assertEquals("Deco1", result.get().name());
    }

}
