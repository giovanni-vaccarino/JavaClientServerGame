package polimi.ingsoft.client.ui.cli;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SafeScanner {
    private Thread waitThread = new Thread(()->{});
    private final Scanner in;
    private final PrintStream out;

    public SafeScanner(Scanner in, PrintStream out) {
        this.in = in;
        this.out = out;
    }

    public void readInt(String inputMessage, String errorMessage, Predicate<Integer> validityCheck, Consumer<Integer> callback) {
        if (waitThread.isAlive())
            waitThread.interrupt();

        waitThread = new Thread(() -> {
            boolean isValid = false;
            int value;

            do {
                out.print(inputMessage);
                value = in.nextInt();
                in.nextLine();
                if (validityCheck.test(value)) {
                    isValid = true;
                } else {
                    out.println(errorMessage);
                }
            } while (!isValid);
            callback.accept(value);
        });
        waitThread.start();
    }

    public void readLine(String inputMessage, Consumer<String> callback) {
        if (waitThread.isAlive())
            waitThread.interrupt();

        waitThread = new Thread(() -> {
            String value;
            out.print(inputMessage);
            value = in.nextLine();
            callback.accept(value);
        });
        waitThread.start();
    }
}
