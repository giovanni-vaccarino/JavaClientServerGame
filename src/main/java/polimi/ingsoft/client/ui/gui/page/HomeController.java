package polimi.ingsoft.client.ui.gui.page;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import javafx.application.Platform;
import polimi.ingsoft.client.ui.gui.GUI;
import polimi.ingsoft.client.ui.gui.GUIsingleton;

import java.util.Timer;
import java.util.TimerTask;

public class HomeController extends Application {
    int waitingTime = 1;
    private static GUI gui;
    private NicknamePageController nicknamePageController;


    public HomeController() {
        gui = GUIsingleton.getInstance().getGui();
    }

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

        //System.setProperty("javafx.verbose", "false");

        GUIsingleton.getInstance().setStage(stage);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int secondsPassed = 0;

            @Override
            public void run() {
                if (secondsPassed < waitingTime) {
                    secondsPassed++;
                } else {
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

        nicknamePageController = new NicknamePageController();

        try {
            nicknamePageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}