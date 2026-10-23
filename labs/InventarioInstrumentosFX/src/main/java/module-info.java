module cl.duoc.inventarioinstrumentosfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens cl.duoc.inventarioinstrumentosfx to javafx.fxml;
    exports cl.duoc.inventarioinstrumentosfx;
}