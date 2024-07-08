package ru.airux.codegen.handlerbuilder.handler;

import ru.airux.codegen.handlerbuilder.DataObjectParser;
import ru.airux.codegen.handlerbuilder.view.php.HandlerView;
import ru.airux.codegen.handlerbuilder.view.php.HandlerViewModel;
import ru.airux.codegen.handlerbuilder.view.php.ObjectView;
import ru.airux.codegen.handlerbuilder.view.php.ObjectViewModel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.stream.Collectors;

public class HandlerModel {
    private final DataObjectParser parser = new DataObjectParser();

    public void save(Handler handler) throws Exception {
        var dataObjects = parser.parse(handler.objectDeclaration());
        var handlerViewModel = new HandlerViewModel(
                resolveHandlerName(handler.handlerName()),
                resolveHandlerNamespace(handler.handlerName()),
                resolveHandlerMethod(handler.uri()),
                resolveHandlerUrl(handler.uri()),
                "",
                resolveHandlerRequest(dataObjects),
                resolveHandlerResponse(dataObjects)
        );
        var handlerView = new HandlerView();
        BufferedWriter writer;
        var handlerTarget = handler.directoryRoot() + "/" + handlerViewModel.name() + ".php";
        var handlerData = handlerView.render(handlerViewModel);
        writer = new BufferedWriter(new FileWriter(handlerTarget));
        writer.write(handlerData);
        writer.close();
        System.out.println(handlerTarget);
        System.out.println(handlerData);


        var objectView = new ObjectView();
        for (var dataObject : dataObjects) {
            var target = handler.directoryRoot() + "/" + dataObject.name() + ".php";
            var data = objectView.render(new ObjectViewModel(
                    resolveHandlerNamespace(handler.handlerName()),
                    dataObject.name(),
                    dataObject.schemaName(),
                    dataObject.comment(),
                    dataObject.properties().stream().map(property ->
                                    new ObjectViewModel.Property(
                                            property.required(),
                                            resolvePropertyType(property),
                                            property.name(),
                                            property.schemaName(),
                                            property.comment(),
                                            null
                                    ))
                            .collect(Collectors.toList())
            ));
            writer = new BufferedWriter(new FileWriter(target));
            writer.write(data);
            writer.close();
            System.out.println(target);
            System.out.println(data);
        }


    }

    private ObjectViewModel.Property.Type resolvePropertyType(DataObjectParser.DataObject.Property property) {
        return List.of("int", "string", "object", "double", "float").contains(property.type())
                ? new ObjectViewModel.Property.Type(
                property.type(),
                null
        )
                : new ObjectViewModel.Property.Type(
                null,
                property.type()
        );
    }

    private String resolveHandlerRequest(List<DataObjectParser.DataObject> dataObjects) {
        return dataObjects.stream().filter(dataObject -> dataObject.type() == DataObjectParser.DataObject.Type.REQUEST).map(DataObjectParser.DataObject::name).findFirst().orElse(null);
    }

    private String resolveHandlerResponse(List<DataObjectParser.DataObject> dataObjects) {
        return dataObjects.stream().filter(dataObject -> dataObject.type() == DataObjectParser.DataObject.Type.RESPONSE).map(DataObjectParser.DataObject::name).findFirst().orElse(null);
    }

    private String resolveHandlerUrl(String uri) {
        var contextParts = uri.split(" ");

        return contextParts[contextParts.length - 1];
    }

    private HandlerViewModel.Method resolveHandlerMethod(String uri) throws Exception {
        var contextParts = uri.split(" ");

        return switch (contextParts[0]) {
            case "POST" -> HandlerViewModel.Method.POST;
            case "PUT" -> HandlerViewModel.Method.PUT;
            case "GET" -> HandlerViewModel.Method.GET;
            case "PATCH" -> HandlerViewModel.Method.PATCH;
            case "OPTION" -> HandlerViewModel.Method.OPTION;
            default -> throw new Exception("Method " + contextParts[0] + " unsupported");
        };
    }

    private String resolveHandlerNamespace(String context) {
        var namespace = new StringBuilder();
        var contextParts = context.split("\\.");
        for (int i = 0; i < contextParts.length - 1; i++) {
            if (i != 0) {
                namespace.append("\\");
            }
            namespace.append(contextParts[i]);
        }

        return namespace.toString();
    }

    private String resolveHandlerName(String context) {
        var contextParts = context.split("\\.");

        return contextParts[contextParts.length - 1];
    }
}
