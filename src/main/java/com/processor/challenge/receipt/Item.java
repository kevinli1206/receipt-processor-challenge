package com.processor.challenge.receipt;
/**
 * To define a single item in a receipt
 */
public class Item {
    private String shortDescription;
    private String price;

    public Item() {}

    public Item(String shortDescription, String price) {
        this.shortDescription = shortDescription;
        this.price = price;
    }
    
    public String getShortDescription() {
        return shortDescription;
    }

    public String getPrice() {
        return price;
    }
}
