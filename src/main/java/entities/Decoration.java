package entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class Decoration {

    private String id;
    private int price;
    private String name;
    private Material material;

    @Getter
    public enum Material {
        PLASTIC("Light and cheap plastic"),
        METAL("Durable and shiny metal"),
        GLASS("Fragile but elegant glass"),
        WOOD("Natural and warm wood");

        private final String description;

        Material(String description) {
            this.description = description;
        }

    }

}
