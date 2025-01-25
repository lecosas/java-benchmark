package io.backendscience.javabenchmark.model;

public record Todo (
        int userId,
        int id,
        String title,
        boolean completed
) {}
