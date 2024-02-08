package org.example.Service;

import org.example.Exception.SellerException;
import org.example.Main;
import org.example.Model.Seller;

import java.util.ArrayList;
import java.util.List;


public class SellerService {

    List<Seller> sellerList;

    public SellerService(){
        this.sellerList = new ArrayList<>();
    }

    public List<Seller> getSellerList(){
        return sellerList;
    }
    public void addSeller(Seller s) throws SellerException {
        Main.log.warn("Attempting to add seller: " + s);
        if(s.sellerName.isEmpty()){
            Main.log.warn("Seller name missing: " + s.getSellerName());
            throw new SellerException("Seller name cannot be blank.");
        }
        sellerList.add(s);
    }
}
