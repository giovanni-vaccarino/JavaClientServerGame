package polimi.ingsoft.client.ui.gui;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class homeController extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        URL resourceUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/home.fxml");
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
                if (secondsPassed < 1) {
                    // print a wait message (?)
                    secondsPassed++;
                    System.out.println(secondsPassed);
                } else {
                    System.out.println("FINE FINE FINE");
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

        URL resource = getClass().getResource("/polimi/ingsoft/demo/graphics/connectionPage.fxml");
        if (resource == null) {
            System.out.println("Starting scene fxml not found");
            return;
        }

        Parent root = FXMLLoader.load(resource);
        //Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), root);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
