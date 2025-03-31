package com.processor.challenge.receipt;
import java.util.List;

/**
 * To define the details of a receipt sent through JSON
 */
public class Receipt {
    private String retailer;
    private String purchaseDate;
    private String purchaseTime;
    private List<Item> items;
    private String largeLanguageModel;
    private String total;

    /**
     * Empty constructor
     */
    public Receipt() {}

    /**
     * Constructor with no LLM
     * @param retailer
     * @param purchaseDate
     * @param purchaseTime
     * @param items
     * @param total
     */
    public Receipt(String retailer, String purchaseDate, String purchaseTime, List<Item> items, String total) {
        this.retailer = retailer;
        this.purchaseDate = purchaseDate;
        this.purchaseTime = purchaseTime;
        this.items = items;
        this.largeLanguageModel = "false";
        this.total = total;
    }

    /**
     * Constructor with LLM
     * @param retailer
     * @param purchaseDate
     * @param purchaseTime
     * @param items
     * @param llm
     * @param total
     */
    public Receipt(String retailer, String purchaseDate, String purchaseTime, List<Item> items, String llm, String total) {
        this.retailer = retailer;
        this.purchaseDate = purchaseDate;
        this.purchaseTime = purchaseTime;
        this.items = items;
        this.largeLanguageModel = llm;
        this.total = total;
    }

    /* Getters */
    public String getRetailer() { 
        return retailer;
    }
    public String getPurchaseDate() { 
        return purchaseDate; 
    }
    public String getPurchaseTime() { 
        return purchaseTime; 
    }
    public List<Item> getItems() { 
        return items; 
    }
    public String getLargeLanguageModel() { 
        return largeLanguageModel; 
    }
    public String getTotal() { 
        return total;
    }

}
