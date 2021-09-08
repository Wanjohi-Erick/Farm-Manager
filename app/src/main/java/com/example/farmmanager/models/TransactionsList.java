package com.example.farmmanager.models;

public class TransactionsList {
    String date, particulars, commodity, quantity, price, transactionID, contact;

    public TransactionsList(String date, String particulars, String commodity, String quantity, String price, String transactionID, String contact) {
        this.date = date;
        this.particulars = particulars;
        this.commodity = commodity;
        this.quantity = quantity;
        this.price = price;
        this.transactionID = transactionID;
        this.contact = contact;
    }

    public String getDate(){
        return date;
    }

    public String getParticulars() {
        return particulars;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public String getContact() {
        return contact;
    }
}
