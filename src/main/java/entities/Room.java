package entities;

import entities.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Room {

    @BsonId
    private ObjectId id;
    private String name;
    private int price;
    private Difficulty difficulty;
    private List<Decoration> decorations;
    private List<Clue> clues;

}
