package polimi.ingsoft.client.ui.cli;

import java.io.*;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SafeScanner {
    private Thread waitThread = new Thread(() -> {});
    private final InputStream in;
    private final PrintStream out;

    public SafeScanner(InputStream in, PrintStream out) {
        this.out = out;
        this.in = in;
    }

    public void readInt(String inputMessage, String errorMessage, Predicate<Integer> validityCheck, Consumer<Integer> callback) {
        if (waitThread.isAlive()) {
            waitThread.interrupt();
        }

        waitThread = new Thread(() -> {
            boolean isValid = false;
            Integer value;
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            try {
                do {
                    out.print(inputMessage);
                    try {
                        while (true) {
                            if (Thread.interrupted()) throw new InterruptedException();
                            if (in.available() > 0) {
                                out.println("Reading from thread: " + Thread.currentThread().getName());
                                value = Integer.valueOf(reader.readLine());
                                out.println("Input: " + value);
                                if (validityCheck.test(value)) {
                                    isValid = true;
                                    callback.accept(value);
                                    reader.close();
                                    break;
                                } else {
                                    out.println(errorMessage);
                                    break;
                                }
                            }
                        }
                    } catch (IOException e) {
                        out.println("IO EXCEP");
                        break;
                    } finally {
                        reader.close();
                    }
                } while (!isValid);
            } catch (InterruptedException e) {
                out.println("INTERRUPTED: " + Thread.currentThread().getName());
            } catch (IOException ignored) { }
        });
        waitThread.start();
    }

    public void readLine(String inputMessage, Consumer<String> callback) {
        if (waitThread.isAlive())
            waitThread.interrupt();

        waitThread = new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String value;
            out.print(inputMessage);
            try {
                while (true) {
                    if (in.available() > 0) {
                        out.println("Reading from thread: " + Thread.currentThread().getName());
                        value = reader.readLine();
                        callback.accept(value);
                        reader.close();
                    }
                }
            } catch (IOException ignored) { }
        });
        waitThread.start();
    }
}
