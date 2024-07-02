package ru.airux.codegen.handlerbuilder.handler;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class HandlerViewModel {
    private final StringProperty context = new SimpleStringProperty("");
    private final StringProperty uri = new SimpleStringProperty("/api");
    private final ObjectProperty<ContentType> contentType = new SimpleObjectProperty<>(ContentType.JSON);

    private final HandlerModel handlerModel = new HandlerModel();
    private final HandlerConverter handlerConverter = new HandlerConverter();


    public String getContext() {
        return context.get();
    }

    public StringProperty contextProperty() {
        return context;
    }

    public String getUri() {
        return uri.get();
    }

    public StringProperty uriProperty() {
        return uri;
    }

    public ContentType getContentType() {
        return contentType.get();
    }

    public ObjectProperty<ContentType> contentTypeProperty() {
        return contentType;
    }

    public void generate() {
        handlerModel.save(handlerConverter.toHandler(this));
    }

    public enum ContentType {
        JSON
    }
}
