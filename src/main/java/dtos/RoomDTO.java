package dtos;

import entities.enums.Difficulty;
import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class RoomDTO {
    private final ObjectId id;
    private final String name;
    private final Difficulty difficulty;

    public RoomDTO(ObjectId id, String name, Difficulty difficulty) {
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
    }
}

