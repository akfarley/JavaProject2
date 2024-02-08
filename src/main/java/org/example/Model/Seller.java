package org.example.Model;

import java.util.Objects;

public class Seller {
    public String sellerName;

    public Seller() {

    }

    public Seller(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seller)) return false;
        Seller seller = (Seller) o;
        return Objects.equals(getSellerName(), seller.getSellerName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSellerName());
    }

    @Override
    public String toString() {
        return "Seller{" +
                "sellerName='" + sellerName + '\'' +
                '}';
    }

}