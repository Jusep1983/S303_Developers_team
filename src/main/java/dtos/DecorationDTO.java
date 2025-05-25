package dtos;

import org.bson.types.ObjectId;

public class DecorationDTO {
    private final ObjectId id;
    private final String name;

    public DecorationDTO(ObjectId id, String name) {
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
