package PointOfSale;

import Application.ApplicationToolsController;
import Application.Iterms;
import Application.SqliteConnection;
import TransactionProcessor.ToPdf;
import dataBaseTracker.DBVars;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;
import popUps.BuyItem;
import popUps.Notify;
import serializer.SerHandler;
import transactions.Transaction;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import static TransactionProcessor.ToPdf.getTrueDate;


public class POSController implements Initializable {

    //String [] words = {"charger","cable","challenger","cheese","close","cat","bacon"};

    ArrayList<String> words = new ArrayList();

    BuyItem buyItem =  new BuyItem();
    // words.

    private static DoubleProperty amount = new SimpleDoubleProperty();
    private double change = 0;
    private double amountRecieved ;



    private static int quantity;
    private static double pricee;
    private static String emp;

    public MenuBar posMenubar;



    @FXML
    TextField txtAmtRcvd;

    @FXML
    MenuItem optExport;

    @FXML
    TextField txtAmtDue;

    @FXML
    private TableView tableItemsList;

    @FXML
    private TextField txtItermSearch;

    @FXML
    private TableColumn columnItems;

    @FXML
    private TableView shoppingList;

    @FXML
    private Button btnDone;



    @FXML
    private TableColumn <ShoppingBasket,String>columnShoppingItems;

    @FXML
    private TableColumn <ShoppingBasket,Double>columnItemPrice;

    @FXML
    private TableColumn <ShoppingBasket,Integer>columnItemsQty;

    @FXML
    private TextField qty;

    @FXML
    private TextField price;


    @FXML
    private Label item;

    @FXML
    private Label level;

    @FXML
    private Label stock;

    @FXML
    private Label retail;

    @FXML
    private Label wholesale;

    private SqliteConnection dc;
    private ObservableList<Iterms> data;
    private static ObservableList<ShoppingBasket> basketData;
    Connection conn;

    DataSearch ds = new DataSearch();

    public static ObservableList<Transaction> transactions;

    public TableView<Transaction> transactiontable;
    public TableColumn<Transaction, String> dateCol;
    public TableColumn<Transaction, Double> totalCostCol;
    public TableColumn<Transaction, Double> cashCol;
    public TableColumn<Transaction, Double> changeCol;
    public TableColumn<Transaction, Integer> itemsBoughtCol;

    public ListView<String> listView;

    private ObservableList<Transaction> printables;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        printables = FXCollections.observableArrayList();

        ApplicationToolsController.btnDisable();

        TransactionHandler();
        setAmount(0);
       //mat.bind(Double.valueOf(txtAmtRcvd.textProperty()));

        columnShoppingItems.setCellValueFactory(new PropertyValueFactory<>("item"));
        columnItemsQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        columnItemPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        basketData = FXCollections.observableArrayList();

        shoppingList.setItems(basketData);


        dc = new SqliteConnection();


        conn = dc.connector();
        data = FXCollections.observableArrayList();


        //String []words = new String [size];

        Vector<String> tmp = new Vector<String>();


      //  out.println("well that worked");

        try {
            ResultSet rs = conn.createStatement().executeQuery("SELECT Item FROM iterms");

            while (rs.next()) {
                data.add(new Iterms(rs.getString(1)));
            }

            for (Iterms x : data) {
                tmp.add(x.getItem());
            }

            //words.add("charger");


            TextFields.bindAutoCompletion(txtItermSearch, ds.getWords());


        } catch (SQLException e) {
            System.err.println("error" + e);
        }


        columnItems.setCellValueFactory(new PropertyValueFactory<>("item"));


        tableItemsList.setItems(data);

        TableSelectionModel<Iterms> model = tableItemsList.getSelectionModel();
        ObservableList<Iterms> olist = model.getSelectedItems();
        olist.addListener((ListChangeListener<? super Iterms>) e ->{
           // txtItermSearch.setText(olist.get(0).getItem());
            addToBasket(olist.get(0).getItem());
        });

