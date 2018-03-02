package DatabaseView;

import Application.Iterms;
import Application.SqliteConnection;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import popUps.Notify;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InventoryViewController  implements Initializable{

    @FXML
    TableView tableInventory;

    @FXML
    TableColumn<Iterms, String> columnItemDescription;

    @FXML
    TableColumn<Iterms, Integer> columnQty;

    @FXML
    TableColumn<Iterms, Double> columnStockPrice;

    @FXML
    TableColumn<Iterms, Double> columnRetailPrice;

    @FXML
    TableColumn<Iterms, Double> columnWholeSalePrice;

    @FXML
    private Button btnLoad;



    private SqliteConnection dc;
    private ObservableList<Iterms> data;
    private Iterms selectedItem;

    public static BooleanProperty confirm = new SimpleBooleanProperty();

    public static boolean isConfirm() {
        return confirm.get();
    }

    public static BooleanProperty confirmProperty() {
        return confirm;
    }

    public static void setConfirm(boolean confirm) {
        InventoryViewController.confirm.set(confirm);
    }

    public void deleteFromDb(String item){
        String key = "'" + item + "'";

        try {
           SqliteConnection.connector().createStatement().execute("DELETE FROM iterms where item = " + key);
        }
        catch (SQLException e) {
            System.err.println("error" + e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setConfirm(false);
        confirm.addListener(e -> {
            if(selectedItem != null){
                deleteFromDb(selectedItem.getItem());
                data.remove(selectedItem);
                selectedItem = null;
            }
        });

        dc = new SqliteConnection();
        BtnLoad();

        TableSelectionModel model = tableInventory.getSelectionModel();
        ObservableList<Iterms> tmp = model.getSelectedItems();
        tmp.addListener((ListChangeListener<? super Iterms>) e ->{
            selectedItem = tmp.get(0);
        });


    }



    @FXML
    private void BtnLoad (){

        Connection  conn = dc.connector();
       /* try {
         //  System.out.println("schema --> " +  conn.getCatalog());
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        data = FXCollections.observableArrayList();
      //  System.out.println("well that worked");

        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM iterms");

            while (rs.next()){
                data.add(new Iterms(rs.getString(2),rs.getInt(3),rs.getDouble(4),rs.getDouble(5),rs.getDouble(6)));
            }
        }
        catch (SQLException e) {
            System.err.println("error" + e);
        }

        columnItemDescription.setCellValueFactory(new PropertyValueFactory<>("item"));
        columnQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        columnStockPrice.setCellValueFactory(new PropertyValueFactory<>("stockingPrice"));
        columnRetailPrice.setCellValueFactory(new PropertyValueFactory<>("retailThrashold"));
        columnWholeSalePrice.setCellValueFactory(new PropertyValueFactory<>("wholesaleThrashold"));

        tableInventory.setItems(data);
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void deleteClick( ) {

        new Notify().display("", "Warning!", "Are you sure you want to delete this item");
    }





}