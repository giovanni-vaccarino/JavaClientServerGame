package polimi.ingsoft.client.ui.gui.utils;

import javafx.scene.layout.GridPane;
import polimi.ingsoft.client.ui.gui.GUI;
import polimi.ingsoft.client.ui.gui.GUIsingleton;
import polimi.ingsoft.server.model.PlaceInPublicBoard;

public class SetGamePage {
    public static GUI getGui(){
        return GUIsingleton.getInstance().getGui();
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

        for(int i=0; i<3; i++){
            id = getGui().getPlayerHand().get(i).getID();
            if(PlaceCardUtils.getIsFrontPlayerHandCard(i+1,0)){
                path= CardPathUtils.frontMixedCard(id);
            }else{
                path= CardPathUtils.backMixedCard(id);
            }
            PlaceCardUtils.placeCardString(i+1,0,personalDeck, path);
        }
    }
}