        TableSelectionModel<ShoppingBasket> baskeModel = shoppingList.getSelectionModel();
        ObservableList<ShoppingBasket> tmpList = baskeModel.getSelectedItems();
        tmpList.addListener((ListChangeListener<? super ShoppingBasket>) e ->{
           setBaskeIterm(tmpList.get(0));
        });



//        txtChange.textProperty().bind();
    }

    public void addToBasket(String key){

        //check if there's still enough of item(key) in the DB
       if(DBVars.getQuantity(key) == 0){

           new Notify().display(key, "Warning !",key + " is no longer available in stock");

       }else{
           try {

               ArrayList<String> tmp = new DataSearch().setLables(key);
               item.setText(tmp.get(0));

               emp = item.getText();
               level.setText(tmp.get(1));
               stock.setText(tmp.get(2));
               retail.setText(tmp.get(3));
               wholesale.setText(tmp.get(4));



           } catch (Exception e) {
               e.printStackTrace();
           }
//        btnOK.setDisable(false);
           buyItem.display(emp);

           //i know right
           txtAmtDue.textProperty().bind(amount.asString());

       }
    }

    public ShoppingBasket getBaskeIterm() {
        return baskeIterm;
    }

    public void setBaskeIterm(ShoppingBasket baskeIterm) {
        this.baskeIterm = baskeIterm;
    }

    private ShoppingBasket baskeIterm;


    public static double getAmount() {
        return amount.get();
    }

    public static DoubleProperty amountProperty() {
        return amount;
    }

    public static void setAmount(double amount) {
        POSController.amount.set(amount);
    }

    @FXML
    public void submitSearch() {

      addToBasket(txtItermSearch.getText());
    }


    public static void Ok( String count, String charge) {

        quantity = Integer.valueOf(count);
        pricee = Double.valueOf(charge);

        basketData.add(new ShoppingBasket(emp, quantity, pricee));
        //btnOK.setDisable(true);

         setAmount( getAmount() + (quantity * pricee));
       // txtAmtDue.setText(String.valueOf(amount));
    }


    public void btnOk(Event event) {



        int quant = Integer.valueOf(qty.getText());
        double prc = Double.valueOf(price.getText());

        basketData.add(new ShoppingBasket(item.getText(), quant, prc));
       // btnOK.setDisable(true);

     //   amount += (quant * prc);
        txtAmtDue.setText(String.valueOf(amount));
    }

    public ArrayList<ShoppingBasket> OBStoALT(ObservableList<ShoppingBasket> buff){
      ArrayList<ShoppingBasket> buffet = new ArrayList<ShoppingBasket>();
        for(ShoppingBasket x: buff){
            buffet.add(x);
        }
        return buffet;
    }


    public void btnDone(){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                amountRecieved = Double.valueOf(txtAmtRcvd.getText());

                if(amountRecieved >= getAmount() ){

                    change = amountRecieved - getAmount();
                  //  txtChange.setText(String.valueOf(change));




                    for (ShoppingBasket x: basketData){

                        // System.out.println("res " + x.getItem() +x.getQty() );
                        int newVal = DBVars.getQuantity( x.getItem()) - x.getQty();
                        //System.out.println("res " + newVal );
                        modifyDB(x.getItem(), newVal);
                    }

                    //////////////transaction tab///////////////


                    addTransaction(OBStoALT(basketData), getAmount(), amountRecieved, change, basketData.size());

                    //clear all fields

                    new Notify().display(getDate(), "Transaction status" , "Change  $" + String.valueOf(change));
                    clear();

                }else {
                    txtAmtRcvd.setStyle("-fx-background-color : red");
                    txtAmtRcvd.setOnMouseClicked(e ->{
                        txtAmtRcvd.setStyle("-fx-background-color : white");
                        btnDone.setDisable(false);
                    });
                    btnDone.setDisable(true);
                }

            }
        });


        Stage s = (Stage)btnDone.getScene().getWindow();
        s.setOnCloseRequest(e -> {
           // System.out.println("closing window");
            /////////save transactions///////////////////
            try {
                SerHandler.saveData(transactions);
                transactions.removeAll(transactions);
            } catch (Exception e1) {
                e1.printStackTrace();

            }
        });

    }

    private void clear() {
        basketData.remove(0, basketData.size());
        txtItermSearch.clear();
      //  txtChange.clear();
        setAmount(0);
        txtAmtRcvd.clear();
        item.setText("");
        level.setText("");
        stock.setText("");
        retail.setText("");
        wholesale.setText("");
    }

    public void btnRemove(){
       if(baskeIterm != null){
           //System.out.println("res " + baskeIterm.getItem() );
           double amt = Double.valueOf(txtAmtDue.getText()) - (baskeIterm.getPrice() * baskeIterm.getQty());
           setAmount(amt);
           try {
               basketData.remove(baskeIterm);
           } catch (Exception e) {
//               e.printStackTrace();

           }
           //if(baskeIterm != null) baskeIterm = null;
       }
       else {
           new Notify().display("warning", "Warning !", "Nothing selected to delete");
       }
    }

    @FXML
    private void rightClickModifyClick(){

    }

    @FXML
    private void rightClickDeleteClick(){
        btnRemove();
    }


    public void modifyDB(String item, int qty){

        String ttmp = "'" + qty + "'";
        String tmp = '"' + item + '"';
       // System.out.println("boom " + ttmp);
        String query = "update iterms set quantity = " + ttmp + " where Item = " + tmp ;

        try {
            conn.createStatement().executeQuery(query);
        } catch (SQLException e) {

        }


    }

