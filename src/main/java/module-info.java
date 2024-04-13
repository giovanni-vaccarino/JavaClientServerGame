module polimi.ingsoft.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;

    opens polimi.ingsoft.demo to javafx.fxml;
    exports polimi.ingsoft.server;
    exports polimi.ingsoft.rmi;
    exports polimi.ingsoft.controller;
}


