module cl.duoc.recintos.reservarecintosfx {
    requires javafx.controls;
    requires javafx.fxml;

    // Ocultar/abrir paquetes para que JavaFX pueda acceder a los controladores y modelos
    opens cl.duoc.recintos.reservarecintosfx to javafx.fxml;
    opens cl.duoc.recintos.controller to javafx.fxml;
    opens cl.duoc.recintos.model to javafx.base;

    exports cl.duoc.recintos.reservarecintosfx;
    exports cl.duoc.recintos.controller;
    exports cl.duoc.recintos.model;
    exports cl.duoc.recintos.repository;
}