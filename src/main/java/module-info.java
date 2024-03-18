module polimi.ingsoft.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens polimi.ingsoft.demo to javafx.fxml;
    exports polimi.ingsoft.demo;
}