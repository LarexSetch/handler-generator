package ru.airux.codegen.handlerbuilder.handler;

import java.io.File;

public record Handler(
        File directoryRoot,
        String handlerName,
        String uri,
        String objectDeclaration
) {
}
