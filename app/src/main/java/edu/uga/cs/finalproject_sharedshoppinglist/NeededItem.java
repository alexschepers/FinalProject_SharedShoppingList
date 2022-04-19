package edu.uga.cs.finalproject_sharedshoppinglist;



/**
 * This class represents a single job lead, including the company name,
 * phone number, URL, and some comments.
 */
public class NeededItem {
    private String itemName;
    private int quantity;

    public NeededItem()
    {
        this.itemName = null;
        this.quantity = -1;
    }

    public NeededItem( String itemName, int quantity ) {
        this.itemName = itemName;
        this.quantity = quantity;

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

    public String toString() {
        return itemName + " " + quantity;
    }
}
