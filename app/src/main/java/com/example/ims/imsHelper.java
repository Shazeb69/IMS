package com.example.ims;




public class imsHelper {
    String Product,Stock,Cost,Expiry,Price;

    public imsHelper() {
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public String getStock() {
        return Stock;
    }

    public void setStock(String stock) {
        Stock = stock;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getExpiry() {
        return Expiry;
    }

    public void setExpiry(String expiry) {
        Expiry = expiry;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public imsHelper(String product, String stock, String cost, String expiry, String price) {
        Product = product;
        Stock = stock;
        Cost = cost;
        Expiry = expiry;
        Price = price;
    }
}
