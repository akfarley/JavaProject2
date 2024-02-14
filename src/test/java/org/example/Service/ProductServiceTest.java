package org.example.Service;

import org.example.Exception.ProductException;
import org.example.Exception.SellerException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class ProductServiceTest {
    ProductService productService;
    SellerService sellerService;



    @Before
    public void setUp() throws SellerException {
        productService = new ProductService(sellerService);
        sellerService = new SellerService();

        }

    @Test
    public void productServiceEmptyAtStart() {
        List<Product> productList = productService.getProductList();
        Assert.assertTrue(productList.isEmpty());
    }
    @Test
    public void sellerServiceEmptyAtStart() {
        List<Seller> sellerList = sellerService.getSellerList();
        Assert.assertTrue(sellerList.isEmpty());
    }
    @Test
    public void addSellerTest() throws SellerException {
        try {
            sellerService.addSeller(new Seller("Chuck"));
            sellerService.addSeller(new Seller("Jack"));
            sellerService.addSeller(new Seller("Susan"));
        } catch (SellerException e) {
            e.printStackTrace();
            Assert.fail("Seller exception should not be thrown");
        }

        List<Seller> sellerList = sellerService.getSellerList();
        Assert.assertEquals(3, sellerList.size());
    }

    @Test
    public void addInvalidSellerTest() {
        try {
            sellerService.addSeller(new Seller(""));
        } catch (SellerException e) {
            e.printStackTrace();
        }
        List<Seller> sellerList = sellerService.getSellerList();
        Assert.assertEquals(0, sellerList.size());
    }



    @Test
    public void addProductTest() throws ProductException, SellerException {
       Seller seller = new Seller();
       seller.setSellerName("Chuck");
       sellerService.addSeller(seller);

       Product product = new Product();
        product.setProductName("widget");
        product.setSellerName("Chuck");
        product.setPrice(1.99);
        try{
            productService.addProduct(product);
        } catch (ProductException e) {
            e.printStackTrace();
            Assert.fail("exception should not be thrown");
        }


        List<Product> productList = productService.getProductList();
        Assert.assertFalse(productList.isEmpty());



    }




    }
