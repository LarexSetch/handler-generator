package ru.airux.codegen.handlerbuilder.handler;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class HandlerView extends VBox {
    private final GridPane gridPane = new GridPane();
    private final TextField textFieldName = new TextField();
    private final TextField textFieldUri = new TextField();
    private final TextArea textAreaObjectDeclaration = new TextArea();
    private final Button buttonChooseDirectory = new Button("Choose directory");
    private final DirectoryChooser directoryChooser = new DirectoryChooser();

    private final ButtonBar buttonBar = new ButtonBar();
    private final Button buttonGenerate = new Button("Generate");

    private final HandlerViewModel handlerModel = new HandlerViewModel();
    private final Stage stage;

    public HandlerView(Stage stage) {
        this.stage = stage;
        createView();
        bindModel();
    }

    private void createView() {
        VBox wrapper = new VBox();
//        wrapper.setAlignment(Pos.CENTER);
//        directoryChooser.showDialog()

        buttonChooseDirectory.setOnAction(actionEvent -> handlerModel.rootDirectoryProperty().setValue(directoryChooser.showDialog(stage)));

        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(4);

        addRow(new Label("Root directory"), buttonChooseDirectory, 0);
        addRow(new Label("Name"), textFieldName, 1);
        addRow(new Label("URI"), textFieldUri, 2);

        final ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(50);
        gridPane.getColumnConstraints().addAll(columnConstraints, columnConstraints);

        wrapper.getChildren().add(gridPane);

        createButtons();
        textAreaObjectDeclaration.setStyle("-fx-font-family: monospace");

        this.getChildren().addAll(
                wrapper,
                new Separator(),
                textAreaObjectDeclaration,
                new Separator(),
                buttonBar
        );
    }

    private void createButtons() {
        buttonGenerate.setOnAction(this::generate);
        buttonGenerate.setDefaultButton(true);
        buttonBar.setPadding(new Insets(20.0d));
        ButtonBar.setButtonData(buttonGenerate, ButtonBar.ButtonData.OK_DONE);
        buttonBar.getButtons().addAll(buttonGenerate); // todo add with index
    }

    private void generate(ActionEvent actionEvent) {
        try {
            handlerModel.generate();
        } catch (Exception e) {
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error handed");
            alert.setContentText(e.toString());
            throw new RuntimeException(e);
        }
    }

    private void addRow(Node node1, Node node2, int row) {
        gridPane.add(node1, 0, row);
        gridPane.add(node2, 1, row);
    }

    private void bindModel() {
        textFieldName.textProperty().bindBidirectional(handlerModel.nameProperty());
        textFieldUri.textProperty().bindBidirectional(handlerModel.uriProperty());
        textAreaObjectDeclaration.textProperty().bindBidirectional(handlerModel.objectDeclarationProperty());
    }
}
