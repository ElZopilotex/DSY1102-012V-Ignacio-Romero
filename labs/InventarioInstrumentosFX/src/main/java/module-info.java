module cl.duoc.inventarioinstrumentosfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    opens cl.duoc.inventarioinstrumentosfx to javafx.fxml;
    opens cl.duoc.inventarioinstrumentosfx.model to com.fasterxml.jackson.databind;
    //opens cl.duoc.inventarioinstrumentosfx.controller to javafx.fxml;

    exports cl.duoc.inventarioinstrumentosfx;
}