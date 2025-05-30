package entities;

import entities.enums.Material;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@AllArgsConstructor
@Data
@Builder
public class Decoration {
    @BsonId
    private ObjectId id;
    private int price;
    private String name;
    private Material material;

}
