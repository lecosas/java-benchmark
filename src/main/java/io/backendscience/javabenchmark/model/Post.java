package io.backendscience.javabenchmark.model;

public record Post(
        int userId,
        int id,
        String title,
        String body
) {}
