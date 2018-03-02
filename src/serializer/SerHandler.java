package serializer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import transactions.Transaction;

import java.io.*;
import java.util.Vector;

public class SerHandler  {

    private static ObservableList<Transaction> transactions = FXCollections.observableArrayList();
    private static String fileName = "resources/rabit.ser";


    public static ObservableList<Transaction> getTransactions() {

        Bank bank = null;
        File file = new File(fileName);
        if(file.exists()){
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));

                try {
                    bank = (Bank) inputStream.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            transactions.addAll(bank.getBank());
        }

        return transactions;
    }



    public static void saveData(ObservableList<Transaction> data){

        Vector<Transaction> tmp = new Vector<>();
        tmp.addAll(data);

        Vector<Transaction> buff = new Vector<>();

        buff.addAll(getTransactions());

        Bank bank = new Bank();
        bank.setBank(tmp);


        File file = new File(fileName);

        if(!file.exists()) try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));

            outputStream.writeObject(bank);

            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
