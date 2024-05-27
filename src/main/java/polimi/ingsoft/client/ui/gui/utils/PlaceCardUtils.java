package polimi.ingsoft.client.ui.gui.utils;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class PlaceCardUtils {

    public static void placeSameCard(int x, int y, GridPane gridPane){
        ImageView cardImg = new ImageView(new Image("/polimi/ingsoft/demo/graphics/img/card/frontCard/mixedCard/frontResourceCard(1).jpg"));
        placeCard(x,y,gridPane,cardImg);
    }

    public static void placeCardString(int x,int y, GridPane gridPane, String path){
        ImageView cardImg = new ImageView(new Image(path));
        placeCard(x,y,gridPane,cardImg);
    }
    public static void placeCard(int x, int y, GridPane gridPane, ImageView imageView){

        imageView.setFitWidth(140);
        imageView.setFitHeight(100);

        imageView.setViewport(new Rectangle2D(61, 64, 908, 628));
        imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 2, 2);");

        // Add the ImageView to the specific cell in the GridPane
        gridPane.add(imageView, x, y);
    }
}
