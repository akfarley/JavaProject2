package org.example.Model;

import java.text.NumberFormat;
import java.util.Objects;

public class Product {
    public long id;
    public String productName;
    public String productSeller;
    public double price = Double.parseDouble(".01");

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public Product() {
    }

    public Product(String productName, String productSeller, double price) {
        this.productName = productName;
        this.productSeller = productSeller;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getProductSeller() {
        return productSeller;
    }

    public void setProductSeller(String productSeller) {
        this.productSeller = productSeller;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getId() == product.getId() && Double.compare(getPrice(), product.getPrice()) == 0 && Objects.equals(getProductName(), product.getProductName()) && Objects.equals(getProductSeller(), product.getProductSeller());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getProductName(), getProductSeller(), getPrice());
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", productSeller='" + productSeller + '\'' +
                ", price=" + price +
                '}';
    }
}

