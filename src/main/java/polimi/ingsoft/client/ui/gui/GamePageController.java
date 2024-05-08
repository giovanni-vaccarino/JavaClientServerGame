package polimi.ingsoft.client.ui.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
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

    @FXML private GridPane coveredDrawableDeck1;
    @FXML private GridPane coveredDrawableDeck2;
    @FXML private GridPane coveredDrawableDeck3;
    @FXML private GridPane coveredDrawableDeck4;
    @FXML private GridPane visibleDrawableDeck;
    @FXML private GridPane personalDeck;

    @FXML private GridPane board;


    public GamePageController() {}

    public GamePageController(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initial Card
        placeCard(2,4, board, "/polimi/ingsoft/demo/graphics/img/card/frontCard/initialCard/frontInitialCard(1).jpg");

        // Free places on Board
        possibleOptions();

        // Personal cards
        placeCardHandler(0,0, personalDeck);
        placeCardHandler(1,0, personalDeck);
        placeCardHandler(2,0, personalDeck);
        placeCardHandler(3,0, personalDeck);

        // Visible Drawable Cards
        placeCardHandler(0,0, visibleDrawableDeck);
        placeCardHandler(1,0, visibleDrawableDeck);

        placeCardHandler(0,1, visibleDrawableDeck);
        placeCardHandler(1,1, visibleDrawableDeck);

        placeCardHandler(0,2, visibleDrawableDeck);
        placeCardHandler(1,2, visibleDrawableDeck);

        // Covered Drawable Cards

        placeCardHandler(0,0, coveredDrawableDeck1);
        placeCardHandler(0,1, coveredDrawableDeck1);
        placeCardHandler(0,2, coveredDrawableDeck1);

        placeCardHandler(0,0, coveredDrawableDeck2);
        placeCardHandler(0,1, coveredDrawableDeck2);
        placeCardHandler(0,2, coveredDrawableDeck2);

        placeCardHandler(0,0, coveredDrawableDeck3);
        placeCardHandler(0,1, coveredDrawableDeck3);
        placeCardHandler(0,2, coveredDrawableDeck3);

        placeCardHandler(0,0, coveredDrawableDeck4);
        placeCardHandler(0,1, coveredDrawableDeck4);
        placeCardHandler(0,2, coveredDrawableDeck4);

        // Make position editable (only for board)
        for (Node node : board.getChildren()) {
            // Attach a mouse click event handler to each cell
            node.setOnMouseClicked(event -> {
                System.out.println(board.getChildren());
                int y = GridPane.getRowIndex(node);
                int x = GridPane.getColumnIndex(node);

                placeCardHandler(x, y, board);
            });
        }
    }

    public void possibleOptions(){
        // LINK TO THE AVAILABLE PLACES

        // margins:
        //coloredCell(0,0);
        //coloredCell(4,8);

        coloredCell(3,5);
        coloredCell(3,3);
    }

    public void coloredCell(int x, int y){
        /*Region cellBackground = new Region();
        cellBackground.setStyle("-fx-background-color: #d64917;");

        // Set the background to only one cell (row 1, column 1)
        GridPane.setRowIndex(cellBackground, y);
        GridPane.setColumnIndex(cellBackground, x);

        // Add the background to the GridPane
        board.getChildren().add(cellBackground);*/

        // TO COLOR BOARD FREE POSITIONS

        ImageView imageView = new ImageView(new Image("/polimi/ingsoft/demo/graphics/img/background/possiblePosition.png"));

        imageView.setFitWidth(140);
        imageView.setFitHeight(100);

        imageView.setStyle("-fx-border-width: 5; -fx-border-color: black");

        // Add the ImageView to the specific cell in the GridPane
        board.add(imageView, x, y);
    }

    public void placeCardHandler(int x,int y, GridPane gridPane){
        placeCard(x,y,gridPane,"/polimi/ingsoft/demo/graphics/img/card/frontCard/resourceCard/frontResourceCard(1).jpg");
    }

    public void placeCard(int x, int y, GridPane gridPane, String imagePath){
        ImageView imageView = new ImageView(new Image(imagePath));

        imageView.setFitWidth(140);
        imageView.setFitHeight(100);

        imageView.setViewport(new javafx.geometry.Rectangle2D(61, 64, 908, 628));
        imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 2, 2);");

        // Add the ImageView to the specific cell in the GridPane
        gridPane.add(imageView, x, y);
    }

    public void loadCard(ImageView imageView, String imagePath) {
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
