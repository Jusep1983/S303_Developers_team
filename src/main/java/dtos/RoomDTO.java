package dtos;

import entities.enums.Difficulty;
import org.bson.types.ObjectId;

public class RoomDTO {
    private final ObjectId id;
    private final String name;
    private final Difficulty difficulty;

    public RoomDTO(ObjectId id, String name, Difficulty difficulty) {
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
    }

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Difficulty getDifficulty() {return difficulty;}

}

