package ru.airux.codegen.handlerbuilder.view.php;

import java.util.List;

public record ObjectViewModel(
        String namespace,
        String name,
        String schemaName,
        String title,
        List<Property> properties
) {
    public record Property(
            boolean isRequired,
            Type type,
            String name,
            String serializedName,
            String title,
            List<String> acceptedValues
    ) {
        public record Type(
                String scalar,
                String reference
        ) {
        }
    }
}
