package polimi.ingsoft.client.ui.gui.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ScoreUtils {
    public static int GetXFromScore(int s){
        int x=41; //first post default

        switch(s){
            case 0:
                x=41;
                break;
            case 1:
                x=85;
                break;
            case 2:
                x=129;
                break;
            case 3:
                x=153;
                break;
            case 4:
                x=108;
                break;
            case 5:
                x=64;
                break;
            case 6:
                x=19;
                break;
            case 7:
                x=19;
                break;

            case 8:
                x=62;
                break;

            case 9:
                x=108;
                break;

            case 10:
                x=153;
                break;

            case 11:
                x=153;
                break;

            case 12:
                x=108;
                break;

            case 13:
                x=63;
                break;

            case 14:
                x=19;
                break;

            case 15:
                x=19;
                break;

            case 16:
                x=63;
                break;

            case 17:
                x=108;
                break;

            case 18:
                x=153;
                break;

            case 19:
                x=153;
                break;

            case 20:
                x=86;
                break;

            case 21:
                x=19;
                break;

            case 22:
                x=19;
                break;

            case 23:
                x=19;
                break;

            case 24:
                x=44;
                break;

            case 25:
                x=86;
                break;

            case 26:
                x=127;
                break;

            case 27:
                x=153;
                break;

            case 28:
                x=153;
                break;

            case 29:
                x=86;
                break;
        }
        return x;
    }

    public static int GetYFromScore(int s){
        int y=331;//first post default

        switch(s){
            case 0:
                y=331;
                break;
            case 1:
                y=331;
                break;
            case 2:
                y=331;
                break;
            case 3:
                y=294;
                break;
            case 4:
                y=294;
                break;
            case 5:
                y=294;
                break;
            case 6:
                y=294;
                break;
            case 7:
                y=251;
                break;

            case 8:
                y=251;
                break;

            case 9:
                y=251;
                break;

            case 10:
                y=251;
                break;

            case 11:
                y=219;
                break;

            case 12:
                y=219;
                break;

            case 13:
                y=219;
                break;

            case 14:
                y=219;
                break;

            case 15:
                y=180;
                break;

            case 16:
                y=180;
                break;

            case 17:
                y=180;
                break;

            case 18:
                y=180;
                break;

            case 19:
                y=143;
                break;

            case 20:
                y=123;
                break;

            case 21:
                y=143;
                break;

            case 22:
                y=104;
                break;

            case 23:
                y=67;
                break;

            case 24:
                y=35;
                break;

            case 25:
                y=28;
                break;

            case 26:
                y=35;
                break;

            case 27:
                y=67;
                break;

            case 28:
                y=104;
                break;

            case 29:
                y=75;
                break;
        }

        return y;
    }

    public static void placeSingolScore(int x, int y, String imageUrl, ImageView imageView){
        Image image = new Image(imageUrl);

        imageView.setFitWidth(29);
        imageView.setFitHeight(29);

        imageView.setLayoutX(x);
        imageView.setLayoutY(y);

        imageView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 2, 2);");

        imageView.setImage(image);
    }
}
