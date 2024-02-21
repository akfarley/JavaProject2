package org.example.DAO;

import org.example.Model.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SellerDAO {
    Connection conn;

    public SellerDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Seller> getAllSeller() {
        List<Seller> sellerResults = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from Seller");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int sellerId = resultSet.getInt("seller_id");
                String sellerName = resultSet.getString("seller_name");
                Seller seller = new Seller(sellerId, sellerName);
                sellerResults.add(seller);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sellerResults;
    }

    public void insertSeller(Seller seller) {
        try {
            PreparedStatement ps = conn.prepareStatement("insert into " +
                    "Seller (seller_id, seller_name) values (?,?)");
            ps.setInt(1, seller.getSellerId());
            ps.setString(2, seller.getSellerName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public Seller getSellerById(int sellerId) {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from seller where seller_id = ?");
            ps.setInt(1, sellerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sellerId = rs.getInt("seller_id");
                String sellerName = rs.getString("seller_name");
                Seller seller = new Seller(sellerId, sellerName);
                return seller;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Seller getSellerByName(String sellerName) {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from seller where seller_name = ?");
            ps.setString(1, sellerName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                //sellerId = rs.getInt("seller_id");
                sellerName = rs.getString("seller_name");
                Seller seller = new Seller();
                return seller;
            }else{
                return null;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    return null;
    }

}