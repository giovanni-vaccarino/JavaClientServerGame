package polimi.ingsoft.server.factories;

import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.model.*;

public class PlayerInitialSettingFactory {
    public static PlayerInitialSetting createPlayerInitialSetting(PublicBoard publicBoard, String nickname){
        ResourceCard firstResourceCard = publicBoard.getResource(PlaceInPublicBoard.Slots.DECK);
        ResourceCard secondResourceCard = publicBoard.getResource(PlaceInPublicBoard.Slots.DECK);
        GoldCard goldCard = publicBoard.getGold(PlaceInPublicBoard.Slots.DECK);
        QuestCard firstQuestCard = publicBoard.getQuest(PlaceInPublicBoard.Slots.DECK);
        QuestCard secondQuestCard = publicBoard.getQuest(PlaceInPublicBoard.Slots.DECK);
        //TODO add this when ready to load cards: InitialCard initialCard = publicBoard.getInitial();

        //TODO change playerHand constructor
        PlayerHand<MixedCard> playerHand = new PlayerHand<>();

        //TODO change PlayerIntialSetting constructor like this when ready:
        //          PlayerInitialSetting(nickname, playerHand, firstQuestCard, secondQuestCard);

        return new PlayerInitialSetting(nickname);
    }
}
