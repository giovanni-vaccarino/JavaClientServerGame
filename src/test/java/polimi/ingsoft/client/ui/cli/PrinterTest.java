package polimi.ingsoft.client.ui.cli;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class PrinterTest {

    static Printer a;
    @BeforeAll
    public static void init(){
        a=new Printer(new PrintStream(System.out));
    }
    @Test
    void printFromMain() {
        a.printFromMain("HELP");
        a.printFromMain("CASO");
    }

//      @Test
//    void printFromBoard() {
//        a.printFromBoard("HELP");
//        a.printFromBoard("CASO");
//    }

//    @Test
//    void printFromPublicBoard() {
//        a.printFromPublicBoard("HELP");
//        a.printFromPublicBoard("getcard");
//        a.printFromPublicBoard("GolD");
//        a.printFromPublicBoard("resource");
//        a.printFromPublicBoard("CASO");
//    }
}