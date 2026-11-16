module cl.duoc.tallerbicicletas {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens cl.duoc.tallerbicicletas.controlador to javafx.fxml;
    opens cl.duoc.tallerbicicletas.modelo to com.google.gson;

    exports cl.duoc.tallerbicicletas;
    exports cl.duoc.tallerbicicletas.controlador;
    exports cl.duoc.tallerbicicletas.modelo;
}