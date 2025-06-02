package entities.enums;

public enum Difficulty {
    EASY, MEDIUM, HARD;

    public int getDurationMinutes() {
        return switch (this) {
            case EASY -> 45;
            case MEDIUM -> 60;
            case HARD -> 75;
        };
    }

    public int getPriceByDifficulty() {
        return switch (this) {
            case EASY -> 15;
            case MEDIUM -> 20;
            case HARD -> 25;
        };
    }
}