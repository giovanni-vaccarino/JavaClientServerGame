package polimi.ingsoft.server.model.player;

import polimi.ingsoft.server.model.boards.Board;
import polimi.ingsoft.server.model.cards.MixedCard;
import polimi.ingsoft.server.model.cards.QuestCard;
import polimi.ingsoft.server.model.decks.PlayerHand;

import java.io.Serializable;

/**
 * Class that represents a player
 */
public class Player implements Serializable {
    private Board board;
    private PlayerColor color;
    private final PlayerHand hand;
    private QuestCard questCard;
    private final String nickname;

    private Boolean isDisconnected = false;

    /**
     * Creates a new player object
     * @param hand Player's set of cards
     * @param nickname Player's nickname
     * @param color Player's color
     * @param board Player's board
     * @param questCard Player's personal QuestCard
     */

    public Player(PlayerHand hand, String nickname, PlayerColor color, Board board, QuestCard questCard) {
        this.nickname = nickname;
        this.color = color;
        this.hand = hand;
        this.board = board;
        this.questCard = questCard;
    }

    public Boolean getIsDisconnected() {
        return isDisconnected;
    }

    public void setIsDisconnected(Boolean isDisconnected){
        this.isDisconnected = isDisconnected;
    }
    /**
     * Returns Player's board
     * @return Player's board
     */
    public Board getBoard() {
        return this.board;
    }
    /**
     * Returns Player's nickname
     * @return Player's nickname
     */
    public String getNickname(){return this.nickname;}

    /**
     * Returns Player's personal QuestCard
     * @return Player's personal QuestCard
     */
    public QuestCard getQuestCard(){return this.questCard;}


    /**
     * Sets player's personal QuestCard
     * @param questCard the player's new personal QuestCard
     */
    public void setQuestCard(QuestCard questCard){this.questCard = questCard;}


    /**
     * Returns player's PlayerHand
     * @return player's PlayerHand
     */
    public PlayerHand getHand() {
        return hand;
    }

    /**
     * Adds a card to player's PlayerHand
     * @param card the card's that's to be added in player's PlayerHand
     */
    public void addToHand(MixedCard card) {
        this.hand.add(card);
    }
    /**
     * Removes a card from player's PlayerHand
     * @param card the card's that's to be removed from player's PlayerHand
     */
    public void removeFromHand(MixedCard card){
        this.hand.remove(card);
    }
}
