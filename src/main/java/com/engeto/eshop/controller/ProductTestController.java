package com.engeto.eshop.controller;

import com.engeto.eshop.model.Product;
import com.engeto.eshop.service.EShopException;
import com.engeto.eshop.service.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 * This class provides methods that can be tested in REST API platforms like
 * Postman.
 */
@RestController
@RequestMapping("/test")
public class ProductTestController {

    private final ProductService productService;
    Logger logger = LoggerFactory.getLogger(ProductTestController.class);

    public ProductTestController(ProductService productServiceImpl) {
        this.productService = productServiceImpl;
    }

    /**
     * Irreversibly deletes all products.
     *
     * @return Number of deleted products
     */
    @DeleteMapping("/delete-all")
    public long deleteAll_Rest() throws SQLException {
        long numberOfDeleted = productService.deleteAll();
        logger.info("Test, deleting all products, their count "
                + numberOfDeleted);
        return numberOfDeleted;
    }

    @GetMapping("/get-all")
    public List<Product> getAll_Rest() throws SQLException {
        logger.info("Test, getting all products");
        return productService.getAll();
    }

    @GetMapping("/get-by-id/{id}")
    public Product getById_Rest(@PathVariable(value = "id") long id)
            throws SQLException, EShopException {
        Product product = productService.getById(id);
        logger.info("Test, getting " + product.toString());
        return product;
    }

    @GetMapping("/get-max-id")
    public long getMaxId_Rest() throws SQLException {
        long maxId = productService.getMaxId();
        logger.info("Test, getting max ID = " + maxId);
        return maxId;
    }

    /**
     * Restarts IDs. Requires ALTER user permissions.
     */
    @DeleteMapping("/reset-ids")
    public String resetIdsRest() throws SQLException {
        logger.info("Test, resetting IDs in database");
        productService.resetIds();
        return "IDs reset in database";
    }

    /**
     * Sets out of sale products to deleted state instead of physically
     * deleting them.
     *
     * @return Number of products set to deleted state
     */
    @PatchMapping("/set-all-out-of-sale-deleted")
    public long setAllOutOfSaleDeleted_Rest() throws SQLException {
        long numberOfSetDeleted = productService.setAllOutOfSaleDeleted();
        logger.info("Test, setting all out of sale products to deleted state, "
                + "their count " + numberOfSetDeleted);
        return numberOfSetDeleted;
    }

    @PostMapping("/save")
    public Product save_Rest(Product product) throws SQLException {
        productService.save(product);
        logger.info("Test, saving " + product.toString());
        return product;
    }

    @PatchMapping("/update/{id}/{price}")
    public Product update_Rest(@PathVariable(value = "id") long id,
                             @PathVariable(value = "price") BigDecimal price)
            throws SQLException, EShopException {
        Product product = productService.getById(id);
        logger.info("Test, updating " + product.toString()
                + " with price " + price);
        Product updatedProduct = productService.updatePrice(id, price);

        // Update product in database
        productService.update(updatedProduct);

        return updatedProduct;
    }
}
