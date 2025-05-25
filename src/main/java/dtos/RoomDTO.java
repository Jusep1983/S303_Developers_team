package dtos;

import org.bson.types.ObjectId;

public class RoomDTO {
    private final ObjectId id;
    private final String name;

    public RoomDTO(ObjectId id, String name) {
        this.id = id;
        this.name = name;
    }

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}

