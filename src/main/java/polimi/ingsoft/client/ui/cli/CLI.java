package polimi.ingsoft.client.ui.cli;

import polimi.ingsoft.client.common.Client;
import polimi.ingsoft.client.ui.cli.pages.*;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.*;
import polimi.ingsoft.client.ui.UI;
import polimi.ingsoft.server.model.*;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CLI extends UI {
    enum CLIPhase {
        WELCOME, NICKNAME_CHOICE, MATCH_CHOICE, LOBBY, GAME_INIT, GAME
    }

    private final PrintStream out;
    private CLIPhase phase = CLIPhase.WELCOME;

    private final NicknameManager nicknameManager;
    private final MatchManager matchManager;
    private final LobbyManager lobbyManager;
    private final MatchInitializationManager matchInitializationManager;
    private final GameManager gameManager;
    private CLIPhaseManager currentManager;

    public CLI(Scanner in, PrintStream out, Client client) {
        super(client);
        this.out = out;
        nicknameManager = new NicknameManager(in, out, this);
        matchManager = new MatchManager(in, out, this);
        lobbyManager = new LobbyManager(out, this);
        matchInitializationManager = new MatchInitializationManager(in, out, this);
        gameManager = new GameManager(in, out, this);
    }

    public void startNicknameManager() {
        phase = CLIPhase.NICKNAME_CHOICE;
        changeManager(nicknameManager);
    }

    public void returnNicknameManager() {
        startMatchManager();
    }

    public void startMatchManager() {
        phase = CLIPhase.MATCH_CHOICE;
        changeManager(matchManager);
    }

    public void returnMatchManager() {
        startLobbyManager();
    }

    public void startLobbyManager() {
        phase = CLIPhase.LOBBY;
        changeManager(lobbyManager);
    }

    public void returnLobbyManager() {
        startMatchInitializationManager();
    }

    public void startMatchInitializationManager() {
        phase = CLIPhase.GAME_INIT;
        changeManager(matchInitializationManager);
    }

    public void returnMatchInitializationManager(
            PlayerHand hand,
            QuestCard personalQuestCard,
            InitialCard initialCard,
            boolean isInitialCardFacingUp,
            GameState gameState
    ) {
        startGameManager(hand, personalQuestCard, initialCard, isInitialCardFacingUp, gameState);
    }

    public void startGameManager(
            PlayerHand hand,
            QuestCard personalQuestCard,
            InitialCard initialCard,
            boolean isInitialCardFacingUp,
            GameState gameState
    ) {
        phase = CLIPhase.GAME;
        gameManager.initializeModel(hand, personalQuestCard, initialCard, isInitialCardFacingUp, gameState);
        changeManager(gameManager);
    }

    private void changeManager(CLIPhaseManager manager) {
        currentManager = manager;
        manager.start();
    }

    public void updateNickname() {
        if (phase == CLIPhase.NICKNAME_CHOICE) {
            nicknameManager.updateNickname();
        }
    }

    public void showWelcomeScreen() {
        if (phase == CLIPhase.WELCOME) {
            out.println(MESSAGES.WELCOME.getValue());
            startNicknameManager();
        }
    }

    public void updateMatchesList(List<Integer> matches) {
        if (phase == CLIPhase.MATCH_CHOICE) {
            matchManager.updateMatches(matches);
        }
    }

    @Override
    public void showUpdateMatchJoin() {
        if (phase == CLIPhase.MATCH_CHOICE) {
            matchManager.updateJoinMatch();
        }
    }

    @Override
    public void updatePlayersInLobby(List<String> nicknames) {
        if (phase == CLIPhase.LOBBY) {
            lobbyManager.showWaitingPlayers(nicknames);
        }
    }

    @Override
    public void showMatchCreate(Integer matchId) {
        if (phase == CLIPhase.MATCH_CHOICE) {
            matchManager.updateCreateMatch(matchId);
        }
    }

    @Override
    public void reportError(ERROR_MESSAGES errorMessage) {
        currentManager.parseError(errorMessage);
    }

    @Override
    public void showUpdateGameState(GameState gameState) {
        GAME_PHASE gamePhase = gameState.getGamePhase();

        if (phase == CLIPhase.LOBBY && gamePhase == GAME_PHASE.INITIALIZATION) {
            lobbyManager.endLobby();
        } else if (phase == CLIPhase.GAME_INIT) {
            matchInitializationManager.updateGameState(gameState);
        } else if (phase == CLIPhase.GAME) {
            gameManager.updateGameState(gameState);
        }
    }

    @Override
    public void showUpdateInitialSettings(PlayerInitialSetting playerInitialSetting) {
        if (phase == CLIPhase.GAME_INIT) {
            matchInitializationManager.updatePlayerInitialSettings(playerInitialSetting);
        }
    }

    @Override
    public void setPlayerBoards(Map<String, Board> playerBoard) {
        if (phase == CLIPhase.GAME_INIT) {
            gameManager.initializeBoards(playerBoard);
        }
    }

    @Override
    public void createPublicBoard(PlaceInPublicBoard<ResourceCard> resourceCards, PlaceInPublicBoard<GoldCard> goldCards, PlaceInPublicBoard<QuestCard> questCards) {
        if (phase == CLIPhase.GAME_INIT) {
            gameManager.initializePublicBoards(resourceCards, goldCards, questCards);
        }
    }
}
