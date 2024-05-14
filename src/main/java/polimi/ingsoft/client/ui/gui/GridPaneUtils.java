package polimi.ingsoft.client.ui.gui;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class GridPaneUtils {

    public GridPaneUtils() {}

    public static void eraseGridPane(GridPane gridPane, int rowNum, int colNum) {
        for (int c=0; c<colNum; c++){
            for (int r=0; r<rowNum; r++){
                removeImageViewIfExists(gridPane,r,c);
            }
        }
        // For multiple img:
        for (int c=0; c<colNum; c++){
            for (int r=0; r<rowNum; r++){
                removeImageViewIfExists(gridPane,r,c);
            }
        }
        for (int c=0; c<colNum; c++){
            for (int r=0; r<rowNum; r++){
                removeImageViewIfExists(gridPane,r,c);
            }
        }
    }
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

    public static ImageView getImageViewByCoordinates(GridPane gridPane, int row, int column) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == row &&
                    GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == column) {
                if (node instanceof ImageView) {
                    return (ImageView) node;
                }
            }
        }
        return null; // Not found
    }
}
