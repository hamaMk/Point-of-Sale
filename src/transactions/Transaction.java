package transactions;

import PointOfSale.ShoppingBasket;

import java.io.Serializable;
import java.util.ArrayList;

public class Transaction implements Serializable {

    private ArrayList<ShoppingBasket> items;
    private String Date;
    private double totalCost;
    private double cash;
    private double change;
    private int itemsBought;

    public Transaction(ArrayList<ShoppingBasket> items, String date, double totalCost, double cash, double change, int itemsBought) {
        this.items = items;
        Date = date;
        this.totalCost = totalCost;
        this.cash = cash;
        this.change = change;
        this.itemsBought = itemsBought;
    }

    public ArrayList<ShoppingBasket> getItems() {
        return items;
    }

    public void setItems(ArrayList<ShoppingBasket> items) {
        this.items = items;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public int getItemsBought() {
        return itemsBought;
    }

    public void setItemsBought(int itemsBought) {
        this.itemsBought = itemsBought;
    }
}
