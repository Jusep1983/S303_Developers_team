package dtos;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class PlayerDTO {

    private final ObjectId id;
    private final String name;

    public PlayerDTO(ObjectId id, String name) {
        this.id = id;
        this.name = name;
    }
}
