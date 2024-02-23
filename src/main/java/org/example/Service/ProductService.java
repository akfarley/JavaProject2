package org.example.Service;

import org.example.DAO.ProductDAO;
import org.example.Exception.ProductException;
import org.example.Exception.SellerException;
import org.example.Main;
import org.example.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    ProductDAO productDAO;
    SellerService sellerService;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void saveProduct(Product product) throws ProductException, SellerException {
        if (productDAO.getProductById(product.productId) == null) {
            if (sellerService.getSellerById(product.sellerId) == null) {
                throw new SellerException("Seller does not exist in database");
            }if (product.getProductName() == null || product.getProductName().isEmpty()) {
                throw new ProductException("Product name cannot be null");
            }if (product.getPrice() <= 0) {
                throw new ProductException("Product price cannot be less than or equal to 0");
            }
            productDAO.insertProduct(product);
            Main.log.info("Product added and saved: " +product);
        } else {
            throw new ProductException("product with id " + product.productId + " already exists");
        }
    }

    public List<Product> getAllProducts() {
        Main.log.warn("Retrieving Product List" + productDAO.getAllProducts());
        return productDAO.getAllProducts();
    }

    public Product getProductById(int productId) throws ProductException {
        Main.log.info("Attempting to get a product: " + productId);
        for (Product product : productDAO.getAllProducts()) {
            if (product.productId == productId) {
                return product;
            }
        }
        throw new ProductException("Product cannot be found by id: " + productId);
    }

    public Product removeProductByID(int productId) throws ProductException {
        Main.log.info("Attempting to delete a product" + productId);
        if (productDAO.getProductById(productId) != null) {
            productDAO.deleteProductById(productId);
            Main.log.info("Product deleted, id = " + productId);
        } else {
            throw new ProductException("This product id is not found: " + productId);
        }
        return null;
    }

    public Product updateProductById(int productId) throws ProductException, SellerException {
        Product productToUpdate = productDAO.updateProductById(new Product(), productId);
        Main.log.info("Attempting to update a product" + productId);
        if (sellerService.getSellerById(productToUpdate.sellerId) != null) {
            productToUpdate.setProductName(productToUpdate.getProductName());
            productToUpdate.setPrice(productToUpdate.getPrice());
            productToUpdate.setSellerId(productToUpdate.getSellerId());
            Main.log.info("Product updated: " + productId);
        } else {
            Main.log.warn("Product update failed - check that the seller is in the DB.");
            throw new ProductException("This product update failed, please check seller is in DB: " + productId);
        }
        return null;
    }
}


//    public SellerService sellerService;


//    public ProductService(SellerService sellerService) {
//        this.sellerService = sellerService;
//        this.productList = new ArrayList<>();
//    }

//    public List<Product> getAllProduct() {
//        Main.log.info("Retrieving product list: " + getAllProduct());
//        return productDAO.getAllProducts();
//    }


//    public Product insertProduct(Product product) throws ProductException, SellerException {
//        Main.log.info("Attempting to add a product: " + product.productName + "," + product.sellerName + "," + product.price);
//        if (product.getProductName() == null || product.getSellerName() == null) {
//            throw new ProductException("ProductName and SellerName fields cannot be empty");
//        }
//        if (product.getPrice() <= 0) {
//            Main.log.info("Product price cannot be less than 0.01");
//            throw new ProductException("Price cannot be less that 0.01" + product.price);
//        }
//        if (sellerService.getSellerByName(product.getSellerName()) == null || product.getSellerName().isEmpty()) {
//            throw new SellerException("Seller name does not exist");
//
//        }
//    }
//}


//

//
//    public Product updateProduct(long productId, Product product) throws ProductException, SellerException {
//        String sellerName = product.getSellerName();
//        if (product.getProductName() == null || product.getProductName().isEmpty()) {
//            Main.log.warn("Product name cannot be empty");
//            throw new ProductException("Product name cannot be empty");
//        }
//        if (product.getPrice() <= 0) {
//            Main.log.warn("Price cannot be less than or equal to 0");
//            throw new ProductException("Price cannot be less than or equal to 0");
//        }
//        if (sellerService.getSellerByName(product.getSellerName()) == null || product.getSellerName().isEmpty()) {
//            Main.log.warn("Seller name must exist and cannot be blank");
//        }
//        Product productToUpdate = getProductById(productId);
//        productToUpdate.setProductName(product.getProductName());
//        productToUpdate.setPrice(product.getPrice());
//        productToUpdate.setSellerName(product.getSellerName());
//
//        Main.log.info("Product was updated: " + productToUpdate);
//        return productToUpdate;
//    }

//}
