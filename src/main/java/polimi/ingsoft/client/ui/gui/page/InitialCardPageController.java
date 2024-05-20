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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InitialCardPageController implements Initializable {
    private Stage stage;
    @FXML
    private ImageView initialCard;
    private String frontInitialCardPath;
    private String backInitialCardPath;

    private boolean flip;

    // Default constructor
    public InitialCardPageController() {}

    // Constructor with stage parameter
    public InitialCardPageController(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        flip=false;
        setInitialCard("/polimi/ingsoft/demo/graphics/img/card/frontCard/initialCard/frontInitialCard(1).jpg",
                "/polimi/ingsoft/demo/graphics/img/card/backCard/initialCard/backInitialCard(1).jpg");
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

        if(flip){
            image = new Image(backInitialCardPath);
        }else{
            image = new Image(frontInitialCardPath);
        }

        initialCard.setFitWidth(140);
        initialCard.setFitHeight(100);

        initialCard.setViewport(new Rectangle2D(61, 64, 908, 628));
        initialCard.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 2, 2);");

        initialCard.setImage(image);
    }

    public void flip(){
        flip = !flip;
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

        stage.getScene().setRoot(root);
    }

    public void nextPage(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        InitialCardWaitingPageController initialCardWaitingPageController = new InitialCardWaitingPageController(stage);
        try {
            initialCardWaitingPageController.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
