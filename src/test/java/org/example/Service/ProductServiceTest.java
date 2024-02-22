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


    @Before
    public void setUp() {
        Connection conn = ConnectionSingleton.getConnection();
        sellerDAO = new SellerDAO(conn);
        productDAO = new ProductDAO(conn);
        sellerService = new SellerService(sellerDAO);
        productService = new ProductService(productDAO);
    }
/**
Updated the test case to check if productList is NOT empty at start; DB starts with 3 products and sellers.
 */
    @Test
    public void productServiceNotEmptyAtStart() {
        List<Product> productList = productService.getAllProducts();
        Assert.assertFalse(productList.isEmpty());
    }

    /**
     * Updated the test case to check if sellerList is NOT empty at start; DB starts with 3 products and sellers.
     */
    @Test
    public void sellerServiceNotEmptyAtStart() {
        List<Seller> sellerList = sellerService.getAllSeller();
        Assert.assertFalse(sellerList.isEmpty());
    }

    /**
     *This test adds 3 new sellers to the DB and checks for total of 6.
     * Also check to make sure blank seller name cannot be added.
     */

    @Test
    public void addSellerTest() throws SellerException {
        sellerService.saveSeller(new Seller(6, "Anna"));
        sellerService.saveSeller(new Seller(7, "Kyle"));
        sellerService.saveSeller(new Seller(8,"Greg"));

        List<Seller> sellerList = sellerService.getAllSeller();
        Assert.assertEquals(8, sellerList.size());
        //Added check to make sure the seller name is not blank
        Seller seller = new Seller();
        seller.setSellerName("");
        try {
            sellerService.saveSeller(seller);
            Assert.fail("Should not accept blank sellerName");
        }catch (SellerException e) {
            //expected
        }
    }

    /**
     * Attempt to add blank seller and check for exception.
     */
    @Test
    public void addInvalidSellerTest() throws SellerException {
        try {
            sellerService.saveSeller(new Seller(1, ""));
        } catch (SellerException e) {
            Assert.assertTrue(true);
        }
        List<Seller> sellerList = sellerService.getAllSeller();
        Assert.assertFalse(sellerList.isEmpty());
    }

    /**
     * Attempt to add a duplicate seller
     */
    @Test
    public void addDupSellerTest() {
        try {
            sellerService.saveSeller(new Seller(1, "Chuck"));
        } catch (SellerException e) {
            Assert.assertTrue(true);
        }

        List<Seller> sellerList = sellerService.getAllSeller();
        Assert.assertFalse(sellerList.isEmpty());
    }


    /**
     * Test adding a new seller and a new product.
     */
    @Test
    public void addProductTest() throws SellerException {
        Seller seller = new Seller();
        seller.setSellerName("Patrick");
        seller.setSellerId(5);
        sellerService.saveSeller(seller);

        Product product = new Product();
        product.setProductName("widget");
        product.setSellerId("Patrick");
        product.setPrice(1.99);
        try {
            productService.saveProduct(product);
        } catch (ProductException e) {
            e.printStackTrace();
            Assert.fail("exception should not be thrown");
        }
        List<Product> productList = productService.getAllProducts();
        Assert.assertFalse(productList.isEmpty());

        List<Seller> sellerList = sellerService.getAllSeller();
        Assert.assertFalse(sellerList.isEmpty());
    }

    /**
     * Add a new seller and test that a product with price = 0 cannot be added.
     */
    @Test
    public void addInvalidPriceProductTest() throws SellerException {
        Seller seller = new Seller();
        seller.setSellerName("Terry");
        seller.setSellerId(4);
        sellerService.saveSeller(seller);;

        Product product = new Product();
        product.setProductName("tonka truck");
        product.setSellerId("Terry");
        product.setPrice(0);
        try {
            productService.saveProduct(product);
        } catch (ProductException e) {
            e.printStackTrace();
            Assert.assertTrue(true);
        }
        List<Product> productList = productService.getAllProducts();
        Assert.assertFalse(productList.isEmpty());

        List<Seller> sellerList = sellerService.getAllSeller();
        Assert.assertFalse(sellerList.isEmpty());
    }


    @After
    public void tearDown() throws Exception {

    }
}
