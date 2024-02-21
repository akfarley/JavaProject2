package org.example;

import io.javalin.Javalin;
import org.example.Controller.ProductController;
import org.example.DAO.ProductDAO;
import org.example.DAO.SellerDAO;
import org.example.Service.ProductService;
import org.example.Service.SellerService;
import org.example.Util.ConnectionSingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class Main {

    public static Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Connection conn = ConnectionSingleton.getConnection();
        SellerDAO sellerDAO = new SellerDAO(conn);
        ProductDAO productDAO = new ProductDAO(conn);

        SellerService sellerService = new SellerService(sellerDAO);
        ProductService productService = new ProductService(productDAO);
        ProductController productController = new ProductController(sellerService, productService);

//        Product product1 = new Product("widget", "Chuck", 1.00);
//        Product product2 = new Product("bobber", "Jack",2.00);
//
//        productDAO.insertProduct(product1);
//        productDAO.insertProduct(product2);
//
//        List<Product> productList = productDAO.getAllProducts();
//        System.out.println(productList);
//
//        SellerService sellerService = new SellerService();
//        ProductService productService = new ProductService(sellerService);
//        ProductController productController = new ProductController(sellerService, productService);

        Javalin api = productController.getAPI();
        api.start(9018);

    }
}