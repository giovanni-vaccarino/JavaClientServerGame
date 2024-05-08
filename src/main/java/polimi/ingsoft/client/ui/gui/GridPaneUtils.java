package polimi.ingsoft.client.ui.gui;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class GridPaneUtils {

    public static void removeImageViewIfExists(GridPane gridPane, int row, int col) {
        Node node = getNodeFromGridPane(gridPane, row, col);
        if (node != null && node instanceof ImageView) {
            gridPane.getChildren().remove(node);
        }
    }

    private static Node getNodeFromGridPane(GridPane gridPane, int row, int col) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return node;
            }
        }
        return null;
    }
}
