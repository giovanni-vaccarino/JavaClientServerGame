package polimi.ingsoft.client.ui.cli;

import polimi.ingsoft.client.ERROR_MESSAGES;

import java.io.PrintStream;
import java.util.Scanner;

public class NickChoiceCLI {
    private final Scanner in;
    private final PrintStream out;

    public NickChoiceCLI(Scanner in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    public String runChooseNickRoutine() {
        String nick;
        boolean isValid = false;

        do {
            out.print(MESSAGES.CHOOSE_NICKNAME.getValue());
            nick = in.next();
            in.nextLine();
            if (true) isValid = true;
            else out.println(ERROR_MESSAGES.NICKNAME_NOT_AVAILABLE.getValue());
        } while (!isValid);

        return nick;
    }
}


