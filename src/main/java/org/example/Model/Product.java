package org.example.Model;

import java.util.Objects;

public class Product {
    public long productId;
    public String productName;
    public int sellerId;
    public double price = Double.parseDouble(".01");

    public Product(){
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId && sellerId == product.sellerId && Double.compare(price, product.price) == 0 && Objects.equals(productName, product.productName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, productName, sellerId, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", sellerId=" + sellerId +
                ", price=" + price +
                '}';
    }

    public Product(String productName, int sellerId, double price) {
        this.productId = productId;
        this.productName = productName;
        this.sellerId = sellerId;
        this.price = price;
    }
}

