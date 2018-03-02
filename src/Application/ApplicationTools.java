package Application;


import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



//import java.io.IOException;

public class ApplicationTools {



    //Iterms cable = new Iterms();

    //cable.setRetailThrashold() = 5

    public ApplicationTools()throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("pos.fxml"));
    }

    public static void displayPOSWindow(Parent rt) throws Exception{

        Stage stage  = new Stage();
        stage.setTitle("P.O.S");
        Scene scene;
        scene = new Scene(rt,600,400);

        stage.setScene(scene);
        stage.show();

}

}
