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

import static org.example.Util.ConnectionSingleton.resetTestDatabase;

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
        productService = new ProductService(productDAO, sellerService);
        resetTestDatabase();
    }

    /**
     * Updated the test case to check if productList is NOT empty at start; DB starts with 3 products and sellers.
     */
    @Test
    public void productServiceNotEmptyAtStart() {
        List<Product> productList = productService.getAllProducts();
        Assert.assertEquals(3, productList.size());

    }
        /**
         * Updated the test case to check if sellerList is NOT empty at start; DB starts with 3 products and sellers.
         */

    @Test
    public void sellerServiceNotEmptyAtStart () {
            List<Seller> sellerList = sellerService.getAllSeller();
            Assert.assertEquals(3, sellerList.size());
        }

        /**
         *This test adds 3 new sellers to the DB and checks for total of 6.
         * Also check to make sure blank seller name cannot be added.
         */

        @Test
        public void addSellerTest () throws SellerException {
            sellerService.saveSeller(new Seller(6, "Anna"));
            sellerService.saveSeller(new Seller(7, "Kyle"));
            sellerService.saveSeller(new Seller(8, "Greg"));

            List<Seller> sellerList = sellerService.getAllSeller();
            Assert.assertEquals(6, sellerList.size());
            //Added check to make sure the seller name is not blank
            Seller seller = new Seller();
            seller.setSellerName("");
            try {
                sellerService.saveSeller(seller);
                Assert.fail("Should not accept blank sellerName");
            } catch (SellerException e) {
                //expected
            }
        }

        /**
         * Attempt to add blank seller and check for exception.
         */
        @Test
        public void addInvalidSellerTest () throws SellerException {
            try {
                sellerService.saveSeller(new Seller(1, ""));
            } catch (SellerException e) {
                Assert.assertTrue(true);
            }
            List<Seller> sellerList = sellerService.getAllSeller();
            Assert.assertEquals(3, sellerList.size());
        }

        /**
         * Attempt to add a duplicate seller, verify it is not added - check size of seller list.
         */
        @Test
        public void addDupSellerTest () {
            try {
                sellerService.saveSeller(new Seller(1, "Chuck"));
            } catch (SellerException e) {
                Assert.assertTrue(true);
            }

            List<Seller> sellerList = sellerService.getAllSeller();
            Assert.assertEquals(3, sellerList.size());
        }


        /**
         * Test adding a new seller and a new product.
         */
        @Test
        public void addProductTest () throws SellerException, ProductException {
            Seller seller = new Seller();
            seller.setSellerName("Patrick");
            seller.setSellerId(4);
            sellerService.saveSeller(seller);

            Product product = new Product();
            product.setProductName("widget");
            product.setSellerId(4);
            product.setPrice(1.99);
            productService.saveProduct(product);

            List<Product> productList = productService.getAllProducts();
            Assert.assertEquals(4, productList.size());

            List<Seller> sellerList = sellerService.getAllSeller();
            Assert.assertEquals(4, sellerList.size());
        }

        /**
         * Add a new seller and test that a product with price = 0 cannot be added.
         */
        @Test
        public void addInvalidPriceProductTest () throws SellerException, ProductException {
            Seller seller = new Seller();
            seller.setSellerName("Terry");
            seller.setSellerId(4);
            sellerService.saveSeller(seller);

            Product product = new Product();
            product.setProductName("tonka truck");
            product.setSellerId(4);
            product.setPrice(0);
            try {
                productService.saveProduct(product);
            } catch (ProductException e) {
                Assert.assertTrue(true);
            }
            List<Product> productList = productService.getAllProducts();
            Assert.assertFalse(productList.isEmpty());

            List<Seller> sellerList = sellerService.getAllSeller();
            Assert.assertFalse(sellerList.isEmpty());
        }

    /**
     * Test updating a product
     */

    @Test
        public void updateProductTest() {
            try {
                Product product = new Product();
                product.setProductName("craft kit");
                product.setSellerId(1);
                product.setPrice(5.99);
                productService.saveProduct(product);

                List<Product> productList = productService.getAllProducts();
                Product existingProduct = productList.get(0);
                double updatedPrice = 14.99;
                existingProduct.setPrice(updatedPrice);
                productService.updateProductById(existingProduct.getProductId(), existingProduct);

                Product updatedProduct = productService.getProductById(existingProduct.getProductId());
                Assert.assertEquals(updatedPrice, updatedProduct.getPrice(), 0.01);
            } catch (SellerException | ProductException e) {
                Assert.fail("Exception occurred: " + e.getMessage());
            }

        }

    /**
     * Test deleting a Product, then check that it is no longer in the product list (throws an exception due to not found)
     */

    @Test
        public void deleteProductTest()  {
        try {
            Product product = new Product();
            product.setProductName("light bulbs");
            product.setProductId(4);
            product.setSellerId(1);
            product.setPrice(8.99);
            productService.saveProduct(product);

            List<Product> productList = productService.getAllProducts();
            Product existingProduct = productList.get(3);
            productService.removeProductById(existingProduct.getProductId());

            Product deletedProduct = productService.getProductById(existingProduct.getProductId());
            Assert.assertNull(deletedProduct);
        } catch (SellerException | ProductException e) {
            Assert.assertTrue(true );
        }
    }
        @After
        public void tearDown () throws Exception {
        }
    }
