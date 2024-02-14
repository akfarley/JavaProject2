package org.example.Service;

import org.example.Exception.ProductException;
import org.example.Exception.SellerException;
import org.example.Main;
import org.example.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    public SellerService sellerService;
    List<Product> productList;

    public ProductService(SellerService sellerService) {
        this.sellerService = sellerService;
        this.productList = new ArrayList<>();
    }

    public List<Product> getProductList() {
        Main.log.info("Retrieving product list: " + productList);
        return productList;
    }


    public Product addProduct(Product product) throws ProductException, SellerException {
        Main.log.info("Attempting to add a product: " + product.productName + "," + product.sellerName + "," + product.price);
        if (product.getProductName() == null || product.getSellerName() == null) {
            throw new ProductException("ProductName and SellerName fields cannot be empty");
        }
        if (product.getPrice() <= 0) {
            Main.log.info("Product price cannot be less than 0.01");
            throw new ProductException("Price cannot be less that 0.01" + product.price);
        }
        if (sellerService.getSellerByName(product.getSellerName()) == null || product.getSellerName().isEmpty()) {
            throw new SellerException("Seller name does not exist");
        }
        long id = (long) (Math.random() * Long.MAX_VALUE);
        product.setId(id);
        productList.add(product);
        Main.log.info("Product added: " + product.toString());
        return product;
    }

    public Product getProductById(long id) throws ProductException {
        Main.log.info("Attempting to get a product: " + id);
        for (Product product : productList) {
            if (product.getId() != id) {
                throw new ProductException("Product cannot be found: " + id);
            }
            return product;
        }
        return null;
    }

    public Product removeProductByID(long id) throws ProductException {
        Main.log.info("Attempting to delete a product" + id);
        if (getProductById(id) != null) {
            productList.remove(getProductById(id));
            Main.log.info("Product deleted, id = " + id);
        } else {
            throw new ProductException("This product id is not found: " + id);
        }
        return null;
    }

    public Product updateProduct(long id, Product product) throws ProductException, SellerException {
        String sellerName = product.getSellerName();
        if (product.getProductName() == null || product.getProductName().isEmpty()) {
            Main.log.warn("Product name cannot be empty");
            throw new ProductException("Product name cannot be empty");
        }
        if (product.getPrice() <= 0) {
            Main.log.warn("Price cannot be less than or equal to 0");
            throw new ProductException("Price cannot be less than or equal to 0");
        }
        if (sellerService.getSellerByName(product.getSellerName()) == null || product.getSellerName().isEmpty()) {
            Main.log.warn("Seller name must exist and cannot be blank");
        }
        Product productToUpdate = getProductById(id);
        productToUpdate.setProductName(product.getProductName());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setSellerName(product.getSellerName());

        Main.log.info("Product was updated: " + productToUpdate);
        return productToUpdate;
    }

}
