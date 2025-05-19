package entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RoomTest {

    Room testRoom = Room.builder()
            .name("TestRoom")
            .price(9_000)
            .build();

    @Test
    void givenRoomWithName_whenGettingName_thenNameReturned() {
        Assertions.assertEquals("TestRoom", testRoom.getName());
    }

    @Test
    void givenRoomWithPrice_whenGettingPrice_thenPriceReturned() {
        Assertions.assertEquals(9_000, testRoom.getPrice());
    }
}
