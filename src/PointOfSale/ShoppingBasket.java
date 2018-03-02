package PointOfSale;

import java.io.Serializable;

public class ShoppingBasket implements Serializable {
    private String item;
    private int qty;
    private double price;


    public ShoppingBasket(String item, int qty, double price){

        this.item = item;
        this.qty = qty;
        this.price = price;

    }


    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
