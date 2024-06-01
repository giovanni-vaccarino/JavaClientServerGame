package polimi.ingsoft.client.ui.gui.utils;

import javafx.scene.layout.GridPane;
import polimi.ingsoft.client.ui.gui.GUI;
import polimi.ingsoft.client.ui.gui.GUIsingleton;
import polimi.ingsoft.client.ui.gui.page.GamePageController;
import polimi.ingsoft.server.enumerations.PlayerColor;
import polimi.ingsoft.server.model.Board;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.PlaceInPublicBoard;
import polimi.ingsoft.server.model.PlayedCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SetGamePage {
    public static GUI getGui(){
        return GUIsingleton.getInstance().getGui();
    }
    public static GamePageController getGamePageController(){
        return GUIsingleton.getInstance().getGamePageController();
    }

    public static void setCoveredPublicBoard(GridPane coveredDrawableDeck1){
        GridPaneUtils.eraseGridPane(coveredDrawableDeck1,3,1);

        // Resource Covered
        String id = getGui().getResourceCardPublicBoard().get(PlaceInPublicBoard.Slots.DECK).getID();
        String path= CardPathUtils.backMixedCard(id);
        PlaceCardUtils.placeCardString(0,0,coveredDrawableDeck1, path);

        // Gold Covered
        id = getGui().getGoldCardPublicBoard().get(PlaceInPublicBoard.Slots.DECK).getID();
        path= CardPathUtils.backMixedCard(id);
        PlaceCardUtils.placeCardString(0,1,coveredDrawableDeck1, path);

        // Quest Covered
        id = getGui().getQuestCardPublicBoard().get(PlaceInPublicBoard.Slots.DECK).getID();
        path= CardPathUtils.backQuestCard(id);
        PlaceCardUtils.placeCardString(0,2,coveredDrawableDeck1, path);
    }

    public static void setVisiblePublicBoard(GridPane visibleDrawableDeck){
        String path;

        GridPaneUtils.eraseGridPane(visibleDrawableDeck,3,2);

        // Resource

        String id = getGui().getResourceCardPublicBoard().get(PlaceInPublicBoard.Slots.SLOT_A).getID();
        if(PlaceCardUtils.getIsFrontPublicBoardCard(0,0)){
            path= CardPathUtils.frontMixedCard(id);
        }else{
            path= CardPathUtils.backMixedCard(id);
        }
        PlaceCardUtils.placeCardString(0,0,visibleDrawableDeck, path);

        id = getGui().getResourceCardPublicBoard().get(PlaceInPublicBoard.Slots.SLOT_B).getID();
        if(PlaceCardUtils.getIsFrontPublicBoardCard(1,0)){
            path= CardPathUtils.frontMixedCard(id);
        }else{
            path= CardPathUtils.backMixedCard(id);
        }
        PlaceCardUtils.placeCardString(1,0,visibleDrawableDeck, path);

        // Gold

        id = getGui().getGoldCardPublicBoard().get(PlaceInPublicBoard.Slots.SLOT_A).getID();
        if(PlaceCardUtils.getIsFrontPublicBoardCard(0,1)){
            path= CardPathUtils.frontMixedCard(id);
        }else{
            path= CardPathUtils.backMixedCard(id);
        }
        PlaceCardUtils.placeCardString(0,1,visibleDrawableDeck, path);

        id = getGui().getGoldCardPublicBoard().get(PlaceInPublicBoard.Slots.SLOT_B).getID();
        if(PlaceCardUtils.getIsFrontPublicBoardCard(1,1)){
            path= CardPathUtils.frontMixedCard(id);
        }else{
            path= CardPathUtils.backMixedCard(id);
        }
        PlaceCardUtils.placeCardString(1,1,visibleDrawableDeck, path);

        // Quest

        id = getGui().getQuestCardPublicBoard().get(PlaceInPublicBoard.Slots.SLOT_A).getID();
        path= CardPathUtils.frontQuestCard(id);
        PlaceCardUtils.placeCardString(0,2,visibleDrawableDeck, path);

        id = getGui().getQuestCardPublicBoard().get(PlaceInPublicBoard.Slots.SLOT_B).getID();
        path= CardPathUtils.frontQuestCard(id);
        PlaceCardUtils.placeCardString(1,2,visibleDrawableDeck, path);
    }

    public static void setPlayerHand(GridPane personalDeck){
        String path, id;

        GridPaneUtils.eraseGridPane(personalDeck,1,4);

        id = getGui().getPersonalQuestCard().getID();
        if(PlaceCardUtils.getIsFrontPlayerHandCard(0,0)){
            path= CardPathUtils.frontQuestCard(id);
        }else{
            path= CardPathUtils.backQuestCard(id);
        }
        PlaceCardUtils.placeCardString(0,0,personalDeck, path);

        for(int i=0; i<getGui().getPlayerHand().size(); i++){
            id = getGui().getPlayerHand().get(i).getID();
            if(PlaceCardUtils.getIsFrontPlayerHandCard(i+1,0)){
                path= CardPathUtils.frontMixedCard(id);
            }else{
                path= CardPathUtils.backMixedCard(id);
            }
            PlaceCardUtils.placeCardString(i+1,0,personalDeck, path);
        }
    }

    public static void setBoardData(){

        Map<Coordinates, PlayedCard> allCards;
        int x,y, order;
        String path, id;

        for(String player : getGui().getUiModel().getPlayerBoards().keySet()){
            System.out.println(player);

            Board board = getGui().getUiModel().getPlayerBoards().get(player);

            List<Coordinates> possibleCoordinates = board.getAvailablePlaces();

            for(Coordinates possibleCoordinate: possibleCoordinates){
                x = possibleCoordinate.getX();
                y = possibleCoordinate.getY();
                getGamePageController().setPossibleOptions(x,y);
            }

            allCards = getGui().getUiModel().getPlayerBoards().get(player).getCards(); // hashmap <coordinate, playedcard>
            for (Coordinates coordinates: allCards.keySet()){
                x = coordinates.getX();
                y = coordinates.getY();
                System.out.println(x+":"+y);
                if(allCards.get(coordinates) == null){
                    System.out.println("allCards.get(coordinates) NULL");
                }else {
                    System.out.println("allCards.get(coordinates) NON null - "+allCards.get(coordinates));
                }
                id = allCards.get(coordinates).getCard().getID();

                if(x==0 && y==0){
                    // Initial card
                    if(allCards.get(coordinates).isFacingUp()){
                        path = CardPathUtils.frontInitialCard(id);
                    }else {
                        path = CardPathUtils.backInitialCard(id);
                    }
                } else if (allCards.get(coordinates).isFacingUp()){
                    // Mixed card facing up
                    path = CardPathUtils.frontMixedCard(id);
                } else {
                    // Mixed card facing down
                    path = CardPathUtils.backMixedCard(id);
                }

                order = allCards.get(coordinates).getOrder();

                // TO FILL THE MODEL OF GAMEPAGE CONTROLLER:
                GUIsingleton.getInstance().getGamePageController().setBoardCoordinatesOrder(x,y,path,order,player);
            }
        }
    }

    public static void setNicknameColors(){
        GamePageController gamePageController = GUIsingleton.getInstance().getGamePageController();

        for(String player : getGui().getUiModel().getPlayerBoards().keySet()){
            gamePageController.setNicknameColor(player, getGui().getUiModel().getPlayerBoards().get(player).getColor());
        }
    }
}
