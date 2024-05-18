package polimi.ingsoft.client.ui.cli;

import java.io.PrintStream;
import java.util.Scanner;

public class Printer {
    private final Scanner in;
    private final PrintStream out;

    public Printer(Scanner in, PrintStream out) {
        this.in = in;
        this.out = out;
    }
    public void printFromMain(MainArguments argument){
        out.println(MESSAGES.CHOOSE_ACTION);
        switch(argument){
            case HELP:
                out.println(MESSAGES.HELPMAIN);

        }
    }
}
