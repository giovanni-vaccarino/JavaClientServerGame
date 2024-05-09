package polimi.ingsoft.client.ui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.util.*;

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
    @FXML private GridPane otherBoards;

    @FXML private ImageView arrowS;
    @FXML private ImageView arrowE;
    @FXML private ImageView arrowO;
    @FXML private ImageView arrowN;

    @FXML private ImageView blueScoreImg;
    @FXML private ImageView greenScoreImg;
    @FXML private ImageView redScoreImg;
    @FXML private ImageView yellowScoreImg;

    private List<Integer> score;

    private ImageView[][] tableAppo; // ADD INT VAL TO DEFINE IF IT'S UPON/UNDER

    private int rowNum;
    private int colNum;

    public void setNumTable() {
        rowNum = board.getRowConstraints().size(); // 9
        colNum = board.getColumnConstraints().size(); // 5
    }

    public GamePageController() {}

    public GamePageController(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initial Card
        ImageView initialCardImg = new ImageView(new Image("/polimi/ingsoft/demo/graphics/img/card/frontCard/initialCard/frontInitialCard(1).jpg"));
        placeCard(2,4, board, initialCardImg);

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

        // Set table
        setNumTable();
        //tableAppo = new ImageView[colNum][rowNum];

        // Set other Player's Boards
        List<String> playerList = Arrays.asList("Andre", "Gio", "Simon");
        otherBoards(playerList);

        // Set arrows
        placeArrow("S", arrowS);
        placeArrow("E", arrowE);
        placeArrow("O", arrowO);
        placeArrow("N", arrowN);

        // Set score positions
        score = new ArrayList<>();
        score.add(16);
        score.add(17);
        score.add(16);
        score.add(18);
        placeScore();

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

    public void otherBoards(List<String> playerList) {

        for (int col = 0; col < playerList.size(); col++) {
            Button button = new Button(playerList.get(col));
            button.getStyleClass().add("buttonRegular"); // Add CSS style class
            int finalCol = col;
            button.setOnAction(event -> {
                // Handle button click, you can implement actions here
                System.out.println("Button clicked for player: " + playerList.get(finalCol));
            });
            //GridPane.setHalignment(button, HPos.CENTER);
            otherBoards.add(button, col, 0);
        }
    }

    public void loadEntireBoard(){

        // LOAD THE TABLE FROM THE SERVER
        // THEN LOAD THE GIRD FROM THE TABLE:
        for (int i = 0; i < colNum; i++) {
            for (int j = 0; j < rowNum; j++) {
                if (tableAppo != null) {
                    // ImageView found
                    System.out.println(i);
                    System.out.println(j);

                    placeCard(i,j,board,tableAppo[i][j]);
                } else {
                    // ImageView not found at the specified coordinates
                }
            }
        }
    }

    public void placeScore(){
        /*
        1.blue
        2.green
        3.red
        4.yellow
         */

        ImageView imageView = null;
        String imageUrl = null;
        int x=41,y=331;

        if(score != null){
            if(score.size()<=4){
                System.out.println(score.size());
                System.out.println(score);
                for(int i=0; i<score.size(); i++){
                    System.out.println(i);
                    switch (i){
                        case 0:
                            imageUrl = "/polimi/ingsoft/demo/graphics/img/score/blueScore.png";
                            imageView = blueScoreImg;
                            break;
                        case 1:
                            imageUrl = "/polimi/ingsoft/demo/graphics/img/score/greenScore.png";
                            imageView = greenScoreImg;
                            break;
                        case 2:
                            imageUrl = "/polimi/ingsoft/demo/graphics/img/score/redScore.png";
                            imageView = redScoreImg;
                            break;
                        case 3:
                            imageUrl = "/polimi/ingsoft/demo/graphics/img/score/yellowScore.png";
                            imageView = yellowScoreImg;
                            break;
                    }

                    switch(score.get(i)){
                        case 0:
                            x=41;
                            y=331;
                            break;
                        case 1:
                            x=85;
                            y=331;
                            break;
                        case 2:
                            x=129;
                            y=331;
                            break;
                        case 3:
                            x=153;
                            y=294;
                            break;
                        case 4:
                            x=108;
                            y=294;
                            break;
                        case 5:
                            x=64;
                            y=294;
                            break;
                        case 6:
                            x=19;
                            y=294;
                            break;
                        case 7:
                            x=19;
                            y=251;
                            break;

                        case 8:
                            x=62;
                            y=251;
                            break;

                        case 9:
                            x=108;
                            y=251;
                            break;

                        case 10:
                            x=153;
                            y=251;
                            break;

                        case 11:
                            x=153;
                            y=219;
                            break;

                        case 12:
                            x=108;
                            y=219;
                            break;

                        case 13:
                            x=63;
                            y=219;
                            break;

                        case 14:
                            x=19;
                            y=219;
                            break;

                        case 15:
                            x=19;
                            y=180;
                            break;

                        case 16:
                            x=63;
                            y=180;
                            break;

                        case 17:
                            x=108;
                            y=180;
                            break;

                        case 18:
                            x=153;
                            y=180;
                            break;

                        case 19:
                            x=153;
                            y=143;
                            break;

                        case 20:
                            x=86;
                            y=123;
                            break;

                        case 21:
                            x=19;
                            y=143;
                            break;

                        case 22:
                            x=19;
                            y=104;
                            break;

                        case 23:
                            x=19;
                            y=67;
                            break;

                        case 24:
                            x=44;
                            y=35;
                            break;

                        case 25:
                            x=86;
                            y=28;
                            break;

                        case 26:
                            x=127;
                            y=35;
                            break;

                        case 27:
                            x=153;
                            y=67;
                            break;

                        case 28:
                            x=153;
                            y=104;
                            break;

                        case 29:
                            x=86;
                            y=75;
                            break;
                    }

                    x += (-4)*countSameScore(i);
                    y += (-6)*countSameScore(i);


                    placeSingolScore(x,y,imageUrl, imageView);
                }
            }else{
                //errore numero giocatori
            }
        }
    }

    public void placeSingolScore(int x, int y, String imageUrl, ImageView imageView){
        Image image = new Image(imageUrl);

        imageView.setFitWidth(29);
        imageView.setFitHeight(29);

        imageView.setLayoutX(x);
        imageView.setLayoutY(y);

        imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 2, 2);");

        imageView.setImage(image);
    }

    public int countSameScore(int s){
        int counter = 0;

        if(s<score.size() && s>0){
            for(int i=s-1; i>=0; i--){
                if(score.get(i).equals(score.get(s))){
                    counter++;
                }
            }
        }

        return counter;
    }
    public void placeArrow(String dir, ImageView imageView){
        double rotationAngle = 0;

        String imageUrl = "/polimi/ingsoft/demo/graphics/img/arrow.PNG"; // Replace with your image URL
        Image image = new Image(imageUrl);

        imageView.setFitWidth(50.0);
        imageView.setFitHeight(50.0);

        if(Objects.equals(dir, "S")){
            rotationAngle = 0.0;
        } else if (Objects.equals(dir, "E")) {
            rotationAngle = -90.0;
        } else if (Objects.equals(dir, "O")) {
            rotationAngle = 90.0;
        } else if (Objects.equals(dir, "N")) {
            rotationAngle = 180.0;
        }
        imageView.setRotate(rotationAngle);

        imageView.setImage(image);
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

        ImageView possiblePosition = new ImageView(new Image("/polimi/ingsoft/demo/graphics/img/card/possiblePosition.png"));

        possiblePosition.setFitWidth(140);
        possiblePosition.setFitHeight(100);

        possiblePosition.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 2, 2);");

        // Add the ImageView to the specific cell in the GridPane
        board.add(possiblePosition, x, y);
    }

    public void placeCardHandler(int x,int y, GridPane gridPane){
        ImageView cardImg = new ImageView(new Image("/polimi/ingsoft/demo/graphics/img/card/frontCard/resourceCard/frontResourceCard(1).jpg"));
        placeCard(x,y,gridPane,cardImg);
    }

    public void placeCard(int x, int y, GridPane gridPane, ImageView imageView){

        imageView.setFitWidth(140);
        imageView.setFitHeight(100);

        imageView.setViewport(new Rectangle2D(61, 64, 908, 628));
        imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 2, 2);");

        // Add the ImageView to the specific cell in the GridPane
        gridPane.add(imageView, x, y);
    }

    /*public void loadCard(ImageView imageView, String imagePath) {
        Image image = new Image(imagePath);
        imageView.setImage(image);

        imageView.setFitWidth(140);
        imageView.setFitHeight(100);

        imageView.setViewport(new javafx.geometry.Rectangle2D(61, 64, 908, 628));
        imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 2, 2);");
    }*/

    public void moveBoardHandler_N(ActionEvent actionEvent){
        moveBoard(0,-2);
    }

    public void moveBoardHandler_E(ActionEvent actionEvent){
        moveBoard(2,0);
    }

    public void moveBoardHandler_O(ActionEvent actionEvent){
        moveBoard(-2,0);
    }

    public void moveBoardHandler_S(ActionEvent actionEvent){
        moveBoard(0,2);
    }

    public void moveBoard(int x, int y){

        //RELOAD ALL THE IMAGES BY CHANGING THE COORDINATE (SUM with x,y)

        /*
        SAVES THE PRESENT CARD + LOAD THE MISSING CARDS

        ImageView[][] tableAppo = new ImageView[colNum][rowNum];

        for (int i = 0; i < colNum-x; i++) {
            for (int j = 0; j < rowNum-y; j++) {
                ImageView mixedCard = getImageViewByCoordinates(board, j, i);
                if (mixedCard != null) {
                    // ImageView found
                    System.out.println(i);
                    System.out.println(j);

                    if(i+x<colNum && j+y<rowNum) {
                        tableAppo[x][y] = mixedCard;
                    }
                } else {
                    // ImageView not found at the specified coordinates
                }
            }
        }

        for(int i=0; i+x<colNum; i++){
            for(int j=0; j+y<rowNum; y++){
                GridPaneUtils.removeImageViewIfExists(board, j + y, i + x);
                placeCard(i + x, j + y, board, tableAppo[x][y]);
            }
        }*/


        //for(int i=colNum-x; i<)
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
