package polimi.ingsoft.client.ui.cli;

import polimi.ingsoft.server.enumerations.ERROR_MESSAGES;
import java.io.PrintStream;
import java.util.Scanner;

public class IPChoiceCLI {
    private final Scanner in;
    private final PrintStream out;

    public IPChoiceCLI(Scanner in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    public String runChooseIpRoutine() {
        out.println(MESSAGES.CHOOSE_SEVER_IP.getValue());

        String serverIp;
        boolean isValid = false;

        do {
            serverIp = in.nextLine();
            if (isValidIP(serverIp)){
                isValid = true;
            }
            else {
                out.println(ERROR_MESSAGES.PROTOCOL_NUMBER_OUT_OF_BOUND.getValue());
            }
        } while (!isValid);

        return serverIp;
    }

    public static boolean isValidIP(String ip) {
        String ipv4Pattern = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        return ip.matches(ipv4Pattern);
    }
}
