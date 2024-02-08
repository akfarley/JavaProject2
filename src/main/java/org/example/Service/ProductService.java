package org.example.Service;

import org.example.Exception.ProductException;
import org.example.Main;
import org.example.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    SellerService sellerService;
    List<Product> productList;

    public ProductService(SellerService sellerService) {
        this.sellerService = sellerService;
        productList = new ArrayList<>();
    }

    public List<Product> getProductList() {
        return productList;
    }



    public Product addProduct(Product p) throws ProductException {
       Main.log.info("Attempting to add a product."  + p.productName + "," + p.productSeller + "," + p.price);
        if(p.getProductName() == null || p.getProductSeller() == null){
            throw new ProductException("ProductName and ProductSeller fields must be non-null!");
        } else if (p.price < 0.01) {
            throw new ProductException("Price cannot be less that 0.01" + p.price);

        }
        long id = (long) (Math.random() * Long.MAX_VALUE);
        p.setId(id);
        productList.add(p);
        return p;
    }

    public Product getProductById(long id){
        for(int i = 0; i < productList.size(); i++) {
        Product currentProduct = productList.get(i);
            if (currentProduct.getId() == id) {
                return currentProduct;
            }
        }
        return null;
    }


    }
