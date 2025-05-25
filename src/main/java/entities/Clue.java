package entities;

import entities.enums.Theme;
import lombok.Builder;
import lombok.Data;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

@Data
@Builder
public class Clue {

    @BsonId
    private ObjectId id;
    private int price;
    private String name;
    private Theme theme;

    public Clue(int price, String name, Theme theme){
        this.id = new ObjectId();
        this.price = price;
        this.name = name;
        this.theme = theme;
    }

    public Clue(ObjectId id, int price, String name, Theme theme){
        this.id = id;
        this.price = price;
        this.name = name;
        this.theme = theme;

    }
}