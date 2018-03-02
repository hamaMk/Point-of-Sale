package serializer;


import transactions.Transaction;

import java.io.Serializable;
import java.util.Vector;

public class Bank implements Serializable { 
    
    private Vector<Transaction> bank = new Vector<>();



    public Bank(Vector<Transaction> bank) {
        this.bank = bank;
    }

    public Bank() {

    }

    public Vector<Transaction> getBank() {
        return bank;
    }

    public void setBank(Vector<Transaction> bank) {
        this.bank.addAll(bank);
    }
}
