package polimi.ingsoft.client;

import polimi.ingsoft.client.cli.CLI;
import polimi.ingsoft.server.Player;

import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    private static final PrintStream printStream = System.out;
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        VirtualServer virtualServer = new VirtualServer();
        CLI cli = new CLI(scanner, printStream, virtualServer);

        Player player = cli.runJoinMatchRoutine();
    }
}
