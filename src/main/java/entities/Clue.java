package entities;

import lombok.Data;

@Data
public class Clue {

    int price;
    String name;
    enum theme {PUZZLE, PASSWORD, SYMBOL}
}
