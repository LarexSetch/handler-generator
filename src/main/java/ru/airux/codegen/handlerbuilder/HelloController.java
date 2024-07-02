package ru.airux.codegen.handlerbuilder;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class HelloController {
    @FXML
    public TextField context;
    @FXML
    public TextField uri;
    @FXML
    public ChoiceBox contentType;

    @FXML
    protected void onHelloButtonClick() {
        System.out.println(context.getText());
        System.out.println(uri.getText());
    }
}