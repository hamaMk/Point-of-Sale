package transactions;

import PointOfSale.ShoppingBasket;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

public class ToPdf {

    static double total = 0;

   // static File path  = new File ("Transactions.txt");

    Transaction transaction;

    public  ToPdf(Transaction transaction){

        this.transaction = transaction;

    }

    public static void saveToTextFile(ObservableList<Transaction> transactions)throws Exception{


        String fileName =  getTrueDate(new Date().toString());
        File file = new File(fileName +".txt");
        file.createNewFile();

        PrintWriter printWriter = new PrintWriter(new FileWriter(file,true));


        //transactions.removeAll(transactions);

        for(Transaction x: transactions ){

          printItems(printWriter, x.getItems());
          total += x.getTotalCost();
        }

        printTotal(printWriter);

        printWriter.close();




    }


    public static  String getTrueDate(String Date){

        String [] temp = Date.split(" ");
        String buffet = new String ("");
        for (int i = 0; i<3;i++){
            buffet = buffet.concat(temp[i]);
        }

        return buffet;
    }


    private static void printTotal(PrintWriter printWriter) {
        printWriter.println();
        printWriter.println(total);
    }

    private static void printItems(PrintWriter printWriter, ArrayList<ShoppingBasket> items) {
        for(ShoppingBasket x: items){
            printWriter.println(x.getItem() + "    x" + x.getQty() + "    $" + x.getPrice());
        }

    }


}
