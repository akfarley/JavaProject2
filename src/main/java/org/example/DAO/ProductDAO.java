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
                            " (product_name, seller_name, price)" + "values (?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);


            ps1.setString(1, product.getProductName());
            ps1.setString(2, product.getSellerName());
            ps1.setDouble(3, product.getPrice());


            ps1.executeUpdate();
            ResultSet rs = ps1.getGeneratedKeys();

            while (rs.next()) {
                System.out.println("Generated ID:" + rs.getInt(1));
                ;
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
                //long productId = rs.;
                String productName = rs.getString("product_name");
                double price = rs.getDouble("price");
                String sellerName = rs.getString("seller_name");
                Product product = new Product(productName, sellerName, price);
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
                String sellerName = rs.getString("seller_name");
                double price = rs.getDouble("price");
                Product product = new Product(productName, sellerName, price);
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
                String sellerName = rs.getString("seller_name");
                double price = rs.getDouble("price");
                Product product = new Product(productName, sellerName, price);
                return product;
            }
                  }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
