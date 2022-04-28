package edu.uga.cs.finalproject_sharedshoppinglist;

public class PurchasedItem {
    private String itemName;
    private int quantity;
    private double price;

    public PurchasedItem()
    {
        this.itemName = null;
        this.quantity = -1;
        this.price = -1.0;
    }
    public PurchasedItem( String itemName, int quantity, double price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;

    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public String toString() {
        return itemName + " " + quantity + " " + price;
    }

}