///////////////////////TRANSACTION TAB/////////////////////////////////////////


public void TransactionHandler(){

    //get transactions from a local file
    transactions = SerHandler.getTransactions();


    dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        totalCostCol.setCellValueFactory(new PropertyValueFactory<>("totalCost"));
        cashCol.setCellValueFactory(new PropertyValueFactory<>("cash"));
        changeCol.setCellValueFactory(new PropertyValueFactory<>("change"));
        itemsBoughtCol.setCellValueFactory(new PropertyValueFactory<>("itemsBought"));

        //printable contains printable transactions for current day

   // printables = FXCollections.observableArrayList();

    transactions.addListener((ListChangeListener<? super Transaction>) e -> {
            for(Transaction x: transactions){
               if(!printables.contains(x)) if(x.getDate().contains(getTrueDate(getDate())))printables.add(x);
            }
        });

    for(Transaction x: transactions){
        if(!printables.contains(x)) if(x.getDate().contains(getTrueDate(getDate())))printables.add(x);
    }

        transactiontable.setItems(printables);

        TableSelectionModel<Transaction> model = transactiontable.getSelectionModel();
        ObservableList<Transaction> tmp = model.getSelectedItems();

        tmp.addListener((ListChangeListener<? super Transaction>) e -> {

            listView.getItems().removeAll(listView.getItems());
            ArrayList<ShoppingBasket> list = tmp.get(0).getItems();


            for(ShoppingBasket x: list){
               // System.out.println("selected " + x.getItem());
                listView.getItems().add(x.getItem() + "    $" + x.getPrice() + "    x" + x.getQty());
            }



        });



}

  /*  private void clearListView(ObservableList<String> items) {
    items.removeAll(items);
        for (String x : items) {
            items.remove(x);
        }
    }*/

    public void addTransaction(ArrayList<ShoppingBasket> buffet, double totalCost, double cash, double change, int itemsBought){
    transactions.add(new Transaction(buffet, getDate(), totalCost, cash, change, itemsBought));
}

    public String getDate() {
        return new Date().toString();
    }

    public void txtSubmit(){
        if(amountRecieved >= getAmount())btnDone.fire();
    }



    public  void TransPrinter(){


        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\HAMANDISHE\\Documents\\"));
        String fileName =  getTrueDate(new Date().toString()) +".pdf" ;
        fileChooser.setInitialFileName(fileName);
        fileChooser.setTitle("Save to pdf");
        File tmp = fileChooser.showSaveDialog(transactiontable.getScene().getWindow());

        try {
          ToPdf.saveToTextFile(printables, tmp);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void posRemoveItem(){
        btnRemove();
    }

    public void closeClick(){
        Stage stage = (Stage)posMenubar.getScene().getWindow();
        stage.close();
    }

    public void viewStats() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/statistics/demandData.fxml"));
        Stage stage  = new Stage();
        stage.setTitle("Statistics");
        Scene scene;
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void addProductsWindow() throws IOException {
        try {
            new ApplicationToolsController().viewDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void viewSalesWindow(){
        try {
            new ApplicationToolsController().openVIewSales();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

