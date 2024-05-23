package polimi.ingsoft.client.ui.gui.page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import polimi.ingsoft.client.ui.gui.GUI;
import polimi.ingsoft.client.ui.gui.GUIsingleton;
import polimi.ingsoft.server.enumerations.PlayerColor;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ColorPageController implements Initializable {
    private String color;
    private boolean selected;
    @FXML
    SplitMenuButton colorList;
    @FXML
    Button errButton;
    @FXML
    ImageView colorSelected;

    // Default constructor
    public ColorPageController() {
        GUIsingleton.getInstance().setColorPageController(this);
    }

    public GUI getGui(){
        return GUIsingleton.getInstance().getGui();
    }

    public Stage getStage(){
        return GUIsingleton.getInstance().getStage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errButton.setVisible(false);
        List<PlayerColor> items = List.of(PlayerColor.RED,PlayerColor.BLUE,PlayerColor.GREEN); // CALL MODEL
        resetColor();
        setGameList(items);
    }
    public void resetColor(){
        setColorText("Select color");
        selected = false;
        colorList.setStyle("-fx-background-color: white;");
        colorSelected.setVisible(false);
    }
    public void setColorText(String s){
        color=String.valueOf(s);
        colorList.setText(color);
    }

    public void setGameList(List<PlayerColor> colors) {
        colorList.getItems().clear();
        for (PlayerColor color : colors) {
            MenuItem menuItem = new MenuItem(color.toString().toLowerCase());
            menuItem.setOnAction(e -> handleMenuItemAction(color));
            colorList.getItems().add(menuItem);
        }
    }

    private void handleMenuItemAction(PlayerColor s) {
        System.out.println(s.toString().toLowerCase());
        String url="";
        errButton.setVisible(false);

        if(!selected){
            selected = true;
        }

        switch (s){
            case RED:
                colorList.setStyle("-fx-background-color: red;");
                url = "/polimi/ingsoft/demo/graphics/img/score/redScore.png";
                break;
            case BLUE:
                colorList.setStyle("-fx-background-color: blue;");
                url = "/polimi/ingsoft/demo/graphics/img/score/blueScore.png";
                break;
            case GREEN:
                colorList.setStyle("-fx-background-color: green;");
                url = "/polimi/ingsoft/demo/graphics/img/score/greenScore.png";
                break;
            case YELLOW:
                colorList.setStyle("-fx-background-color: yellow;");
                url = "/polimi/ingsoft/demo/graphics/img/score/yellowScore.png";
        }

        placeColor(url);

        setColorText(s.toString().toLowerCase());
    }

    public void placeColor(String imageUrl){
        colorSelected.setVisible(true);
        Image image = new Image(imageUrl);

        colorSelected.setFitWidth(50);
        colorSelected.setFitHeight(50);

        colorSelected.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 2, 2);");

        colorSelected.setImage(image);
    }

    public void refreshColors(){ // tasto update
        resetColor();
        List<PlayerColor> items = List.of(PlayerColor.RED,PlayerColor.BLUE,PlayerColor.GREEN); // REFRESH LIST -- CALL MODEL
        setGameList(items);
    }
    public void nextPage(ActionEvent actionEvent) throws IOException {

        if(selected){
            ColorWaitingPageController colorWaitingPageController = new ColorWaitingPageController();
            try {
                colorWaitingPageController.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            colorList.setStyle("-fx-background-color: #d34813;");
            setColorText("Select another color");
            errButton.setVisible(true);
        }
    }
    public void start() throws Exception {

        // Load FXML file
        URL resourceUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/ColorPage.fxml");
        if (resourceUrl == null) {
            System.out.println("FXML file not found");
            return;
        }
        //System.out.println("FXML file found");
        Parent root = FXMLLoader.load(resourceUrl);

        // Load CSS file
        URL cssUrl = getClass().getResource("/polimi/ingsoft/demo/graphics/css/ButtonStyle.css");
        if (cssUrl != null) {
            root.getStylesheets().add(cssUrl.toExternalForm());
            //System.out.println("CSS file found");
        } else {
            System.out.println("CSS file not found");
        }
        getStage().getScene().setRoot(root);
    }
}

