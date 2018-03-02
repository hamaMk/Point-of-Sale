package UpdateDatabase;

import Application.Iterms;
import Application.SqliteConnection;
import PointOfSale.DataSearch;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.textfield.TextFields;
import popUps.Notify;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public Button btnAdd;
    public Button btnClear;
    public Button btnRemoveSel;
    public Button btnRefresh;
    public Button btnSave;

    public TextField txtItem;
    public TextField txtQty;
    public TextField txtStockPrc;
    public TextField txtRetailPrc;
    public TextField txtWholesalePrc;

    public TableView<Iterms> table;


    public TableColumn<Iterms, String> itemCol;
    public TableColumn<Iterms, Integer> qtyCol;
    public TableColumn<Iterms, Double> stckpriceCol;
    public TableColumn<Iterms, Double> retailpriceCol;
    public TableColumn<Iterms, Double> wholesaleCol;

    private String item;
    private int level;
    private double stockPrc;
    private double retailPrc;
    private double wholesalePrc;

     ObservableList<Iterms> allData = FXCollections.observableArrayList();
    ObservableList<Iterms> data = FXCollections.observableArrayList();
    Connection connection = new SqliteConnection().connector();
    DataSearch ds = new DataSearch();
    private boolean refillMode = false;

    public static BooleanProperty confirm = new SimpleBooleanProperty();

    public static boolean isConfirm() {
        return confirm.get();
    }

    public static BooleanProperty confirmProperty() {
        return confirm;
    }

    public static void setConfirm(boolean confirm) {
        Controller.confirm.set(confirm);
    }

    public void modifyDB(String item, int qty){
        String ttmp = "'" + qty + "'";
        String tmp = '"' + item + '"';
        // System.out.println("boom " + ttmp);
        String query = "update iterms set quantity = " + ttmp + " where Item = " + tmp ;

        try {
            connection.createStatement().executeQuery(query);
        } catch (SQLException e) {

        }
    }


    public void submitSearch(){

        if(ds.getWords().contains(txtItem.getText())){

         //   System.out.println("yep");
            String item = "'" + txtItem.getText() + "'";
            try {
                ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM iterms where item = " + item);


                    txtItem.setText(rs.getString(2));
                    txtQty.setText(String.valueOf(rs.getInt(3)));
                    txtStockPrc.setText(String.valueOf(rs.getDouble(4)));
                    txtRetailPrc.setText(String.valueOf(rs.getDouble(5)));
                    txtWholesalePrc.setText(String.valueOf(rs.getDouble(6)));

            }
            catch (SQLException e) {
                System.err.println("error" + e);
            }

            refillMode = true;

        }
    }

    public void updateDB(){

           for(Iterms x: allData){

               String a = "'" + x.getItem() + "'";
               String b =  "," + "'" + x.getQuantity() + "'";
               String c =  "," + "'" + x.getStockingPrice() + "'";
               String d =  "," + "'" + x.getRetailThrashold() + "'";
               String e =  "," + "'" + x.getWholesaleThrashold() + "'";
               String query = "insert into iterms(item, quantity, 'stock price', 'retail price', 'wholesale price') values( " + a + b + c + d + e + ")";


               try {
                   connection.createStatement().execute(query);
               } catch (SQLException e1) {
                   e1.printStackTrace();
               }
           }


        //clear
       clearFields();
        clearTable();
        refillMode = false;
        new Notify().display("Add new stock", "Successful", item + "was added to your stock");
    }

    public void clearFields(){
        txtItem.clear();
        txtQty.clear();
        txtStockPrc.clear();
        txtRetailPrc.clear();
        txtWholesalePrc.clear();
    }

    public void clearClick(){
        clearFields();
    }

    public void clearTable() {
        allData.removeAll(allData);
    }

    public void Add(){

        if(refillMode){
            modifyDB(txtItem.getText(), Integer.valueOf(txtQty.getText()));
            new Notify().display(txtItem.getText(), "Update successful","Quantity topped up to " + txtQty.getText());
            clearFields();
            refillMode = false;
        }
       else{
           item = txtItem.getText();
           level = Integer.valueOf(txtQty.getText());
           stockPrc = Double.valueOf(txtStockPrc.getText());
           retailPrc = Double.valueOf(txtRetailPrc.getText());
           wholesalePrc = Double.valueOf(txtWholesalePrc.getText());

          if(!ItemExists(item)) allData.add(new Iterms(item, level, stockPrc, retailPrc, wholesalePrc));
          else new Notify().display(item, "Warning!", "This item already exists,\nclick (add item) to modify.");
       }

    }

    public boolean ItemExists(String key){

        ObservableList<String> tmp = FXCollections.observableArrayList();

        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT Item FROM iterms");

            while (rs.next()) {
                tmp.add(rs.getString(1) );
            }

        } catch (SQLException e) {
            System.err.println("error" + e);
        }
        if(tmp.contains(key))return true;
        return false;
    }

    public void refresh(){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setConfirm(false);
        confirm.addListener(e ->{
            submitSearch();
        });

        itemCol.setCellValueFactory(new PropertyValueFactory<>("item"));
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        stckpriceCol.setCellValueFactory(new PropertyValueFactory<>("stockingPrice"));
        retailpriceCol.setCellValueFactory(new PropertyValueFactory<>("retailThrashold"));
        wholesaleCol.setCellValueFactory(new PropertyValueFactory<>("wholesaleThrashold"));

       // allData.add(new Iterms("yy", 2,2.2,2.2,2.2));
         table.setItems(allData);


        TextFields.bindAutoCompletion(txtItem, ds.getWords());

    }


}
