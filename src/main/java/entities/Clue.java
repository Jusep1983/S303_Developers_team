package entities;

import entities.enums.Theme;
import lombok.Data;

@Data
public class Clue {

    String id;
    int price;
    String name;
    Theme theme;
}
