package Application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginWindow extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception{

       // primaryStage.initStyle(StageStyle.UNIFIED);
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        //primaryStage.setTitle("Login");

        Scene scene = new Scene(root);
       // primaryStage.initStyle(StageStyle.TRANSPARENT);
       // scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();






    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void quite() {
        Platform.exit();
    }
}
