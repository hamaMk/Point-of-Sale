package Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    

    LoginStatus loginStatus = new LoginStatus();


    @FXML
    private Button btnLogin;

    @FXML
    private Label isConnected;

    @FXML
    private Button getBtnLogin;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtPassword;
    private double initX, initY;

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        txtUserName.setText("Tafson");
        txtPassword.setText("Juice");

        if (loginStatus.isDisconneced()){
            isConnected.setText("Connected");
        }

        else{
            isConnected.setText("Not Connected");
        }
    }

    public void Login(ActionEvent event)throws Exception{


        try {

            if (loginStatus.isLogin(txtUserName.getText() , txtPassword.getText())) {

                Stage newStage = (Stage)this.btnLogin.getScene().getWindow();
                newStage.close();
                Parent root = FXMLLoader.load(ApplicationTools.class.getResource("applicationTools.fxml"));
                isConnected.setText("Access Granted");
                //Pos.displayPOSWindow(Rt);
                Stage stage  = new Stage();
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.initModality(Modality.APPLICATION_MODAL);

                dragHandler(stage, root);

                stage.setTitle("Point of Sale System");
                Scene scene;
                scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.show();




            }
            else{
                isConnected.setText("Dennied");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    public void dragHandler(Stage mainStage, Parent mainGroup){
        mainGroup.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                initX = me.getScreenX() - mainStage.getX();
                initY = me.getScreenY() - mainStage.getY();
            }
        });

        mainGroup.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                mainStage.setX(me.getScreenX() - initX);
                mainStage.setY(me.getScreenY() - initY);
            }
        });
    }


}
