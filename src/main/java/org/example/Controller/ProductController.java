package org.example.Controller;

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

        api.get("seller", context -> {
            List<Seller> sellerList = sellerService.getAllSeller();
            context.json(sellerList);
        });
        api.post("seller", context -> {
            ObjectMapper om = new ObjectMapper();
            try {
                Seller seller = om.readValue(context.body(), Seller.class);
                sellerService.saveSeller(seller);
                context.status(201);
                Main.log.info("POST to seller was successful!");
            } catch (SellerException e) {
                context.status(400);
                context.result(e.getMessage());
                Main.log.warn("POST to seller failed");
            }
        });
        api.get("seller/{sellerId}", context -> {
            int sellerId = Integer.parseInt(context.pathParam("sellerId"));
            try {
                Seller seller = sellerService.getSellerById(sellerId);
                context.json(seller);
                context.status(201);
                Main.log.info("GET seller by ID was successful: "+sellerId);
            } catch (SellerException e) {
                context.status(404);
                Main.log.warn("GET seller by ID failed");
            }
        });
        api.post("product", context -> {
            ObjectMapper om = new ObjectMapper();
            Product product = om.readValue(context.body(), Product.class);
            try {
                productService.saveProduct(product);
                context.status(201);
                Main.log.info("POST to product was successful!");
            } catch (ProductException e) {
                context.status(400);
                context.result(e.getMessage());
                Main.log.warn("POST to product failed");
            }
        });
        api.get("product", context -> {
            List<Product> products = productService.getAllProducts();
            context.json(products);
        });
        api.get("product/{productId}", context -> {
           long productId = Long.parseLong(context.pathParam("productId"));
           try {
               Product product = productService.getProductById(productId);
               context.json(product);
               context.status(201);
               Main.log.info("GET product by ID was successful: "+productId);
           } catch (ProductException e) {
               context.status(404);
               Main.log.warn("GET product by ID failed");
           }
        });
        api.delete("product/{productId}", context -> {
            long productId = Long.parseLong(context.pathParam("productId"));
            Product product = productService.removeProductByID(productId);
            if (product == null) {
                context.status(200);
                Main.log.info("Delete product by ID failed");
            } else {
                context.json(product);
                context.status(200);
                Main.log.info("DELETE product successful, 200: " + productId);
            }
        });
        return api;
    }
}





//        api.post("seller", context -> {
//            try {
//                ObjectMapper om = new ObjectMapper();
//                Seller seller = om.readValue(context.body(), Seller.class);
//                sellerService.insertSeller(seller);
//                context.status(201);
//                Main.log.info("POST to seller was successful!");
//            } catch (JsonProcessingException e) {
//                context.status(400);
//                Main.log.warn("JsonProcessingException was encountered");
//            } catch (SellerException e) {
//                context.result(e.getMessage());
//                context.status(400);
//                Main.log.warn("Seller exception was encountered");
//            }
//        });
//        api.post("product", context -> {
//            try {
//                ObjectMapper om = new ObjectMapper();
//                Product p = om.readValue(context.body(), Product.class);
//                Product newProduct = productService.insertProduct(p);
//                context.status(201);
//                context.json(newProduct);
//                Main.log.info("POST to product was successful!  " + newProduct);
//            } catch (JsonProcessingException e) {
//                context.status(400);
//                Main.log.warn("JsonProcessingException was encountered");
//            } catch (ProductException e) {
//                context.result(e.getMessage());
//                context.status(400);
//                Main.log.warn("Product exception was encountered");
//            }
//        });
//
//        api.get("product/{productId}", context -> {
//            long productId = Long.parseLong(context.pathParam("productId"));
//            Product product = productService.getProductById(productId);
//            if (product == null) {
//                context.status(404);
//                Main.log.warn("GET product by ID failed, not found 404");
//            } else { //context is status 200 ok by default but added this for clarity.
//                context.json(product);
//                context.status(200);
//                Main.log.info("GET product by ID was successful!  " + product);
//            }
//        });
//        api.get("seller/{sellerName}", context -> {
//            String sellerName = (context.pathParam("sellerName"));
//            Seller seller = sellerService.getSellerByName(sellerName);
//            if (seller == null) {
//                context.status(404);
//                Main.log.warn("Failed to GET seller, 404");
//            } else { //context is status 200 ok by default but added this for clarity.
//                context.json(seller);
//                context.status(200);
//                Main.log.info("GET seller successful: " + sellerName);
//            }
//        });
//        api.put("product/{productId}", context -> {
//            try {
//                ObjectMapper om = new ObjectMapper();
//                Product product = om.readValue(context.body(), Product.class);
//                Product updatedProduct = productService.updateProduct(product.productId, product);
//                context.json(updatedProduct).status(201);
//                Main.log.info("PUT/update to product was successful: " + product);
//            } catch (JsonProcessingException e) {
//                context.status(400);
//                Main.log.warn("JsonProcessingException was encountered");
//            } catch (ProductException e) {
//                context.result(e.getMessage());
//                context.status(400);
//                Main.log.warn("Product exception was encountered");
//            }
//        });
//        api.delete("product/{productId}", context -> {
//            long productId = Long.parseLong(context.pathParam("productId"));
//            Product product = productService.removeProductByID(productId);
//            if (product == null) {
//                context.status(200);
//            } else {
//                context.json(product);
//                context.status(200);
//                Main.log.info("DELETE product successful, 200: " + productId);
//            }
//        });
//
//        return api;
//    }