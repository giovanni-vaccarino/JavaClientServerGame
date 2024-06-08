package polimi.ingsoft.client.ui.gui.utils;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import polimi.ingsoft.client.ui.gui.GUIsingleton;
import polimi.ingsoft.client.ui.gui.utils.selectedCardUtils.CardClickedEvent;
import polimi.ingsoft.client.ui.gui.utils.selectedCardUtils.CardClickedEventDispatcher;
import polimi.ingsoft.client.ui.gui.utils.selectedCardUtils.ShowSelectedCardUtils;
import polimi.ingsoft.server.model.boards.Coordinates;

import java.util.HashMap;
import java.util.Map;

public class PlaceCardUtils {
    private static final Map<Coordinates, Boolean> isCartFrontPublicBoard = new HashMap<>();
    private static final Map<Coordinates, Boolean> isCartFrontPlayerHand = new HashMap<>();

    public static void placeSameResourceCard(int x, int y, GridPane gridPane){
        ImageView cardImg = new ImageView(new Image("/polimi/ingsoft/demo/graphics/img/card/frontCard/mixedCard/frontResourceCard(1).jpg"));
        placeCard(x,y,gridPane,cardImg);
    }

    public static void placeSameQuestCard(int x, int y, GridPane gridPane){
        ImageView cardImg = new ImageView(new Image("/polimi/ingsoft/demo/graphics/img/card/backCard/questCard/backQuestCard(1).jpg"));
        placeCard(x,y,gridPane,cardImg);
    }

    public static void placeCardString(int x,int y, GridPane gridPane, String path){
        System.out.println("PLACE CARD PATH - "+path);
        ImageView cardImg = new ImageView(new Image(path));
        placeCard(x,y,gridPane,cardImg);
    }
    public static void placeCard(int x, int y, GridPane gridPane, ImageView imageView){

        imageView.setFitWidth(140);
        imageView.setFitHeight(100);

        imageView.setViewport(new Rectangle2D(61, 64, 908, 628));
        imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 2, 2);");

        // Add the ImageView to the specific cell in the GridPane
        gridPane.add(imageView, x, y);

        if(y!=2){
            showSelectedCard(imageView);
        }
    }

    public static void initializeFaceCards(){
        for(int i=0; i<2; i++){
            for(int j=0; j<2; j++){
                isCartFrontPublicBoard.put(new Coordinates(i,j),false);
            }
        }

        isCartFrontPlayerHand.put(new Coordinates(0,0),true);

        for(int i=1; i<4; i++){
            isCartFrontPlayerHand.put(new Coordinates(i,0),false);
        }
    }

    public static Boolean getIsFrontPublicBoardCard(int x, int y){
        return isCartFrontPublicBoard.get(new Coordinates(x,y));
    }

    public static Boolean getIsFrontPlayerHandCard(int x, int y){
        return isCartFrontPlayerHand.get(new Coordinates(x,y));
    }

    public static void flipCardPublicBoard(int x, int y){
        //System.out.println(x+":"+y);
        Coordinates coordinates = new Coordinates(x,y);
        Boolean isFront = getIsFrontPublicBoardCard(x,y);
        isCartFrontPublicBoard.remove(coordinates);
        isCartFrontPublicBoard.put(coordinates,!isFront);

        //System.out.println(isCartFrontPublicBoard);

        GUIsingleton.getInstance().getGamePageController().setVisiblePublicBoard();

        GUIsingleton.getInstance().getGamePageController().setClickBoardHandler();
    }

    public static void flipCardPlayerHand(int x, int y){
        //System.out.println(x+":"+y);

        Coordinates coordinates = new Coordinates(x,y);
        Boolean isFront = getIsFrontPlayerHandCard(x,y);
        isCartFrontPlayerHand.remove(coordinates);
        isCartFrontPlayerHand.put(coordinates,!isFront);

        //System.out.println(isCartFrontPlayerHand);

        GUIsingleton.getInstance().getGamePageController().setPlayerHand();

        GUIsingleton.getInstance().getGamePageController().setClickBoardHandler();
    }

    public static void showSelectedCard(ImageView imageView){

//        int key = ShowSelectedCardUtils.newKey();

        imageView.setOnMouseEntered(event -> {
            //imageView.setStyle("");
            imageView.setFitWidth(170);
            imageView.setFitHeight(120);
            imageView.setTranslateX(-20);
        });

        imageView.setOnMouseExited(event -> {
            //imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 2, 2);");
            imageView.setFitWidth(140);
            imageView.setFitHeight(100);
            imageView.setTranslateX(0);
        });

        imageView.setOnMouseClicked(event -> {
//            ShowSelectedCardUtils.setCurrentKey(key);

//            CardClickedEvent cardClickedEvent = new CardClickedEvent();
//            CardClickedEventDispatcher.dispatchEvent(cardClickedEvent);

            imageView.setFitWidth(170);
            imageView.setFitHeight(120);
            imageView.setTranslateX(-20);
        });

//        CardClickedEventDispatcher.registerListener(event -> {
//            if(!ShowSelectedCardUtils.isCurrentKey(key)){
//                imageView.setFitWidth(140);
//                imageView.setFitHeight(100);
//                imageView.setTranslateX(0);
//            }
//        });
    }

    public static void placePossibleCoordinates(int x, int y){
        ImageView possiblePosition = new ImageView(new Image("/polimi/ingsoft/demo/graphics/img/card/possiblePosition.png"));

        possiblePosition.setFitWidth(140);
        possiblePosition.setFitHeight(100);

        possiblePosition.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 2, 2);");

        // Add the ImageView to the specific cell in the GridPane

        GUIsingleton.getInstance().getGamePageController().getBoard().add(possiblePosition, x, y);

        possiblePosition.setOnMouseEntered(event -> {
            possiblePosition.setFitWidth(170);
            possiblePosition.setFitHeight(120);
            possiblePosition.setTranslateX(-20);
        });

        possiblePosition.setOnMouseExited(event -> {
            possiblePosition.setFitWidth(140);
            possiblePosition.setFitHeight(100);
            possiblePosition.setTranslateX(0);
        });
    }
}
