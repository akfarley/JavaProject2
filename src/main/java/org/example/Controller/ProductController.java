package org.example.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import org.example.Exception.ProductException;
import org.example.Exception.SellerException;
import org.example.Main;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Service.ProductService;
import org.example.Service.SellerService;

import java.util.List;

public class ProductController {

    SellerService sellerService;
    ProductService productService;

    public ProductController(SellerService sellerService, ProductService productService) {
        this.sellerService = sellerService;
        this.productService = productService;
    }

    public Javalin getAPI() {
        Javalin api = Javalin.create();

        api.get("health", context -> {
            context.result("server is up");
        });


        /** A GET for both seller and product
         * A POST for both seller and product
         */
        api.get("seller", context -> {
            List<Seller> sellerList = sellerService.getSellerList();
            context.json(sellerList);
        });
        api.get("product", context -> {
            List<Product> productList = productService.getProductList();
            context.json(productList);
        });
        api.post("seller", context -> {
            try {
                ObjectMapper om = new ObjectMapper();
                Seller seller = om.readValue(context.body(), Seller.class);
                sellerService.addSeller(seller);
                context.status(201);
                Main.log.info("POST to seller was successful!");
            } catch (JsonProcessingException e) {
                context.status(400);
                Main.log.warn("JsonProcessingException was encountered");
            } catch (SellerException e) {
                context.result(e.getMessage());
                context.status(400);
                Main.log.warn("Seller exception was encountered");
            }
        });
        api.post("product", context -> {
            try {
                ObjectMapper om = new ObjectMapper();
                Product p = om.readValue(context.body(), Product.class);
                Product newProduct = productService.addProduct(p);
                context.status(201);
                context.json(newProduct);
                Main.log.info("POST to product was successful!  " + newProduct);
            } catch (JsonProcessingException e) {
                context.status(400);
                Main.log.warn("JsonProcessingException was encountered");
            } catch (ProductException e) {
                context.result(e.getMessage());
                context.status(400);
                Main.log.warn("Product exception was encountered");
            }
        });

        api.get("product/{id}", context -> {
            long id = Long.parseLong(context.pathParam("id"));
            Product product = productService.getProductById(id);
            if (product == null) {
                context.status(404);
                Main.log.warn("GET product by ID failed, not found 404");
            } else { //context is status 200 ok by default but added this for clarity.
                context.json(product);
                context.status(200);
                Main.log.info("GET product by ID was successful!  " + product);
            }
        });
        api.get("seller/{sellerName}", context -> {
            String sellerName = (context.pathParam("sellerName"));
            Seller seller = sellerService.getSellerByName(sellerName);
            if (seller == null) {
                context.status(404);
                Main.log.warn("Failed to GET seller, 404");
            } else { //context is status 200 ok by default but added this for clarity.
                context.json(seller);
                context.status(200);
                Main.log.info("GET seller successful: " + sellerName);
            }
        });
        api.put("product/{id}", context -> {
            try {
                ObjectMapper om = new ObjectMapper();
                Product product = om.readValue(context.body(), Product.class);
                Product updatedProduct = productService.updateProduct(product.id, product);
                context.json(updatedProduct).status(201);
                Main.log.info("PUT/update to product was successful: " + product);
            } catch (JsonProcessingException e) {
                context.status(400);
                Main.log.warn("JsonProcessingException was encountered");
            } catch (ProductException e) {
                context.result(e.getMessage());
                context.status(400);
                Main.log.warn("Product exception was encountered");
            }
        });
        api.delete("product/{id}", context -> {
            long id = Long.parseLong(context.pathParam("id"));
            Product product = productService.removeProductByID(id);
            if (product == null) {
                context.status(200);
            } else {
                context.json(product);
                context.status(200);
                Main.log.info("DELETE product successful, 200: " + id);
            }
        });

        return api;
    }

}