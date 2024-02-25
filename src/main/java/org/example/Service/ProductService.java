package org.example.Service;

import org.example.DAO.ProductDAO;
import org.example.Exception.ProductException;
import org.example.Exception.SellerException;
import org.example.Main;
import org.example.Model.Product;

import java.util.List;

public class ProductService {
    ProductDAO productDAO;
    SellerService sellerService;


    public ProductService(ProductDAO productDAO, SellerService sellerService) {
        this.productDAO = productDAO;
        this.sellerService = sellerService;
    }

    public void saveProduct(Product product) throws ProductException, SellerException {
        int id = product.getProductId();
        Main.log.info("Attempting to add a product: productID=" + product.productId + ", productName= " + product.productName + ", sellerID= " + product.sellerId + ", price= " + product.price);

        //checking for duplicate productId
        if (productDAO.getProductById(product.productId) != null) {
            throw new ProductException("Product with id: " + product.productId + " already exists");
        }
        // Check product name is not null
        if (product.getProductName() == null || product.getProductName().isEmpty()) {
            throw new ProductException("Product name cannot be null");
        }
        //checking for price > 0
        if (product.getPrice() <= 0) {
            throw new ProductException("Product price cannot be less than or equal to 0");
        }
        //checking if the sellerId exists
        if (sellerService.getSellerById(product.getSellerId()) == null) {
            throw new SellerException("Seller with id: " + product.getSellerId() + " does not exist");
        }
        //if all above passes, add the product
        productDAO.insertProduct(product);
        Main.log.info("Product added and saved: " + product);
    }


    public List<Product> getAllProducts() {
        Main.log.warn("Retrieving Product List" + productDAO.getAllProducts());
        return productDAO.getAllProducts();
    }

    public Product getProductById(int productId) throws ProductException {
        Main.log.info("Attempting to get a product: " + productId);
        for (Product product : productDAO.getAllProducts()) {
            if (product.productId == productId) {
                Main.log.info("Found product by ProductID: " + productId);
                return product;
            }
        }
        throw new ProductException("Product cannot be found by id: " + productId);
    }

    public Product removeProductById(int productId) throws ProductException {
        Main.log.info("Attempting to delete a product: " + productId);
        if (productDAO.getProductById(productId) != null) {
            productDAO.deleteProductById(productId);
            Main.log.info("Product deleted, id = " + productId);
        } else {
            throw new ProductException("This product id is not found! ProductId: " + productId);
        }
        return null;
    }
    public Product updateProductById(int productId, Product updatedProduct) throws ProductException, SellerException {
        Main.log.info("Attempting to update a product with productId = " + productId);
    // get the existing product
        Product existingProduct = getProductById(productId);

    //check productId for existingProduct
        if (existingProduct == null) {
            throw new ProductException("Product with productId: " + productId + " does not exist");
        }
    //validate the seller
        if (sellerService.getSellerById(updatedProduct.getSellerId()) == null) {
            throw new SellerException("SellerId does not exist: " + updatedProduct.getSellerId());
        }
    // setting new values to update the fields
        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setSellerId(updatedProduct.getSellerId());
    // update in the DAO layer
        productDAO.updateProductById(existingProduct);
        Main.log.info("Product updated: " +existingProduct);
        return existingProduct;
        }


}


