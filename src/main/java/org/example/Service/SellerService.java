package org.example.Service;

import org.example.Exception.SellerException;
import org.example.Main;
import org.example.Model.Seller;

import java.util.ArrayList;
import java.util.List;


public class SellerService {

    List<Seller> sellerList;

    public SellerService() {
        this.sellerList = new ArrayList<>();
    }

    public List<Seller> getSellerList() {
        Main.log.warn("Retrieving Seller List" + sellerList);
        return sellerList;
    }

    public void addSeller(Seller seller) throws SellerException {
        Main.log.warn("Attempting to add seller: " + seller);
        if (seller.sellerName.isEmpty()) {
            Main.log.warn("Seller name missing: " + seller.getSellerName());
            throw new SellerException("Seller name cannot be blank.");
        } else if (sellerList.contains(seller)) {
            throw new SellerException("Duplicate seller name is not allowed: " + seller.sellerName);
        }
        sellerList.add(seller);
    }

    public Seller getSellerByName(String sellerName) throws SellerException {
        Main.log.info("Attempting to get seller by name: " + sellerName);
        for (Seller currentSeller : sellerList) {
            if (currentSeller.getSellerName().isEmpty()) {
                //Main.log.warn("Unable to get seller by name");
                throw new SellerException("Seller name not found");
            } else if (currentSeller.sellerName.equalsIgnoreCase(sellerName)) {
                return currentSeller;
            }
        }
        throw new SellerException("seller name not found");
    }
}



