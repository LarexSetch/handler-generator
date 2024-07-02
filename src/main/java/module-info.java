module ru.airux.codegen.handlerbuilder {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens ru.airux.codegen.handlerbuilder to javafx.fxml;
    exports ru.airux.codegen.handlerbuilder;
    exports ru.airux.codegen.handlerbuilder.handler;
    opens ru.airux.codegen.handlerbuilder.handler to javafx.fxml;
}