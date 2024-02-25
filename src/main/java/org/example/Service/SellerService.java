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
        Main.log.info("Retrieving Seller List" + sellerDAO.getAllSeller());
        return sellerDAO.getAllSeller();
    }


    public void saveSeller(Seller seller) throws SellerException {
        if (seller == null) {
            throw new SellerException("Seller cannot be null");
        }
        if (seller.getSellerName().isBlank()) {
            throw new SellerException("Seller name cannot be blank.");
        }
        String sellerName = seller.getSellerName();
        Main.log.info("Attempting to add seller: " + seller);
        if (sellerDAO.getSellerByName(sellerName) != null) {
            Main.log.warn("Seller with name " + sellerName + " already exists");
        } else {
            sellerDAO.insertSeller(seller);
            Main.log.info("Seller was added: " + seller);
        }

    }

    public Seller getSellerById(int sellerId) throws SellerException {
        Seller seller = sellerDAO.getSellerById(sellerId);
        if (seller == null) {
            throw new SellerException("No seller was found with that SellerId: " +sellerId);
        } else {
            return seller;
        }
    }


    public Seller getSellerByName(String sellerName) throws SellerException {
        Main.log.info("Attempting to get seller by name: " + sellerName);
        List<Seller> sellers = sellerDAO.getAllSeller();
        for (Seller currentSeller : sellers) {
            if (currentSeller.getSellerName().equalsIgnoreCase(sellerName)) {
                return currentSeller;
            }
        }
        Main.log.warn("Unable to get seller by name: " +sellerName);
        throw new SellerException("Seller is not found with sellerName = " +sellerName);
    }
}



