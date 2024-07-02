package ru.airux.codegen.handlerbuilder.handler;

public record Handler(
        String context,
        String uri,
        ContentType contentType
) {
    enum ContentType {
        JSON
    }
}
