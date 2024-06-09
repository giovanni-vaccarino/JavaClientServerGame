package polimi.ingsoft.client.ui.cli;

import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ProtocolChoiceCLI {
    private final Scanner in;
    private final PrintStream out;

    public ProtocolChoiceCLI(Scanner in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    public Protocols runChooseProtocolRoutine() {
        out.println(MESSAGES.CHOOSE_PROTOCOL_LIST.getValue());
        Protocols[] protocols = Protocols.values();
        for (int i = 0; i < protocols.length; i++)
            out.printf("%d. %s%n", i + 1, protocols[i]);

        int protocolIndex=5;
        boolean isValid = false;

        do {
            out.print(MESSAGES.CHOOSE_PROTOCOL.getValue());
            try {
                protocolIndex = in.nextInt();
            }catch(InputMismatchException e){
                out.print(MESSAGES.ERROR.getValue());
            }
            in.nextLine();
            if (protocolIndex <= protocols.length) isValid = true;
            else out.println(ERROR_MESSAGES.PROTOCOL_NUMBER_OUT_OF_BOUND.getValue());
        } while (!isValid);

        return protocols[protocolIndex - 1];
    }
}
