package entities;

import entities.enums.Theme;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@Data
public class Clue {

    @BsonId
    private ObjectId id;
    private int price;
    private String name;
    private Theme theme;
}
