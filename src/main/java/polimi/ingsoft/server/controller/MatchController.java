package polimi.ingsoft.server.controller;

import polimi.ingsoft.server.Player;
import polimi.ingsoft.server.exceptions.WrongPlayerForCurrentTurnException;
import polimi.ingsoft.server.exceptions.WrongStepException;
import polimi.ingsoft.server.model.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MatchController {
    private enum TURN_STEP {
        DRAW, PLACE
    }

    private TURN_STEP currentStep;
    private Player currentPlayer;
    private ArrayList<Player> players = new ArrayList<>();

    private final PublicBoard publicBoard;

    protected Iterator<Player> getPlayers(){
        return players.iterator();
    }

    protected final Scanner in;
    protected final PrintWriter out;

    protected final int matchId;

    public MatchController(
        Scanner in,
        PrintWriter out,
        int matchId,
        PublicBoard publicBoard
    ) {
        this.in = in;
        this.out = out;
        this.matchId = matchId;
        this.publicBoard = publicBoard;
    }

    public int getMatchId() {
        return matchId;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    private void playTurn() {
        Iterator<Player> players = getPlayers();
        while (players.hasNext()) {
            currentPlayer = players.next();

        }
    }

    public void play() {
        boolean lastRound = false;

        do {
            if (isLastRound()) lastRound = true;
            playTurn();
        } while (!lastRound);
    }

    private boolean isLastRound() {
        return false;
    }

    private void validateMove(Player player, TURN_STEP move) throws WrongPlayerForCurrentTurnException, WrongStepException {
        if (player != currentPlayer) throw new WrongPlayerForCurrentTurnException();
        if (currentStep != move) throw new WrongStepException();
    }

    private ResourceCard drawResourceCard(Player player, PlaceInPublicBoard.Slots slot) throws WrongPlayerForCurrentTurnException, WrongStepException {
        validateMove(player, TURN_STEP.DRAW);
        currentStep = TURN_STEP.PLACE;
        return publicBoard.getResource(slot);
    }

    private GoldCard drawGoldCard(Player player, PlaceInPublicBoard.Slots slot) throws WrongPlayerForCurrentTurnException, WrongStepException {
        validateMove(player, TURN_STEP.DRAW);
        currentStep = TURN_STEP.PLACE;
        return publicBoard.getGold(slot);
    }

    private QuestCard drawQuestCard(Player player, PlaceInPublicBoard.Slots slot) throws WrongPlayerForCurrentTurnException, WrongStepException {
        validateMove(player, TURN_STEP.DRAW);
        currentStep = TURN_STEP.PLACE;
        return publicBoard.getQuest(slot);
    }

    private void place(Player player, MixedCard card, Coordinates coordinates, boolean facingUp) throws WrongPlayerForCurrentTurnException, WrongStepException {
        validateMove(player, TURN_STEP.PLACE);
        player.getBoard().add(coordinates, card, facingUp);
    }
}
