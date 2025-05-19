package entities;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Room {

    String name;
    enum difficulty {EASY, MEDIUM, HARD}
    int price;
    List<Decoration> decorations;
    List<Clue> clues;

}
