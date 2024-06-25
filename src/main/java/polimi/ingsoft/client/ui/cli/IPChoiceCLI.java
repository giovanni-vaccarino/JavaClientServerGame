package polimi.ingsoft.client.ui.cli;

import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;

import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class IPChoiceCLI {
    private final Scanner in;
    private final PrintStream out;

    public IPChoiceCLI(Scanner in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    public IP runGetIPRoutine() {
        out.println("Write here your IP: ");
        String ipInput = in.nextLine();
        String[] ipParts = ipInput.split("\\.");

        while (ipParts.length != 4) {
            out.println("Invalid IP format. Please enter a valid IP (format: n1.n2.n3.n4): ");
            ipInput = in.nextLine();
            ipParts = ipInput.split("\\.");
        }

        try {
            int n1 = Integer.parseInt(ipParts[0]);
            int n2 = Integer.parseInt(ipParts[1]);
            int n3 = Integer.parseInt(ipParts[2]);
            int n4 = Integer.parseInt(ipParts[3]);

            return new IP(n1, n2, n3, n4);
        } catch (NumberFormatException e) {
            out.println("Invalid IP numbers. Please enter a valid IP (format: n1.n2.n3.n4): ");
            return runGetIPRoutine();
        }
    }
}
