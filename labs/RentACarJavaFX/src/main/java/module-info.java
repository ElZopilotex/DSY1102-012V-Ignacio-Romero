module cl.duoc.dsy1102.w09.demo.rentacarjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens cl.duoc.dsy1102.w09.demo.rentacarjavafx to javafx.fxml;
    exports cl.duoc.dsy1102.w09.demo.rentacarjavafx;
}