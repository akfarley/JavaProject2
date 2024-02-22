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
            PreparedStatement ps1 = conn.prepareStatement("insert into PRODUCT" +
                            " (product_name, seller_id, price)" + "values (?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);


            ps1.setString(1, product.getProductName());
            ps1.setInt(2, product.getSellerId());
            ps1.setDouble(3, product.getPrice());


            ps1.executeUpdate();
            try (ResultSet rs = ps1.getGeneratedKeys()) {

                while (rs.next()) {
                    long product_id = rs.getLong(1);
                    System.out.println("Generated ID:" + product.productId);

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Product> getAllProducts() {
        List<Product> productResults = new ArrayList<>();
        try {
            PreparedStatement ps = conn.prepareStatement("select * from PRODUCT");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long productId = rs.getLong("product_id");
                String productName = rs.getString("product_name");
                double price = rs.getDouble("price");
                int sellerid = rs.getInt("seller_id");
                Product product = new Product(productName, sellerid, price);
                productResults.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productResults;
    }

    public Product getProductById(long productId) {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from Product where product_id = ?");
            ps.setLong(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                //long productId = rs.getInt("product_id");
                String productName = rs.getString("product_name");
                int sellerId = rs.getInt("seller_id");
                double price = rs.getDouble("price");
                Product product = new Product();
                return product;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Product deleteProductById(long productId) {
        try {
            PreparedStatement ps = conn.prepareStatement("delete from Product where product_id = ?");
            ps.setLong(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                String productName = rs.getString("product_name");
                int sellerId = rs.getInt("seller_id");
                double price = rs.getDouble("price");
                Product product = new Product(productName, sellerId, price);
                return product;
            }
                  }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
