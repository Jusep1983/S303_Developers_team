package dtos;

import org.bson.types.ObjectId;

public record PlayerDTO(ObjectId id, String name) {}
