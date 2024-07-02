package ru.airux.codegen.handlerbuilder.handler;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class HandlerView extends VBox {
    private final GridPane gridPane = new GridPane();
    private final TextField textFieldContext = new TextField();
    private final TextField textFieldUri = new TextField();
    private final ChoiceBox<HandlerViewModel.ContentType> choiceBoxContentType = new ChoiceBox<>();

    private final Button buttonGenerate = new Button("Generate");

    private final HandlerViewModel handlerModel = new HandlerViewModel();

    public HandlerView() {
        createView();
        bindModel();
    }

    private void createView() {
        VBox wrapper = new VBox();
        wrapper.setAlignment(Pos.CENTER);

        gridPane.setPadding(new Insets(40));
        gridPane.setVgap(4);

        addRow(new Label("Context"), textFieldContext, 0);
        addRow(new Label("URI"), textFieldUri, 1);
        addRow(new Label("Content-type"), choiceBoxContentType, 2);

        final ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth( 50 );
        gridPane.getColumnConstraints().addAll(columnConstraints, columnConstraints);

        wrapper.getChildren().add(gridPane);

        VBox.setVgrow( wrapper, Priority.ALWAYS );
        buttonGenerate.setOnAction(this::generate);
        buttonGenerate.setDefaultButton(true);

        this.getChildren().addAll(
                wrapper,
                new Separator(),
                buttonGenerate
        );
    }

    private void generate(ActionEvent actionEvent) {
        handlerModel.generate();
    }

    private void addRow(Node node1, Node node2, int row) {
        gridPane.add(node1, 0, row);
        gridPane.add(node2, 1, row);
    }

    private void bindModel() {
        textFieldContext.textProperty().bindBidirectional(handlerModel.contextProperty());
        textFieldUri.textProperty().bindBidirectional(handlerModel.uriProperty());
        choiceBoxContentType.valueProperty().bindBidirectional(handlerModel.contentTypeProperty());
    }
}
