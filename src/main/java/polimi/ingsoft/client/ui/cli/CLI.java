package polimi.ingsoft.client.ui.cli;

import polimi.ingsoft.client.common.Client;
import polimi.ingsoft.client.ui.cli.pages.LobbyManager;
import polimi.ingsoft.client.ui.cli.pages.MatchInitializationManager;
import polimi.ingsoft.client.ui.cli.pages.MatchManager;
import polimi.ingsoft.server.controller.GameState;
import polimi.ingsoft.server.controller.PlayerInitialSetting;
import polimi.ingsoft.server.enumerations.*;
import polimi.ingsoft.client.ui.UI;
import polimi.ingsoft.server.model.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CLI extends UI {
    enum CLIState {
        WELCOME,
        WAITING_FOR_MATCHES,
        WAITING_FOR_JOIN,
        WAITING_FOR_CREATE,
        WAITING_FOR_NICKNAME,
        WAITING_FOR_PLAYERS,
        SELECTING_COLOR,
        WAITING_FOR_COLOR,
        WAITING_FOR_OTHERS_TO_SELECT_COLOR,
        SELECTING_INITIAL_CARD_FACE,
        WAITING_FOR_INITIAL_CARD_FACE,
        WAITING_FOR_OTHERS_TO_SELECT_INITIAL_CARD_FACE,
        SELECTING_QUEST_CARD,
        WAITING_FOR_QUEST_CARD,
        WAITING_FOR_OTHERS_TO_SELECT_QUEST_CARD,
        READY_TO_PLAY,
        WAITING_FOR_TURN,
        PLACE,
        WAITING_FOR_PLACE,
        DRAW,
        WAITING_FOR_DRAW
    }
    private final Scanner in;
    private final PrintStream out;

    private CLIState state = CLIState.WELCOME;

    private final MatchManager matchManagerPage;
    private final LobbyManager lobbyManager;
    private final MatchInitializationManager matchInitializationManager;

    public CLI(Scanner in, PrintStream out, Client client) {
        super(client);
        this.in = in;
        this.out = out;
        matchManagerPage = new MatchManager(in, out, this);
        lobbyManager = new LobbyManager(out);
        matchInitializationManager = new MatchInitializationManager(in, out, this);
    }

    public void updateNickname() {
        if (state == CLIState.WAITING_FOR_NICKNAME) {
            out.println(MESSAGES.NICKNAME_UPDATED.getValue());
            try {
                getClient().getMatches(this.getClient());
                state = CLIState.WAITING_FOR_MATCHES;
            } catch (IOException e) {
                out.println(ERROR_MESSAGES.UNABLE_TO_LIST_MATCHES.getValue());
            }
        }
    }

    public void showWelcomeScreen() {
        out.println(MESSAGES.WELCOME.getValue());

        String candidateNickname;
        out.print(MESSAGES.CHOOSE_NICKNAME.getValue());
        candidateNickname = in.nextLine();
        setNickname(candidateNickname);
        state = CLIState.WAITING_FOR_NICKNAME;
    }

    public void updateMatchesList(List<Integer> matches) {
        if (state == CLIState.WAITING_FOR_MATCHES) {
            matchManagerPage.updateMatchesList(matches);
            matchManagerPage.showMatchesList();
            matchManagerPage.runMatchRoutine();
        }
    }

    @Override
    public void showUpdateMatchJoin() {
        if (state == CLIState.WAITING_FOR_JOIN) {
            out.println(MESSAGES.JOINED_MATCH.getValue());
            state = CLIState.WAITING_FOR_PLAYERS;
        }
    }

    @Override
    public void updatePlayersInLobby(List<String> nicknames) {
        if (state == CLIState.WAITING_FOR_PLAYERS) {
            lobbyManager.setNicknames(nicknames);
            lobbyManager.showWaitingPlayers();
        }
    }

    @Override
    public void showMatchCreate(Integer matchId) {
        if (state == CLIState.WAITING_FOR_CREATE) {
            matchManagerPage.updateMatchesList(matchId);
            out.println(MESSAGES.CREATED_MATCH.getValue());
            joinMatch(matchId);
        }
    }

    @Override
    public void reportError(ERROR_MESSAGES errorMessage) {
        out.println("ERROR: " + errorMessage.getValue());
        if (state == CLIState.WAITING_FOR_COLOR && errorMessage == ERROR_MESSAGES.COLOR_ALREADY_PICKED) {
            matchInitializationManager.selectColor();
        }
    }

    @Override
    public void showUpdateGameState(GameState gameState) {
        if (gameState.getGamePhase() == GAME_PHASE.INITIALIZATION) {
            // Initialization phase
            if (state == CLIState.WAITING_FOR_PLAYERS
                    && gameState.getCurrentInitialStep() == INITIAL_STEP.COLOR) {
                state = CLIState.SELECTING_COLOR;
                matchInitializationManager.selectColor();
            } else if (state == CLIState.WAITING_FOR_OTHERS_TO_SELECT_COLOR
                            && gameState.getCurrentInitialStep() == INITIAL_STEP.FACE_INITIAL) {
                state = CLIState.SELECTING_INITIAL_CARD_FACE;
                matchInitializationManager.selectInitialCardFace();
            } else if (state == CLIState.WAITING_FOR_OTHERS_TO_SELECT_INITIAL_CARD_FACE
                            && gameState.getCurrentInitialStep() == INITIAL_STEP.QUEST_CARD) {
                state = CLIState.SELECTING_QUEST_CARD;
                matchInitializationManager.selectQuestCard();
            }
        } else if (gameState.getGamePhase() == GAME_PHASE.PLAY) {
            if (state == CLIState.READY_TO_PLAY) {
                // Game ready to start
                out.println(MESSAGES.GAME_START.getValue());
            }

            if (gameState.getCurrentTurnStep() == TURN_STEP.PLACE) {
                String currentPlayerNickname = gameState.getCurrentPlayer().getNickname();
                out.println(MESSAGES.CURRENT_PLAYER.getValue() + currentPlayerNickname);

                //Board currentPlayerBoard = getPlayerBoards().get(currentPlayerNickname);
                // Print current player board

                if (Objects.equals(currentPlayerNickname, getNickname())) {
                    // Your turn

                    // Print player hand
                    // Wait for place
                    state = CLIState.PLACE;
                }
            } else {
                String currentPlayerNickname = gameState.getCurrentPlayer().getNickname();

                if (Objects.equals(currentPlayerNickname, getNickname())) {
                    // Print public boards
                    // Wait for draw
                    state = CLIState.DRAW;
                }
            }

        }
    }

    @Override
    public void showUpdateInitialSettings(PlayerInitialSetting playerInitialSetting) {
        matchInitializationManager.setPlayerInitialSetting(playerInitialSetting);
        if (state == CLIState.WAITING_FOR_COLOR
                && playerInitialSetting.getColor() != null) {
            state = CLIState.WAITING_FOR_OTHERS_TO_SELECT_COLOR;
        } else if (state == CLIState.WAITING_FOR_INITIAL_CARD_FACE
                && playerInitialSetting.getIsInitialFaceUp() != null) {
            state = CLIState.WAITING_FOR_OTHERS_TO_SELECT_INITIAL_CARD_FACE;
        } else if (state == CLIState.WAITING_FOR_QUEST_CARD
                && playerInitialSetting.getQuestCard() != null) {
            state = CLIState.WAITING_FOR_OTHERS_TO_SELECT_QUEST_CARD;
        }
    }

    @Override
    public void createPublicBoard(PlaceInPublicBoard<ResourceCard> resourceCards, PlaceInPublicBoard<GoldCard> goldCards, PlaceInPublicBoard<QuestCard> questCards) {
        if (state == CLIState.WAITING_FOR_OTHERS_TO_SELECT_QUEST_CARD) {
            super.createPublicBoard(resourceCards, goldCards, questCards);
            state = CLIState.READY_TO_PLAY;
        }
    }

    public void setColor(PlayerColor playerColor) {
        super.setColor(playerColor);
        state = CLIState.WAITING_FOR_COLOR;
    }

    public void setIsFaceInitialCardUp(boolean isFaceInitialCardUp) {
        super.setIsFaceInitialCardUp(isFaceInitialCardUp);
        state = CLIState.WAITING_FOR_INITIAL_CARD_FACE;
    }

    public void setQuestCard(QuestCard questCard) {
        super.setQuestCard(questCard);
        state = CLIState.WAITING_FOR_QUEST_CARD;
    }

    public void createMatch(int requestedNumPlayers) {
        try {
            getClient().createMatch(getNickname(), requestedNumPlayers);
            state = CLIState.WAITING_FOR_CREATE;
        } catch (IOException e) {
            out.println(ERROR_MESSAGES.UNABLE_TO_CREATE_MATCH.getValue());
        }
    }

    public void joinMatch(Integer matchId) {
        out.println(MESSAGES.JOINING_MATCH.getValue());
        try {
            getClient().joinMatch(getNickname(), matchId);
            state = CLIState.WAITING_FOR_JOIN;
        } catch (IOException e) {
            out.println(ERROR_MESSAGES.UNABLE_TO_JOIN_MATCH.getValue());
        }
    }
}
