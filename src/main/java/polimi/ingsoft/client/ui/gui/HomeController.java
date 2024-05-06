package polimi.ingsoft.client.ui.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;

import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

public class HomeController extends Application {

    int waitingTime = 1;
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        URL resourceUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/Home.fxml");
        if (resourceUrl == null) {
            System.out.println("Starting scene fxml not found");
            return;
        }
        Parent root = FXMLLoader.load(resourceUrl);
        stage.setTitle("Codex");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int secondsPassed = 0;

            @Override
            public void run() {
                if (secondsPassed < waitingTime) {
                    // print a wait message (?)
                    secondsPassed++;
                    //System.out.println(secondsPassed);
                } else {
                    //System.out.println("FINE FINE FINE");
                    timer.cancel(); // Terminate the timer
                    Platform.runLater(() -> {
                        try {
                            nextPage(stage);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        };

        timer.schedule(task, 0, 1000);
    }

    public void nextPage(Stage stage) throws IOException {
        ConnectionPageController connectionController = new ConnectionPageController(stage);
        try {
            connectionController.start(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}