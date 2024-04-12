module polimi.ingsoft.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens polimi.ingsoft.server.demo to javafx.fxml;
    exports polimi.ingsoft.server.demo;
}