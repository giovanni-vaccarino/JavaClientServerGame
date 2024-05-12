package polimi.ingsoft.client.ui.cli;

import polimi.ingsoft.server.enumerations.Object;
import polimi.ingsoft.server.enumerations.Resource;
import polimi.ingsoft.server.model.*;

import java.util.*;

public class ClientHand {

    public static final String RED = "\u001B[41m";
    public static final String GREEN = "\u001B[42m";
    public static final String BLUE = "\u001B[46m";//"\u001B[44m";
    public static final String PURPLE = "\u001B[45m";
    public static final String YELLOW="\u001B[43m";
    public static final String BLACKTEXT="\u001B[30m";
    public static final String RESET = "\u001B[0m";
    private String color;
    private ArrayList<MixedCard> cards;
    private ArrayList<Boolean> isFlipped; //true: visualizzo back false:visualizzo front

    public ClientHand(){
        this.cards=new ArrayList<>();
        this.isFlipped=new ArrayList<>();
    }
    public void addCard(MixedCard card) {
        if(cards.size()<3){
            this.cards.add(card);
            this.isFlipped.add(true);
            }
        }
    public void print(){
        for(int j=0;j<9;j++){
            for(int i=0;i<cards.size();i++){
                if(cards.get(i).getFront().getCenter().getItems().size()==1) {
                    switch (cards.get(i).getFront().getCenter().getItems().getFirst()) {
                        case Resource.BUTTERFLY:
                            this.color = PURPLE;
                            break;
                        case Resource.LEAF:
                            this.color = GREEN;
                            break;
                        case Resource.MUSHROOM:
                            this.color = RED;
                            break;
                        case Resource.WOLF:
                            this.color = BLUE;
                            break;
                    }
                }else this.color=YELLOW;
                if(j<3) {
                    System.out.print(printResource(i, j,1,color));
                    System.out.print(printCenter(cards.get(i),i,j,color));
                    System.out.print(printResource(i,j, 2,color));
                }
                else if(j<6) {
                    System.out.print(color + "          ");
                    System.out.print(printCenter(cards.get(i),i,j,color));
                    System.out.print(color + "          ");
                }
                else{
                     System.out.print(printResource(i, j,3,color));
                     System.out.print(printCenter(cards.get(i),i,j,color));
                     System.out.print(printResource(i, j,4,color));
                    }
                System.out.print(RESET+"    ");
                }
            System.out.print("\n");
            }
        }

