module org.example.guia_2_1_5 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.guia_2_1_5 to javafx.fxml;
    exports org.example.guia_2_1_5;
}