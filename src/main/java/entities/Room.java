package entities;

import entities.enums.Difficulty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Room {

    String name;
    int price;
    Difficulty difficulty;
    List<Decoration> decorations;
    List<Clue> clues;

}
