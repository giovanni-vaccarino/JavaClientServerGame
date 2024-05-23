package polimi.ingsoft.client.ui.gui.page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import polimi.ingsoft.client.ui.gui.GUI;
import polimi.ingsoft.client.ui.gui.GUIsingleton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class QuestCardPageController implements Initializable {
    @FXML
    private ImageView questCard1;
    @FXML
    private ImageView questCard2;
    private String firstQuestCardPath;
    private String secondQuestCardPath;

    private boolean selected; // 0 -- questCard1, 1 -- questCard2

    public QuestCardPageController() {
        GUIsingleton.getInstance().setQuestCardPageController(this);
    }

    public GUI getGui(){
        return GUIsingleton.getInstance().getGui();
    }

    public Stage getStage(){
        return GUIsingleton.getInstance().getStage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setQuestCard("/polimi/ingsoft/demo/graphics/img/card/frontCard/questCard/frontQuestCard(1).jpg",
                "/polimi/ingsoft/demo/graphics/img/card/frontCard/questCard/frontQuestCard(2).jpg");
    }

    public void setQuestCard(String firstPath, String secondPath){
        firstQuestCardPath = String.valueOf(firstPath);
        secondQuestCardPath = String.valueOf(secondPath);
        placeCards();
    }

    public void placeCards(){
        Image image;

        image = new Image(firstQuestCardPath);

        questCard1.setFitWidth(140);
        questCard1.setFitHeight(100);

        questCard1.setViewport(new Rectangle2D(61, 64, 908, 628));
        questCard1.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 2, 2);");

        questCard1.setImage(image);

        image = new Image(secondQuestCardPath);

        questCard2.setFitWidth(140);
        questCard2.setFitHeight(100);

        questCard2.setViewport(new Rectangle2D(61, 64, 908, 628));
        questCard2.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 2, 2);");

        questCard2.setImage(image);
    }

    public void start() throws Exception {
        // Load FXML file
        URL resourceUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/QuestCardPage.fxml");
        if (resourceUrl == null) {
            System.out.println("FXML file not found");
            return;
        }
        //System.out.println("FXML file found");
        Parent root = FXMLLoader.load(resourceUrl);

        /*FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), root);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();*/

        // Load CSS file
        URL cssUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/css/ButtonStyle.css");
        if (cssUrl != null) {
            root.getStylesheets().add(cssUrl.toExternalForm());
            //System.out.println("CSS file found");
        } else {
            System.out.println("CSS file not found");
        }

        getStage().getScene().setRoot(root);
    }

    public void questCard1Selected(ActionEvent actionEvent) throws IOException {
        selected = false;
        nextPage();
    }

    public void questCard2Selected(ActionEvent actionEvent) throws IOException {
        selected = true;
        nextPage();
    }

    public void nextPage() throws IOException{

        QuestCardWaitingPageController questCardWaitingPageController = new QuestCardWaitingPageController();
        try {
            questCardWaitingPageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
