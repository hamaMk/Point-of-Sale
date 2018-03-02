package Application;

import PointOfSale.POSController;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import static pngGraphics.Icon.getGraphic;

public class ApplicationToolsController implements Initializable {

    @FXML
    Button btnOpenPointOfSale ;

    @FXML
    Button btnAddNewItems ;

    @FXML
    Button btnViewSales ;

    @FXML
    Button btnViewDatabase;

    @FXML
    private ImageView imgMinimise;

    @FXML
    private ImageView imgClose;


    private static BooleanProperty confirm = new SimpleBooleanProperty();

    public static boolean isConfirm() {
        return confirm.get();
    }

    public static BooleanProperty confirmProperty() {
        return confirm;
    }

    public static void setConfirm(boolean confirm) {
        ApplicationToolsController.confirm.set(confirm);
    }


    BooleanProperty conf = new SimpleBooleanProperty();

    public boolean isConf() {
        return conf.get();
    }

    public BooleanProperty confProperty() {
        return conf;
    }

    public void setConf(boolean conf) {
        this.conf.set(conf);
    }

    public  void openPointOfSale (ActionEvent event)throws Exception{



        Parent root = FXMLLoader.load(POSController.class.getResource("/PointOfSale/pos.fxml"));
        Stage stage  = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Point of Sale");
        Scene scene;
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();


        //disable

        //enable
      //  stage.setOnCloseRequest(e ->  btnOpenPointOfSale.setDisable(false));



    }



    public void viewDatabase()throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/DatabaseView/inventory.fxml"));
        Stage stage  = new Stage();
        stage.setTitle("Stock");
        Scene scene;
        scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add("/DatabaseView/table.css");
        stage.show();

    }

    public void addNewItems(ActionEvent event)throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/UpdateDatabase/updateDatabase.fxml"));
        Stage stage  = new Stage();
        stage.setTitle("Update Stock");
        Scene scene;
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void openVIewSales( )throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/viewsales/viewSales.fxml"));
        Stage stage  = new Stage();
        stage.setTitle("View Sales");
        Scene scene;
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

      /*  setConfirm(false);

        confirm.addListener(e -> {
            btnOpenPointOfSale.setDisable(true);
        });
*/
        btnOpenPointOfSale.setGraphic(getGraphic("resources/cash.png", "Point of Sale"));
        btnOpenPointOfSale.setAlignment(Pos.CENTER);
       // btnOpenPointOfSale.setTextAlignment(TextAlignment.CENTER);

        btnAddNewItems.setGraphic(getGraphic("resources/add.png", "Add new Stock"));
        btnAddNewItems.setAlignment(Pos.CENTER);
        btnAddNewItems.setTextAlignment(TextAlignment.CENTER);

        btnViewDatabase.setGraphic(getGraphic("resources/view.png", "View Stock"));
        btnViewDatabase.setAlignment(Pos.CENTER);
        btnViewDatabase.setTextAlignment(TextAlignment.CENTER);

        btnViewSales.setGraphic(getGraphic("resources/sales.png", "View Sales"));
        btnViewSales.setAlignment(Pos.CENTER);
        btnViewSales.setTextAlignment(TextAlignment.CENTER);


        try {
            imgClose.setImage(new Image(new FileInputStream("resources/close.png")));
            imgMinimise.setImage(new Image(new FileInputStream("resources/minimise.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void imgMinimiseClick() {
        Stage stage = (Stage)btnViewSales.getScene().getWindow();
        stage.setMaximized(false);
    }

    @FXML
    private void imgCloseClick() {
       //
        Platform.exit();
    }


    public static void btnDisable() {

        setConfirm(true);
    }
}
