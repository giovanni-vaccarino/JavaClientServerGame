package polimi.ingsoft.client.ui.gui.utils;

import polimi.ingsoft.client.ui.gui.GUI;
import polimi.ingsoft.client.ui.gui.GUIsingleton;
import polimi.ingsoft.server.model.Coordinates;
import polimi.ingsoft.server.model.MixedCard;

import java.io.IOException;

public class GamePageDynamicUtils {
    private static MixedCard mixedCard;
    private static int xPlayedCard;
    private static int yPlayedCard;
    public static GUI getGui(){
        return GUIsingleton.getInstance().getGui();
    }

    public static void drawPlayerHand(int x, int y){
        mixedCard = getGui().getUiModel().getPlayerHand().get(x-1);
        xPlayedCard=x;
        yPlayedCard=y;
        System.out.println(xPlayedCard+":"+yPlayedCard);
    }

    public static void selectBoardPlacePlayedCard(int x, int y){
        String nickname = getGui().getNickname();
        Coordinates coordinates = GUIsingleton.getInstance().getGamePageController().getPosFromTableToGraph(x,y);
        if(mixedCard!=null){
            try {
                System.out.println("Mixed card inviata: "+coordinates.getX()+":"+coordinates.getY());
                getGui().getMatchServer().placeCard(nickname,mixedCard,coordinates, PlaceCardUtils.getIsFrontPlayerHandCard(xPlayedCard,yPlayedCard));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            System.out.println("Mixed card NON inviata 1");
        }
    }
}
