package entities;

import entities.enums.Theme;
import lombok.Data;

@Data
public class Clue {

    int price;
    String name;
    Theme theme;
}
