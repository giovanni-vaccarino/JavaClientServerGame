package polimi.ingsoft.client.ui;

import java.io.IOException;
import java.util.List;

public abstract class UI {
    public abstract void showWelcomeScreen() throws IOException;
    public abstract void showMatchesList(List<Integer> matches) throws IOException;
}
