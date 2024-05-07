package polimi.ingsoft.client.ui.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.net.URL;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import java.util.ResourceBundle;

/*TO FIX:
- define a function that from (x,y) it returns the position in the ancor pane where x0,y0 is the initial card
- define buttons to see boards of the other players
- find a way to expand the board when it becomes too big for the table
 */

public class GamePageController implements Initializable{
    private Stage stage;

    @FXML
    private ImageView resourceCardDeck1;
    @FXML
    private ImageView resourceCardDeck2;
    @FXML
    private ImageView resourceCardDeck3;
    @FXML
    private ImageView resourceCardDeck4;

    @FXML
    private ImageView resourceCard1;
    @FXML
    private ImageView resourceCard2;

    @FXML
    private ImageView goldCardDeck1;
    @FXML
    private ImageView goldCardDeck2;
    @FXML
    private ImageView goldCardDeck3;
    @FXML
    private ImageView goldCardDeck4;

    @FXML
    private ImageView goldCard1;
    @FXML
    private ImageView goldCard2;

    @FXML
    private ImageView questCardDeck1;
    @FXML
    private ImageView questCardDeck2;
    @FXML
    private ImageView questCardDeck3;
    @FXML
    private ImageView questCardDeck4;

    @FXML
    private ImageView questCard1;
    @FXML
    private ImageView questCard2;

    @FXML
    private ImageView personalQuestCard;

    @FXML
    private ImageView personalMixedCard1;
    @FXML
    private ImageView personalMixedCard2;
    @FXML
    private ImageView personalMixedCard3;

    public GamePageController() {}

    // Constructor with stage parameter
    public GamePageController(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load image using a default path
        loadImage(resourceCardDeck1,"/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");
        loadImage(resourceCardDeck2,"/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");
        loadImage(resourceCardDeck3,"/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");
        loadImage(resourceCardDeck4,"/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");

        loadImage(resourceCard1,"/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");
        loadImage(resourceCard2,"/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");

        loadImage(goldCardDeck1,"/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");
        loadImage(goldCardDeck2,"/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");
        loadImage(goldCardDeck3,"/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");
        loadImage(goldCardDeck4,"/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");

        loadImage(goldCard1,"/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");
        loadImage(goldCard2,"/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");

        loadImage(questCardDeck1,"/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");
        loadImage(questCardDeck2,"/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");
        loadImage(questCardDeck3,"/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");
        loadImage(questCardDeck4,"/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");

        loadImage(questCard1,"/polimi/ingsoft/demo/graphics/img/card/frontCard/questCard/frontQuestCard(1).jpg");
        loadImage(questCard2,"/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");

        loadImage(personalQuestCard,"/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");

        loadImage(personalMixedCard1,"/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");
        loadImage(personalMixedCard2,"/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");
        loadImage(personalMixedCard3,"/polimi/ingsoft/demo/graphics/img/card/backCard/resourceCard/backResourceCard(1).jpg");
    }

    // Method to load image with a given path
    public void loadImage(ImageView imageView,String imagePath) {
        Image image = new Image(imagePath);
        imageView.setImage(image);

        imageView.setFitWidth(140);
        imageView.setFitHeight(100);

        imageView.setViewport(new javafx.geometry.Rectangle2D(61, 64, 908, 628));
        imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 2, 2);");
    }

    public void start() throws Exception {
        // Load FXML file
        URL resourceUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/GamePage.fxml");
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

        stage.getScene().setRoot(root);
    }
}
