package polimi.ingsoft.server.factories;

import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.model.cards.GoldCard;
import polimi.ingsoft.server.model.cards.InitialCard;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.cards.ResourceCard;
import polimi.ingsoft.server.model.decks.PlayerHand;
import polimi.ingsoft.server.model.publicboard.PlaceInPublicBoard;
import polimi.ingsoft.server.model.publicboard.PublicBoard;

public class PlayerInitialSettingFactory {
    public static PlayerInitialSetting createPlayerInitialSetting(PublicBoard publicBoard, String nickname){
        ResourceCard firstResourceCard = publicBoard.getResource(PlaceInPublicBoard.Slots.DECK);
        ResourceCard secondResourceCard = publicBoard.getResource(PlaceInPublicBoard.Slots.DECK);
        GoldCard goldCard = publicBoard.getGold(PlaceInPublicBoard.Slots.DECK);
        QuestCard firstQuestCard = publicBoard.getQuest(PlaceInPublicBoard.Slots.DECK);
        QuestCard secondQuestCard = publicBoard.getQuest(PlaceInPublicBoard.Slots.DECK);
        InitialCard initialCard = publicBoard.getInitial();

        PlayerHand playerHand = new PlayerHand(firstResourceCard, secondResourceCard, goldCard);

        return new PlayerInitialSetting(nickname, playerHand, firstQuestCard, secondQuestCard, initialCard);
    }
}
