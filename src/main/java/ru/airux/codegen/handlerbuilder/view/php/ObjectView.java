package ru.airux.codegen.handlerbuilder.view.php;

import java.util.List;

public class ObjectView {
    private final String tab = "    ";

    public String render(ObjectViewModel model) {
        var builder = new StringBuilder();
        builder.append("<?php\n\n");
        builder.append("declare(strict_types=1);\n\n");
        builder.append("namespace ").append(model.namespace()).append(";\n\n");
        builder.append("use OpenApi\\Attributes as OA;\n\n");
        builder.append("#[OA\\Schema(\n");
        builder.append(tab).append("schema: '").append(model.schemaName()).append("',\n");
        builder.append(tab).append("title: '").append(model.title()).append("',\n");
        buildRequiredProperties(builder, model.properties());
        builder.append(")]\n");
        builder.append("final class ").append(model.name()).append("\n");
        builder.append("{\n");
        builder.append(tab).append("public function __construct(\n");
        model.properties().forEach(property -> buildProperty(builder, property));
        builder.append(tab).append(") {\n");
        builder.append(tab).append("}\n");
        builder.append("}\n");

        return builder.toString();
    }

    private void buildProperty(StringBuilder builder, ObjectViewModel.Property property) {
        if (null != property.serializedName()) {
            builder.append(tab).append(tab).append("#[OA\\Property(\n");
            builder.append(tab).append(tab).append(tab)
                    .append("property: '").append(property.serializedName()).append("',\n");
            builder.append(tab).append(tab).append(tab)
                    .append("title: '").append(property.title()).append("',\n");
            if (null != property.type().scalar()) {
                builder.append(tab).append(tab).append(tab)
                        .append("type: '").append(property.type().scalar()).append("',\n");
            } else {
                builder.append(tab).append(tab).append(tab).append("type: 'object',\n");
                builder.append(tab).append(tab).append(tab)
                        .append("ref: ").append(property.type().reference()).append("::class,\n");
            }
            builder.append(tab).append(tab).append(")]\n");
        }

        var propertyType = (!property.isRequired() ? "?" : "");
        if (null != property.type().scalar()) {
            propertyType += property.type().scalar();
        } else {
            propertyType += property.type().reference();
        }

        builder.append(tab).append(tab)
                .append("public readonly ").append(propertyType)
                .append(" $").append(property.name()).append(",\n");
    }

    private void buildRequiredProperties(StringBuilder builder, List<ObjectViewModel.Property> properties) {
        var requiredProperties = properties.stream()
                .filter(property -> property.isRequired() && null != property.serializedName())
                .toList();
        if (!requiredProperties.isEmpty()) {
            builder.append(tab).append("required: [\n");
            requiredProperties.forEach(property -> builder.append(tab).append(tab).append("'").append(property.serializedName()).append("',\n"));
            builder.append(tab).append("],\n");
        }
    }
}
