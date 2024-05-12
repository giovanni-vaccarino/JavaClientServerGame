package polimi.ingsoft.client.ui.cli;

import polimi.ingsoft.server.enumerations.Resource;
import polimi.ingsoft.server.model.Item;
import polimi.ingsoft.server.model.MixedCard;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatWidthException;

public class ClientHand {

    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    private String color;
    private ArrayList<MixedCard> cards;
    private ArrayList<Boolean> isFlipped;

    public ClientHand(){
        this.cards=new ArrayList<>();
        this.isFlipped=new ArrayList<>();
    }
    public void print(){
        for(int j=0;j<9;j++){
            for(int i=0;i<cards.size();i++){
                switch(cards.get(i).getFront().getCenter().getItems().getFirst()){
                    case Resource.BUTTERFLY:
                        this.color=PURPLE;
                        break;
                    case Resource.LEAF:
                        this.color=GREEN;
                        break;
                    case Resource.MUSHROOM:
                        this.color=RED;
                        break;
                    case Resource.WOLF:
                        this.color=BLUE;
                        break;
                    }
                switch(j){
                    case 0:
                        System.out.println(color+" ____________________________________");
                    case 1:
                        System.out.println(color+"/                                     \\");
                    case 2:
                        System.out.println(color+"|  "+ stampaRisorsa(i,1));
                    case 3:
                        System.out.println(color+" ____________________________________");
                    case 4:
                        System.out.println(color+" ____________________________________");
                    case 5:
                        System.out.println(color+" ____________________________________");
                    case 6:
                        System.out.println(color+" ____________________________________");
                    case 7:
                        System.out.println(color+" ____________________________________");
                    case 8:
                        System.out.println(" ____________________________________");

                }
            }
        }
    }
    private String stampaRisorsa(int i,int corner){
        List<Item> resources=new ArrayList<Item>();
        MixedCard actualCard=cards.get(i);
        switch (corner){
            case 1:
                if(isFlipped.get(i))resources=actualCard.getFront().getUpLeft().getItems();
                else resources=actualCard.getBack().getUpLeft().getItems();
                break;
            case 2:
                if(isFlipped.get(i))resources=actualCard.getFront().getUpRight().getItems();
                else resources=cards.get(i).getBack().getUpRight().getItems();
                break;
            case 3:
                if(isFlipped.get(i))resources=actualCard.getFront().getBottomLeft().getItems();
                else resources=cards.get(i).getBack().getBottomLeft().getItems();
                break;
            case 4:
                if(isFlipped.get(i))resources=actualCard.getFront().getBottomRight().getItems();
                else resources=cards.get(i).getBack().getBottomRight().getItems();
                break;
            case 5:
                if(isFlipped.get(i)){
                    resources=new ArrayList<Item>();
                    resources.add(actualCard.getFront().getCenter().getItems().getFirst());
                }
                else return "";
                break;
        }
        return resources.getFirst().toString();
    }
}