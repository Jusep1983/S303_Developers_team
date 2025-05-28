package dtos;

import entities.enums.Difficulty;
import org.bson.types.ObjectId;

public record RoomDTO(ObjectId id, String name, Difficulty difficulty) {}

