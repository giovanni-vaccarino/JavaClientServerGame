package polimi.ingsoft.client.ui.gui.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

public class ArrowUtils {

    public static void placeArrow(String dir, ImageView imageView){
        double rotationAngle = 0;
        String id;

        String imageUrl = "/polimi/ingsoft/demo/graphics/img/arrow.PNG"; // Replace with your image URL
        Image image = new Image(imageUrl);

        imageView.setFitWidth(50.0);
        imageView.setFitHeight(50.0);

        if(Objects.equals(dir, "S")){
            rotationAngle = 0.0;
        } else if (Objects.equals(dir, "E")) {
            rotationAngle = -90.0;
        } else if (Objects.equals(dir, "O")) {
            rotationAngle = 90.0;
        } else if (Objects.equals(dir, "N")) {
            rotationAngle = 180.0;
        }
        imageView.setRotate(rotationAngle);

        imageView.setImage(image);
    }
}
