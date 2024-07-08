package ru.airux.codegen.handlerbuilder.view.php;

public class HandlerView {
    public String render(HandlerViewModel model) {
        var builder = new StringBuilder();
        var tab = "    ";
        builder.append("<?php\n\n");
        builder.append("declare(strict_types=1);\n\n");
        builder.append("namespace ").append(model.namespace()).append(";\n\n");
        builder.append("use OpenApi\\Attributes as OA;\n\n");
        builder.append("interface ").append(model.name()).append("Interface\n");
        builder.append("{\n");
        builder.append(tab).append("#[OA\\").append(model.methodAnnotation()).append("(\n");
        builder.append(tab).append(tab).append("path: '").append(model.url()).append("',\n");
        builder.append(tab).append(tab).append("requestBody: new OA\\RequestBody(\n");
        builder.append(tab).append(tab).append(tab).append("content: new OA\\JsonContent(\n");
        if (null != model.requestClassName()) {
            builder.append(tab).append(tab).append(tab).append(tab)
                    .append("ref: ").append(model.requestClassName()).append("::class\n");
        }
        builder.append(tab).append(tab).append(tab).append(")\n");
        builder.append(tab).append(tab).append("))]\n");
        builder.append(tab).append("#[OA\\Response(\n");
        builder.append(tab).append(tab).append("response: 200,\n");
        builder.append(tab).append(tab).append("description: '").append(model.description()).append("',\n");
        builder.append(tab).append(tab).append("content: new OA\\JsonContent(\n");
        if (null != model.responseClassName()) {
            builder.append(tab).append(tab).append(tab)
                    .append("ref: ").append(model.responseClassName()).append("::class\n");
        }
        builder.append(tab).append(tab).append("))]\n");
        builder.append(tab).append("public function handle(");
        if (null != model.requestClassName()) {
            builder.append(model.requestClassName()).append(" $request");
        }
        builder.append("): ");
        if (null != model.responseClassName()) {
            builder.append(model.responseClassName());
        } else {
            builder.append("void");
        }
        builder.append(";\n");
        builder.append("}\n");

        return builder.toString();
    }
}
