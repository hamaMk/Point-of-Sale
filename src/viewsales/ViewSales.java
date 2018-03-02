package viewsales;

import PointOfSale.POSController;
import PointOfSale.ShoppingBasket;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import transactions.Transaction;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewSales implements Initializable {

    @FXML
    private TableView<Transaction> transactionTable;


    public TableColumn<Transaction, String> dateCol;
    public TableColumn<Transaction, Double> totalCostCol;
    public TableColumn<Transaction, Double> cashCol;
    public TableColumn<Transaction, Double> changeCol;
    public TableColumn<Transaction, Integer> itemsBoughtCol;

    public ListView<String> leftListView;
    public ListView<String> bottomListView;
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();


    @FXML
    private ImageView imgOne;

    public String getTrueDate(String date){
        String[] tmp = date.split(" ");
        String buffet = new String("");
        for(int i=0; i<3; i++){
           buffet = buffet.concat(tmp[i]);
        }
       // System.out.println("selected " + buffet);
        return buffet;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

      TransactionHandler();
       /*   try {
            SerHandler.loadData();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/


     /*   transactions.removeAll(transactions);
        transactions.addAll(SerHandler.getTransactions());*/

        for(Transaction x: transactions){
            // System.out.println("selected " + x.getItem());
            String tmp = getTrueDate(x.getDate());
          if(! leftListView.getItems().contains(tmp))  leftListView.getItems().add(tmp);
        }

       //  System.out.println("selected " + getTrueDate(transactions.get(0).getDate()));

    }

    public void TransactionHandler(){

       // transactions = POSController.transactions;

        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        totalCostCol.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        cashCol.setCellValueFactory(new PropertyValueFactory<>("cash"));
        changeCol.setCellValueFactory(new PropertyValueFactory<>("change"));
        itemsBoughtCol.setCellValueFactory(new PropertyValueFactory<>("itemsBought"));

        transactionTable.setItems(POSController.transactions);

        TableSelectionModel<Transaction> model = transactionTable.getSelectionModel();
        ObservableList<Transaction> tmp = model.getSelectedItems();

        tmp.addListener((ListChangeListener<? super Transaction>) e -> {

            bottomListView.getItems().removeAll(bottomListView.getItems());
            ArrayList<ShoppingBasket> list = tmp.get(0).getItems();

            for(ShoppingBasket x: list){
                // System.out.println("selected " + x.getItem());
                bottomListView.getItems().add(x.getItem() + "    $" + x.getPrice() + "    x" + x.getQty());
            }

        });

    }

}
