package org.example.Service;

import org.example.DAO.ProductDAO;
import org.example.DAO.SellerDAO;
import org.example.Exception.ProductException;
import org.example.Exception.SellerException;
import org.example.Model.Product;
import org.example.Model.Seller;
import org.example.Util.ConnectionSingleton;
import org.junit.*;

import java.sql.Connection;
import java.util.List;

public class ProductServiceTest {
    ProductService productService;
    SellerService sellerService;
    ProductDAO productDAO;
    SellerDAO sellerDAO;

    @Test
    public void testGetProductById() {
    }

    @Test
    public void testRemoveProductByID() {
    }

    @Test
    public void testUpdateProduct() {
    }

    @Before
    public void setUp() {
        Connection conn = ConnectionSingleton.getConnection();
        sellerDAO = new SellerDAO(conn);
        productDAO = new ProductDAO(conn);
        sellerService = new SellerService(sellerDAO);
        productService = new ProductService(productDAO);
    }

    @Test
    public void productServiceEmptyAtStart() {
        List<Product> productList = productService.getAllProducts();
        Assert.assertTrue(productList.isEmpty());
    }

    @Test
    public void sellerServiceEmptyAtStart() {
        List<Seller> sellerList = sellerService.getAllSeller();
        Assert.assertTrue(sellerList.isEmpty());
    }

    @Test
    public void addSellerTest() throws SellerException {
        sellerService.saveSeller(new Seller(4, "Bob"));
        sellerService.saveSeller(new Seller(5, "Jack"));
        sellerService.saveSeller(new Seller(6,"Susan"));

        List<Seller> sellerList = sellerService.getAllSeller();
        Assert.assertEquals(3, sellerList.size());
    }

    @Test
    public void addInvalidSellerTest() throws SellerException {
        sellerService.saveSeller(new Seller(1,""));
        List<Seller> sellerList = sellerService.getAllSeller();
        Assert.assertEquals(0, sellerList.size());
    }

//    @Test
//    public void addDupSellerTest() {
//        sellerService.saveSeller(new Seller(1, "Chuck"));
//        sellerService.saveSeller(new Seller(2,"Jack"));
//        sellerService.saveSeller(new Seller(3, "Chuck"));
//
//        List<Seller> sellerList = sellerService.getAllSeller();
//        Assert.assertEquals(2, sellerList.size());
//    }



//    @Test
//    public void addProductTest()  {
//        Seller seller = new Seller();
//        seller.setSellerName("Chuck");
//        sellerService.saveSeller(new Seller(1,"Chuck"));
//
//        Product product = new Product();
//        product.setProductName("widget");
//        product.setSellerName("Chuck");
//        product.setPrice(1.99);
//        try {
//            productService.saveProduct(product);
//        } catch (ProductException e) {
//            e.printStackTrace();
//            Assert.fail("exception should not be thrown");
//        }
//        List<Product> productList = productService.getAllProducts();
//        Assert.assertFalse(productList.isEmpty());
//    }

    @Test
    public void addInvalidPriceProductTest() throws ProductException, SellerException {
        Seller seller = new Seller();
        seller.setSellerName("Chuck");
        sellerService.saveSeller(new Seller(1,"Chuck"));;

        Product product = new Product();
        product.setProductName("widget");
        product.setSellerName("Chuck");
        product.setPrice(0);
        try {
            productService.saveProduct(product);
        } catch (ProductException e) {
            e.printStackTrace();
            Assert.assertTrue(true);
        }
        List<Product> productList = productService.getAllProducts();
        Assert.assertTrue(productList.isEmpty());

    }


    @After
    public void tearDown() throws Exception {

    }
}
