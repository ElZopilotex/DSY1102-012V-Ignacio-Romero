module org.example.ejercicio_semana_9 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.ejercicio_semana_9 to javafx.fxml;
    exports org.example.ejercicio_semana_9;
}