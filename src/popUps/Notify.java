package popUps;

import DatabaseView.InventoryViewController;
import UpdateDatabase.Controller;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Notify  implements Initializable {

    public Button btnOK;
    public Label lblHeading;
    public Label lblBody;
    private static String body;
    private static String heading;


    public void display(String winTitle, String heading, String body ){
        this.heading = heading;
        this.body = body;

       if(heading.contains("Warning")) Toolkit.getDefaultToolkit().beep();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("notify.fxml"));

        //    loader.setLocation();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        // loader.setController(POSController.class);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
        stage.setTitle(winTitle);
        stage.show();

        return ;
    }

    public void OK(){
        Stage tmp = (Stage)lblBody.getScene().getWindow();
        tmp.close();
        Controller.setConfirm(true);
        InventoryViewController.setConfirm(true);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblHeading.setText(heading);
        lblBody.setText(body);

    }


}
