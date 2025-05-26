package dtos;

import lombok.Data;
import org.bson.types.ObjectId;

@Data
public class DecorationDTO {
    private final ObjectId id;
    private final String name;

    public DecorationDTO(ObjectId id, String name) {
        this.id = id;
        this.name = name;
    }
}
