package ru.airux.codegen.handlerbuilder.handler;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;

public class HandlerViewModel {
    private final StringProperty name = new SimpleStringProperty("app.handler.v1.user.Create");
    private final StringProperty uri = new SimpleStringProperty("POST /api/v1/user/{user_type}");
    private final StringProperty objectDeclaration = new SimpleStringProperty("request<ApiV1CreateUserRequest> CreateRequest // user creation request\n" +
            "Path<user_type> UserType userType\n" +
            "Json string username\n" +
            "Json string email\n" +
            "Json string password\n" +
            "Json<full_name> FullName fullname\n" +
            "\n" +
            "response<ApiV1CreateUserResponse> CreateResponse // succeed user created\n" +
            "Json int id\n" +
            "\n" +
            "object<ApiV1FullName> FullName\n" +
            "Json<first_name> string firstName\n" +
            "Json<last_name> string lastName\n" +
            "                \n" +
            "enum<ApiV1UserType> UserType\n" +
            "Case<client> CLIENT\n" +
            "Case<partner> PARTNER");
    private final ObjectProperty<File> rootDirectory = new SimpleObjectProperty<>();

    private final HandlerModel handlerModel = new HandlerModel();
    private final HandlerConverter handlerConverter = new HandlerConverter();

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getUri() {
        return uri.get();
    }

    public StringProperty uriProperty() {
        return uri;
    }

    public String getObjectDeclaration() {
        return objectDeclaration.get();
    }

    public StringProperty objectDeclarationProperty() {
        return objectDeclaration;
    }

    public File getRootDirectory() {
        return rootDirectory.get();
    }

    public ObjectProperty<File> rootDirectoryProperty() {
        return rootDirectory;
    }

    public void generate() throws Exception {
        handlerModel.save(handlerConverter.toHandler(this));
    }
}
