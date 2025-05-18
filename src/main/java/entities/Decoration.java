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

        PLASTIC("Plastic"),
        METAL("Metal"),
        GLASS("Glass"),
        WOOD("Wood");

        private final String description;

        Material(String description) {
            this.description = description;
        }

    }

}
