package org.example.Model;

import java.util.Objects;

public class Product {
    public int productId;
    public String productName;
    public int sellerId;
    public double price = Double.parseDouble(".01");

    public Product() {
    }

    public Product(int productId, String productName, int sellerId, double price) {
        this.productId = productId;
        this.productName = productName;
        this.sellerId = sellerId;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
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

    public int setSellerId(int sellerId) {
        this.sellerId = sellerId;
        return sellerId;
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
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getProductId() == product.getProductId() && getSellerId() == product.getSellerId() && Double.compare(getPrice(), product.getPrice()) == 0 && Objects.equals(getProductName(), product.getProductName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getProductName(), getSellerId(), getPrice());
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
}
