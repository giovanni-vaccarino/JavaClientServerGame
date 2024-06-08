package polimi.ingsoft.client.ui.gui.utils.selectedCardUtils;

import java.util.ArrayList;
import java.util.List;

public class ShowSelectedCardUtils {
    static int currentKey = 0;
    static List<Integer> keyUsed = new ArrayList<>(currentKey);

    public static int newKey(){
        int newKeyValue = keyUsed.getLast()+1;
        keyUsed.add(newKeyValue);
        return newKeyValue;
    }
    public static void setCurrentKey(int currentKey){
        ShowSelectedCardUtils.currentKey = currentKey;
    }

    public static boolean isCurrentKey(int key){
        if(currentKey == key){
            return true;
        }else{
            return false;
        }
    }
}
