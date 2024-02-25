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
//GET all sellers
        api.get("seller", context -> {
            List<Seller> sellerList = sellerService.getAllSeller();
            context.json(sellerList);
        });
//POST to seller
        api.post("seller", context -> {
            ObjectMapper om = new ObjectMapper();
            try {
                Seller seller = om.readValue(context.body(), Seller.class);
                sellerService.saveSeller(seller);
                context.status(201);
                Main.log.info("POST to seller was successful!" + seller);
            } catch (SellerException e) {
                context.status(400);
                context.result(e.getMessage());
                Main.log.warn("POST to seller failed");
            }
        });
//GET seller by sellerId
        api.get("seller/{sellerId}", context -> {
            int sellerId = Integer.parseInt(context.pathParam("sellerId"));
            try {
                Seller seller = sellerService.getSellerById(sellerId);
                context.json(seller);
                context.status(201);
                Main.log.info("GET seller by sellerId was successful: " + seller);
            } catch (SellerException e) {
                context.status(404);
                Main.log.warn("GET seller by sellerId failed");
            }
        });
//GET seller by sellerName
        api.get("seller/name/{sellerName}", context -> {
            String sellerName = (context.pathParam("sellerName"));
            try {
                Seller seller = sellerService.getSellerByName(sellerName);
                if (seller == null) {
                    context.status(404);
                    Main.log.warn("GET seller by sellerName failed");
                } else {
                    context.json(seller);
                    context.status(200);
                    Main.log.info("Get seller by sellerName was successful: " + sellerName);
                }
            }catch (SellerException e) {
                context.status(400);
                Main.log.warn("Seller exception was encountered: " + e.getMessage());
            }
        });
//POST to product
        api.post("product", context -> {
            ObjectMapper om = new ObjectMapper();
            Product product = om.readValue(context.body(), Product.class);
            try {
                productService.saveProduct(product);
                context.status(201);
                Main.log.info("POST to product was successful! You added: " + product.productName);
            } catch (ProductException e) {
                context.status(400);
                context.result(e.getMessage());
                Main.log.warn("POST to product failed");
            }
        });
//GET all products
        api.get("product", context -> {
            List<Product> products = productService.getAllProducts();
            context.json(products);
        });
//GET product by productId
        api.get("product/{productId}", context -> {
            int productId = Integer.parseInt(context.pathParam("productId"));
            try {
                Product product = productService.getProductById(productId);
                context.json(product);
                context.status(201);
                Main.log.info("GET product by ID was successful for productID: " + productId);
            } catch (ProductException e) {
                context.status(404);
                Main.log.warn("GET product by ID failed, probably because that the productID does not exist: " + productId);
            }
        });
//DELETE product by productId
        api.delete("product/{productId}", context -> {
            int productId = Integer.parseInt(context.pathParam("productId"));
            Product product = productService.removeProductById(productId);
            if (product == null) {
                context.status(200);
                Main.log.info("DELETE product successful, deleted productID: " + productId);
            } else {
                context.json(product);
                context.status(200);
                Main.log.info("Delete product by ID failed");
            }
        });
//PUT/update product by productId
        api.put("product/{productId}", context -> {
            try {
                int productId = Integer.parseInt(context.pathParam("productId"));
                ObjectMapper om = new ObjectMapper();
                Product updatedProduct = om.readValue(context.body(), Product.class);
                updatedProduct.setProductId(productId);
                Product updated = productService.updateProductById(productId, updatedProduct);
                context.json(updated).status(200);
                Main.log.info("PUT/update to product was successful: " + updated);
            } catch (ProductException e) {
                context.result(e.getMessage());
                context.status(400);
                Main.log.warn("Product exception was encountered");
            }
        });
        return api;
    }
}


