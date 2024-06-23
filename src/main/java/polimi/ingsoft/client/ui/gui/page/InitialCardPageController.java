package polimi.ingsoft.client.ui.gui.page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import polimi.ingsoft.client.ui.gui.GUI;
import polimi.ingsoft.client.ui.gui.GUIsingleton;
import polimi.ingsoft.client.ui.gui.utils.CardPathUtils;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InitialCardPageController implements Initializable {
    private static final Logger cssLogger = Logger.getLogger("javafx.scene.CssStyleHelper");
    @FXML
    private ImageView initialCard;
    @FXML
    Button waitButton;
    @FXML
    Button selectButton;
    @FXML
    Button flipButton;
    private String frontInitialCardPath;
    private String backInitialCardPath;

    private boolean isFaceUp;

    // Default constructor
    public InitialCardPageController() {
        GUIsingleton.getInstance().setInitialCardPageController(this);
        cssLogger.setLevel(Level.SEVERE);
    }

    public GUI getGui(){
        return GUIsingleton.getInstance().getGui();
    }

    public Stage getStage(){
        return GUIsingleton.getInstance().getStage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        waitButton.setVisible(false);
        isFaceUp =false;
        setInitialCardPath(getGui().getInitialCard());
    }

    public void setInitialCardPath(String id){
        String frontPath = CardPathUtils.frontInitialCard(id);
        String backPath = CardPathUtils.backInitialCard(id);
        setInitialCard(frontPath, backPath);
    }

    public void setInitialCard(String frontPath, String backPath){
        frontInitialCardPath = String.valueOf(frontPath);
        backInitialCardPath = String.valueOf(backPath);
        placeFace();
    }

    public void placeFace(){
        /*
        1 = back
        0 = front
         */
        Image image;

        if(isFaceUp){
            image = new Image(frontInitialCardPath);
        }else{
            image = new Image(backInitialCardPath);
        }

        initialCard.setFitWidth(140);
        initialCard.setFitHeight(100);

        initialCard.setViewport(new Rectangle2D(61, 64, 908, 628));
        initialCard.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 2, 2);");

        initialCard.setImage(image);
    }

    public void flip(){
        isFaceUp = !isFaceUp;
        placeFace();
    }

    public void start() throws Exception {
        // Load FXML file
        URL resourceUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/InitialCardPage.fxml");
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

    public void select(ActionEvent actionEvent) {
        getGui().setIsFaceInitialCardUp(isFaceUp);
        showWait();
    }

    public void showWait(){
        waitButton.setVisible(true);
        flipButton.setVisible(false);
        selectButton.setVisible(false);
    }

    public void nextPage(){
        QuestCardPageController questCardPageController = new QuestCardPageController();
        try {
            questCardPageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
