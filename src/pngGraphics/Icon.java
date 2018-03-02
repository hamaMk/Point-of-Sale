package pngGraphics;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Icon {

    private static String path;

    public static VBox getGraphic(String path, String text){
        Image image = null;
        try {
            image = new Image(new FileInputStream(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView view = new ImageView(image);
        VBox vbox = new VBox(10);
        Label label = new Label(text);
        vbox.getChildren().addAll(view, label);

        return vbox;
    }

}
