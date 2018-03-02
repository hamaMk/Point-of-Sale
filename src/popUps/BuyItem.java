package popUps;

import PointOfSale.POSController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class BuyItem {

    public Button btnAdd;
    public Button btnCancel;
    public TextField txtPrice;
    public TextField txtQty;

    private int qty;
    private double price;

    private BooleanProperty bool = new SimpleBooleanProperty(this, "bool", false);

    public boolean isBool() {
        return bool.get();
    }

    public BooleanProperty boolProperty() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool.set(bool);
    }

    public int getQty() {
        return qty;
    }


    public double getPrice() {
        return price;
    }
    

    public int display(String tmp){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("buyItem.fxml"));
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
        stage.setTitle(tmp);
        stage.show();

        return 1;
    }

    public void Add(){
       qty = Integer.valueOf(txtQty.getText());
       price = Double.valueOf(txtPrice.getText());
        POSController.Ok(txtQty.getText(), txtPrice.getText());
       btnCancel.fire();

    }

    public void Cancel(){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void txtSubmit(){
       if(Integer.valueOf(txtQty.getText()) >= 1)btnAdd.fire();
    }
}
