package com.example.ims;


public class Product {

    public String cost;
    public String expiry;
    public String price;
    public String product;
    public String stock;

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public Product() {
    }

    public Product(String cost, String expiry, String price, String product, String stock) {
        this.cost = cost;
        this.expiry = expiry;
        this.price = price;
        this.product = product;
        this.stock = stock;
    }
    @Override
    public String toString(){
        return "Cost: " + cost+
         "\nExpiry: " + expiry+
         "\nPrice: " + price+
         "\nProduct: " + product+
         "\nStock: " + stock;
    }
}
