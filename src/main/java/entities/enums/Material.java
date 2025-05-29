package entities.enums;

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
