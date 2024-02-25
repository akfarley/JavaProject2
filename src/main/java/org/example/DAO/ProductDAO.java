package org.example.DAO;


//DAO = Data Access Object

import org.example.Model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    Connection conn;

    public ProductDAO(Connection conn) {
        this.conn = conn;
    }

    public void insertProduct(Product product) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "insert into PRODUCT" +
                    " (product_id, product_name, seller_id, price)" + "values (?, ?, ?, ?)");

            ps.setInt(1, product.getProductId());
            ps.setString(2, product.getProductName());
            ps.setInt(3, product.getSellerId());
            ps.setDouble(4, product.getPrice());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while inserting product " +e.getMessage());
        }
    }


    public List<Product> getAllProducts() {
        List<Product> productResults = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "select * from PRODUCT");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                double price = rs.getDouble("price");
                int sellerId = rs.getInt("seller_id");
                Product product = new Product(productId, productName, sellerId, price);
                productResults.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while getting all products " +e.getMessage());
        }
        return productResults;
    }

    public Product getProductById(int productId) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "select * from Product where product_id = ?");
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                int sellerId = rs.getInt("seller_id");
                double price = rs.getDouble("price");
                Product product = new Product(productId, productName, sellerId, price);
                return product;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while getting product by productID " +e.getMessage());
        }
        return null;
    }

    public Product deleteProductById(int productId) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "delete from Product where product_id = ?");
            ps.setInt(1, productId);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while deleting product by productId " +e.getMessage());
        }
        return null;
    }
    public void updateProductById(Product product){
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "update Product SET product_name = ?, price = ?, seller_id = ? where product_id = ?");
            ps.setString(1, product.getProductName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getSellerId());
            ps.setInt(4, product.getProductId());
            ps.executeUpdate();
            } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while updating product " +e.getMessage());
        }

    }
}
