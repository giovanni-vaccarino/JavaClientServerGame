package polimi.ingsoft.client.ui.gui;

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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ColorPageController implements Initializable {
    private Stage stage;
    private String color;
    private boolean selected;
    @FXML
    SplitMenuButton colorList;
    @FXML
    Button errButton;
    @FXML
    ImageView colorSelected;

    // Default constructor
    public ColorPageController() {}

    // Constructor with stage parameter
    public ColorPageController(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errButton.setVisible(false);
        List<String> items = List.of("Red", "Green", "Blue"); // CALL MODEL
        resetColor();
        setGameList(items);
    }
    public void resetColor(){
        setColor("Select color");
        selected = false;
        colorList.setStyle("-fx-background-color: white;");
        colorSelected.setVisible(false);
    }
    public void setColor(String s){
        color=String.valueOf(s);
        colorList.setText(color);
    }

    public void setGameList(List<String> colors) {
        colorList.getItems().clear();
        for (String color : colors) {
            MenuItem menuItem = new MenuItem(color);
            menuItem.setOnAction(e -> handleMenuItemAction(color));
            colorList.getItems().add(menuItem);
        }
    }

    private void handleMenuItemAction(String s) {
        String url="";
        errButton.setVisible(false);

        if(!selected){
            selected = true;
        }
        if(s.toLowerCase().equals("red")){
            colorList.setStyle("-fx-background-color: red;");
            url = "/polimi/ingsoft/demo/graphics/img/score/redScore.png";
        } else if (s.toLowerCase().equals("blue")) {
            colorList.setStyle("-fx-background-color: blue;");
            url = "/polimi/ingsoft/demo/graphics/img/score/blueScore.png";
        } else if (s.toLowerCase().equals("green")) {
            colorList.setStyle("-fx-background-color: green;");
            url = "/polimi/ingsoft/demo/graphics/img/score/greenScore.png";
        } else if (s.toLowerCase().equals("yellow")) {
            colorList.setStyle("-fx-background-color: yellow;");
            url = "/polimi/ingsoft/demo/graphics/img/score/yellowScore.png";
        }

        placeColor(url);

        setColor(s);
    }

    public void placeColor(String imageUrl){
        colorSelected.setVisible(true);
        Image image = new Image(imageUrl);

        colorSelected.setFitWidth(50);
        colorSelected.setFitHeight(50);

        colorSelected.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 2, 2);");

        colorSelected.setImage(image);
    }

    public void refreshColors(){
        resetColor();
        List<String> items = List.of("Red", "Green");  // REFRESH LIST
        setGameList(items);
    }
    public void nextPage(ActionEvent actionEvent) throws IOException {

        if(selected){
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            ColorWaitingPageController colorWaitingPageController = new ColorWaitingPageController(stage);
            try {
                colorWaitingPageController.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            colorList.setStyle("-fx-background-color: #d34813;");
            colorList.setText("Select another color");// ADD ERROR WINDOWS
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
        stage.getScene().setRoot(root);
    }
}

