package polimi.ingsoft.client.ui.cli.pages;

import polimi.ingsoft.client.ui.cli.CLI;
import polimi.ingsoft.client.ui.cli.MESSAGES;
import polimi.ingsoft.client.ui.cli.Printer;
import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;

import java.io.PrintStream;
import java.util.Scanner;

public class NicknameManager implements CLIPhaseManager {
    private String nickname;

    private final CLI cli;
    private transient final Scanner in;
    private transient final PrintStream out;

    enum State {
        NONE, SET_NICKNAME, WAITING_FOR_NICKNAME
    }

    private State state = State.NONE;

    public NicknameManager(Scanner in, PrintStream out, CLI cli) {
        this.cli = cli;
        this.in = in;
        this.out = out;
    }

    public void start() {
        state = State.SET_NICKNAME;
        setNickname();
    }

    private void setNickname() {
        Printer.cleanTerminal();
        if (state == State.SET_NICKNAME) {
            out.print(MESSAGES.CHOOSE_NICKNAME.getValue());
            nickname = in.nextLine();
            cli.setNickname(nickname);
            state = State.WAITING_FOR_NICKNAME;
        }
    }

    public void updateNickname() {
        if (state == State.WAITING_FOR_NICKNAME) {
            out.println(MESSAGES.NICKNAME_UPDATED.getValue());
            state = State.NONE;
            cli.returnNicknameManager();
        }
    }

    public void parseError(ERROR_MESSAGES error) {
        if (state == State.WAITING_FOR_NICKNAME) {
            if (error == ERROR_MESSAGES.NICKNAME_NOT_AVAILABLE) {
                out.println("ERROR: " + error.getValue());
                state = State.SET_NICKNAME;
                setNickname();
            }
        }
    }
}