    private String printResource(int i,int j,int corner,String printColor){
        Item color=null;
        Face front=cards.get(i).getFront(),
                back=cards.get(i).getBack();
        String outColor="";
        try{
            switch (corner){
                case 1:
                    if(front.getUpLeft()!=null&&front.getUpLeft().getItems()!=null&&!isFlipped.get(i))
                        color = front.getUpLeft().getItems().getFirst();
                    else if(isFlipped.get(i)&&back.getUpLeft()!=null&&back.getUpLeft().getItems()!=null)
                        color=back.getUpLeft().getItems().getFirst();
                    break;
                case 2:
                    if(front.getUpRight()!=null&&front.getUpRight().getItems()!=null&&!isFlipped.get(i))
                        color=front.getUpRight().getItems().getFirst();
                    else if(isFlipped.get(i)&&back.getUpRight()!=null&&back.getUpRight().getItems()!=null)
                        color=back.getUpRight().getItems().getFirst();
                    break;
                case 3:
                    if(front.getBottomLeft()!=null&&front.getBottomLeft().getItems()!=null&&!isFlipped.get(i))
                        color=front.getBottomLeft().getItems().getFirst();
                    else if(isFlipped.get(i)&&back.getBottomLeft()!=null&&back.getBottomLeft().getItems()!=null)
                        color=back.getBottomLeft().getItems().getFirst();
                    break;
                case 4:
                    if(front.getBottomRight()!=null&&front.getBottomRight().getItems()!=null&&!isFlipped.get(i))
                        color=front.getBottomRight().getItems().getFirst();
                    else if(isFlipped.get(i)&&back.getBottomRight()!=null&&back.getBottomRight().getItems()!=null)
                        color=back.getBottomRight().getItems().getFirst();
                    break;
                }
        }catch(NoSuchElementException e){
            outColor=YELLOW+BLACKTEXT;
            if(j==2||j==8)return(outColor+"!________!");
            else if(j==6||j==0)return(outColor+"¡‾‾‾‾‾‾‾‾¡");
            else return(outColor+"|        |");
        }
        if(color==null)return(printColor+"          ");
        else if(color==Object.POTION&&(j==1||j==7))return(YELLOW+BLACKTEXT+"| POTION |");
        else if(color==Object.SCROLL&&(j==1||j==7))return(YELLOW+BLACKTEXT+"| SCROLL |");
        else if(color==Object.FEATHER&&(j==1||j==7))return(YELLOW+BLACKTEXT+"|FEATHER |");
        else if(color==Resource.BUTTERFLY)outColor=PURPLE+BLACKTEXT;
        else if(color==Resource.WOLF)outColor=BLUE+BLACKTEXT;
        else if(color==Resource.MUSHROOM)outColor=RED+BLACKTEXT;
        else if(color==Resource.LEAF)outColor=GREEN+BLACKTEXT;
        else outColor=YELLOW+BLACKTEXT;
        if(j==2||j==8)return(outColor+"!________!");
        else if(j==6||j==0)return(outColor+"¡‾‾‾‾‾‾‾‾¡");
        else return(outColor+"|        |");
    }
    public void flip(int i){
        Boolean flip=!this.isFlipped.get(i);
        this.isFlipped.remove(i);
        this.isFlipped.add(i,flip);
    }
    private String printCenter(MixedCard card, int i, int j, String actualColor){
        if(isFlipped.get(i)&&j!=0)return actualColor+"              ";
        else if(j==0&&isFlipped.get(i))return printFirstRow(card,i,j,actualColor);
        else{
            CenterSpace center;
            Resource resource = null;
            String print = "";
            center=card.getFront().getCenter();
            switch(center.getItems().size()) {
                case 1:
                    if(j<3||j>5)return actualColor+"              ";
                    resource = center.getItems().getFirst();
                    switch (j) {
                        case 3:
                            print = BLACKTEXT + "¡‾‾‾‾‾‾¡";
                            break;
                        case 4:
                            print = BLACKTEXT + "|      |";
                            break;
                        case 5:
                            print = BLACKTEXT + "!______!";
                            break;
                    }
                    break;
                case 2:
                    if(j<1||j>6)return actualColor+"              ";
                    if(j<4)resource = center.getItems().getFirst();
                    else resource=center.getItems().get(1);
                    switch (j) {
                        case 1, 4:
                            print = BLACKTEXT + "¡‾‾‾‾‾‾¡";
                            break;
                        case 2, 5:
                            print = BLACKTEXT + "|      |";
                            break;
                        case 3:
                            print = BLACKTEXT + "!      !";
                            break;
                        case 6:
                            print = BLACKTEXT + "!______!";
                            break;
                    }
                    break;
                case 3:
                    if(j==7)return actualColor+"              ";
                    if(j<3)resource = center.getItems().getFirst();
                    else if(j<5)resource=center.getItems().get(1);
                    else resource=center.getItems().get(2);
                    switch (j) {
                        case 1, 3, 5:
                            print = BLACKTEXT + "¡‾‾‾‾‾‾¡";
                            break;
                        case 2, 4:
                            print = BLACKTEXT + "!      !";
                            break;
                        case 6:
                            print = BLACKTEXT + "!______!";
                            break;
                    }
                    break;
                default:
                    return actualColor+"              ";
            }
            switch(resource){
                case Resource.LEAF: return actualColor+"   "+GREEN+print+actualColor+"   ";
                case Resource.BUTTERFLY: return actualColor+"   "+PURPLE+print+actualColor+"   ";
                case Resource.MUSHROOM: return actualColor+"   "+RED+print+actualColor+"   ";
                case Resource.WOLF: return actualColor+"   "+BLUE+print+actualColor+"   ";
                default:
                    return actualColor+"              ";
                }
        }
    }
    public ArrayList<Boolean>getIsFlipped(){
        return this.isFlipped;
    }

    private String printFirstRow(MixedCard card, int i, int j, String actualColor){
        if(card.getScore(!isFlipped.get(i))!=0&&card.getPointPattern()==null)
            return actualColor+BLACKTEXT+"     | "+card.getScore(!isFlipped.get(i))+"|     ";
        else if(cards.get(i).getScore(!isFlipped.get(i))!=0&&card.getPointPattern()!=null) {
            HashMap<Item,Integer>cost=((ItemPattern)card.getPointPattern()).getCost();
            if(cost.get(Object.FEATHER)!=0)return actualColor+BLACKTEXT+" |"+card.getScore(!isFlipped.get(i))+"x Feather| ";
            else if (cost.get(Object.POTION)!=0)return actualColor+BLACKTEXT+" |"+card.getScore(!isFlipped.get(i)) +"x Potion|  ";
            else if (cost.get(Object.SCROLL)!=0)return actualColor+BLACKTEXT+" |"+card.getScore(!isFlipped.get(i)) +"x Scroll|  ";
        }
        else return actualColor+"              ";
        return actualColor+"              ";
    }

//    private String printLastRow(ResourceCard card, int i, int j, String actualColor){
//        if(j==0&&cards.get(i).getScore(!isFlipped.get(i))!=0)
//            return "     | "+cards.get(i).getScore(!isFlipped.get(i))+"|     ";
//
//        if(j==8&&isFlipped.get(i)){
//
//        }
//    }

}
