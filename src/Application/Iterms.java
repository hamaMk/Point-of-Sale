package Application;

import javafx.beans.property.*;

public class Iterms {

    private StringProperty item;
    private int quantity;
    private Double stockingPrice;
    private Double retailThrashold;
    private Double wholesaleThrashold;


    public Iterms(String item, int quantity, Double stockingPrice, Double retailThrashold, Double wholesaleThrashold) {
        this.item= new SimpleStringProperty(item);
        this.quantity = quantity;
        this.stockingPrice = stockingPrice;
        this.retailThrashold = retailThrashold;
        this.wholesaleThrashold = wholesaleThrashold;
    }

    public Iterms(String item) {
        this.item= new SimpleStringProperty(item);
    }


    public String getItem() {
        return item.get();
    }

    public StringProperty itemProperty() {
        return item;
    }

    public void setItem(String item) {
        this.item.set(item);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getStockingPrice() {
        return stockingPrice;
    }

    public void setStockingPrice(double stockingPrice) {
        this.stockingPrice = stockingPrice;
    }

    public double getRetailThrashold() {
        return retailThrashold;
    }

    public void setRetailThrashold(double retailThrashold) {
        this.retailThrashold = retailThrashold;
    }

    public double getWholesaleThrashold() {
        return wholesaleThrashold;
    }

    public void setWholesaleThrashold(double wholesaleThrashold) {
        this.wholesaleThrashold = wholesaleThrashold;
    }

    //property values

    public StringProperty itermDescription (){return item;}
    public int quantity (){return quantity;}
    public Double stokingPrice (){return stockingPrice;}
    public Double retailThrashold (){return retailThrashold;}
    public Double wholesaleThrashold (){return stockingPrice;}



}

