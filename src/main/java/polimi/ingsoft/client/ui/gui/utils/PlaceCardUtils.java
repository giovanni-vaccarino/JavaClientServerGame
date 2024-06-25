package polimi.ingsoft.client.ui.gui.utils;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import polimi.ingsoft.client.ui.gui.GUIsingleton;
import polimi.ingsoft.server.enumerations.TYPE_PLAYED_CARD;
import polimi.ingsoft.server.model.boards.Coordinates;

import java.util.HashMap;
import java.util.Map;

public class PlaceCardUtils {
    private static final Map<Coordinates, Boolean> isCardFrontPublicBoard = new HashMap<>();
    private static final Map<Coordinates, Boolean> isCardFrontPlayerHand = new HashMap<>();
    private static ImageView clicked = null;

    public static void placeSameResourceCard(int x, int y, GridPane gridPane){
        ImageView cardImg = new ImageView(new Image("/polimi/ingsoft/demo/graphics/img/card/frontCard/mixedCard/frontResourceCard(1).jpg"));
        placeCard(x,y,gridPane,cardImg, false);
    }

    public static void placeSameQuestCard(int x, int y, GridPane gridPane){
        ImageView cardImg = new ImageView(new Image("/polimi/ingsoft/demo/graphics/img/card/backCard/questCard/backQuestCard(1).jpg"));
        placeCard(x,y,gridPane,cardImg, false);
    }

    public static void placeCardString(int x,int y, GridPane gridPane, String path){
        ImageView cardImg = new ImageView(new Image(path));
        placeCard(x,y,gridPane,cardImg, false);
    }

    public static void placePlayerHandCardString(int x, int y, GridPane gridPane, String path, TYPE_PLAYED_CARD typePlayedCard){
        Boolean mixedCardPlayerHand = false;
        ImageView cardImg = new ImageView(new Image(path));

        if(typePlayedCard.equals(TYPE_PLAYED_CARD.MIXEDCARD)){
            mixedCardPlayerHand = true;
        }

        placeCard(x,y,gridPane,cardImg, mixedCardPlayerHand);
    }
    public static void placeCard(int x, int y, GridPane gridPane, ImageView imageView, Boolean playerHand){

        imageView.setFitWidth(140);
        imageView.setFitHeight(100);

        imageView.setViewport(new Rectangle2D(61, 64, 908, 628));
        imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 2, 2);");

        // Add the ImageView to the specific cell in the GridPane
        gridPane.add(imageView, x, y);

        if(y!=2){
            showSelectedCard(imageView, playerHand);
        }
    }

    public static void initializeFaceCards(){
        for(int i=0; i<2; i++){
            for(int j=0; j<2; j++){
                isCardFrontPublicBoard.put(new Coordinates(i,j),false);
            }
        }

        isCardFrontPlayerHand.put(new Coordinates(0,0),true);

        for(int i=1; i<4; i++){
            isCardFrontPlayerHand.put(new Coordinates(i,0),false);
        }
    }

    public static Boolean getIsFrontPublicBoardCard(int x, int y){
        return isCardFrontPublicBoard.get(new Coordinates(x,y));
    }

    public static Boolean getIsFrontPlayerHandCard(int x, int y){
        return isCardFrontPlayerHand.get(new Coordinates(x,y));
    }

    public static void flipCardPublicBoard(int x, int y){
        Coordinates coordinates = new Coordinates(x,y);
        Boolean isFront = getIsFrontPublicBoardCard(x,y);
        isCardFrontPublicBoard.remove(coordinates);
        isCardFrontPublicBoard.put(coordinates,!isFront);

        GUIsingleton.getInstance().getGamePageController().setVisiblePublicBoard();

        GUIsingleton.getInstance().getGamePageController().setClickBoardHandler();
    }

    public static void flipCardPlayerHand(int x, int y){
        GUIsingleton.getInstance().getGamePageController().resetMixedCard();

        Coordinates coordinates = new Coordinates(x,y);
        Boolean isFront = getIsFrontPlayerHandCard(x,y);
        isCardFrontPlayerHand.remove(coordinates);
        isCardFrontPlayerHand.put(coordinates,!isFront);

        GUIsingleton.getInstance().getGamePageController().setPlayerHand();

        GUIsingleton.getInstance().getGamePageController().setClickBoardHandler();
    }

    public static void showSelectedCard(ImageView imageView, boolean playerHand){
        imageView.setOnMouseEntered(event -> {
            imageView.setFitWidth(170);
            imageView.setFitHeight(120);
            imageView.setTranslateX(-20);
        });

        imageView.setOnMouseExited(event -> {
            imageView.setFitWidth(140);
            imageView.setFitHeight(100);
            imageView.setTranslateX(0);
        });

        if(playerHand){
            imageView.setOnMousePressed(event -> {
                if(clicked!=null){
                    moveImageView(clicked, 0);
                }
                clicked=imageView;

                moveImageView(imageView, 30);
            });
        }
    }

    public static void placePossibleCoordinates(int x, int y){
        ImageView possiblePosition = new ImageView(new Image("/polimi/ingsoft/demo/graphics/img/card/possiblePosition.png"));

        possiblePosition.setFitWidth(140);
        possiblePosition.setFitHeight(100);

        possiblePosition.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 2, 2);");

        // Add the ImageView to the specific cell in the GridPane

        GUIsingleton.getInstance().getGamePageController().getBoard().add(possiblePosition, x, y);

        possiblePosition.setOnMouseEntered(event -> {
//            possiblePosition.setFitWidth(170);
//            possiblePosition.setFitHeight(120);
//            possiblePosition.setTranslateX(-20);
            possiblePosition.setStyle("");
        });

        possiblePosition.setOnMouseExited(event -> {
//            possiblePosition.setFitWidth(140);
//            possiblePosition.setFitHeight(100);
//            possiblePosition.setTranslateX(0);
            possiblePosition.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 2, 2);");
        });
    }

    private static void moveImageView(ImageView imageView, int targetYMove) {
        Timeline timeline = new Timeline();

        KeyFrame keyFrame = new KeyFrame(
                Duration.seconds(.25), // Duration of the animation
                new KeyValue(imageView.translateYProperty(), targetYMove)
        );

        timeline.getKeyFrames().add(keyFrame);

        timeline.play();
    }
}
