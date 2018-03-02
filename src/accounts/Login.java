package accounts;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable{

    @FXML
    private AnchorPane paneLogin;

    @FXML
    private AnchorPane paneNewAccount;



    @FXML
    private Button btnLogin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void optNonAdminClick(){
        paneNewAccount.setVisible(true);
    }

    @FXML
    private void btnCreateAccountClick(){
        paneNewAccount.setVisible(false);
    }

    @FXML
    private void btnLoginClick() throws IOException {
        Stage newStage = (Stage)btnLogin.getScene().getWindow();
        newStage.close();
        Parent root = FXMLLoader.load(getClass().getResource("/Application/applicationTools.fxml"));
       // isConnected.setText("Access Granted");
        //Pos.displayPOSWindow(Rt);
        Stage stage  = new Stage();
        stage.setTitle("Point of Sale System");
        Scene scene;
        scene = new Scene(root);
        scene.getStylesheets().add("/Application/Zeus.css");
        stage.setScene(scene);
        stage.show();
    }


}
