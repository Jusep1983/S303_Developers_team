package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@Data
@AllArgsConstructor
public class Ticket {

    @BsonId
    private ObjectId id;
    private int price;
}