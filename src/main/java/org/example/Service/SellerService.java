package org.example.Service;

import org.example.DAO.SellerDAO;
import org.example.Exception.SellerException;
import org.example.Main;
import org.example.Model.Seller;

import java.util.List;


public class SellerService {
    SellerDAO sellerDAO;
    public List<Seller> sellerList;

    public SellerService(SellerDAO sellerDAO) {
        this.sellerDAO = sellerDAO;
    }

    public List<Seller> getAllSeller() {
        Main.log.warn("Retrieving Seller List" + sellerDAO.getAllSeller());
        return sellerDAO.getAllSeller();
    }

    public void saveSeller(Seller seller) throws SellerException {
        String sellerName = seller.getSellerName();
        Main.log.info("Attempting to add seller: " + seller);
        if (sellerDAO.getSellerByName(sellerName) == null) {
            Main.log.warn("Seller name is missing");
            throw new SellerException("Seller name cannot be blank");
        }
        sellerDAO.insertSeller(seller);

    }

    public Seller getSellerById(int sellerId) throws SellerException {
        Seller seller = sellerDAO.getSellerById(sellerId);
        if (seller == null) {
            throw new SellerException("No seller was found by such id");
        } else {
            return seller;
        }
    }


    public Seller getSellerByName(String sellerName) throws SellerException {
        Main.log.info("Attempting to get seller by name: " + sellerName);
        for (Seller currentSeller : sellerList) {
            if (currentSeller.getSellerName().isEmpty()) {
                Main.log.warn("Unable to get seller by name");
                throw new SellerException("Seller name not found");
            } else if (currentSeller.sellerName.equalsIgnoreCase(sellerName)) {
                return currentSeller;
            }
        }
        throw new SellerException("seller name not found");
    }
}



