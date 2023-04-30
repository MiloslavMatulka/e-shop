package com.engeto.eshop.controller;

import com.engeto.eshop.model.Product;
import com.engeto.eshop.service.EShopException;
import com.engeto.eshop.service.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * This class provides methods that can be tested in REST API platforms like
 * Postman.
 */
@RestController
public class ProductRestController {

    @Value("${com.engeto.author}")
    private String nameOfAuthor;
    private final ProductService productService;
    Logger logger = LoggerFactory.getLogger(ProductRestController.class);

    public ProductRestController(ProductService productServiceImpl) {
        this.productService = productServiceImpl;
    }

    /**
     * Irreversibly deletes all products.
     *
     * @return Number of deleted products
     */
    @GetMapping("/delete-all-rest")
    public long deleteAll_Rest() throws SQLException {
        long numberOfDeleted = productService.deleteAll();
        logger.info("REST, deleting all products, their count "
                + numberOfDeleted);
        return numberOfDeleted;
    }

    @GetMapping("/get-all-rest")
    public List<Product> getAll_Rest() throws SQLException {
        logger.info("REST, getting all products");
        return productService.getAll();
    }

    @GetMapping("/get-by-id-rest/{id}")
    public Product getById_Rest(@PathVariable(value = "id") long id)
            throws SQLException, EShopException {
        Product product = productService.getById(id);
        logger.info("REST, getting " + product.toString());
        return product;
    }

    @GetMapping("/get-max-id-rest")
    public long getMaxId_Rest() throws SQLException {
        long maxId = productService.getMaxId();
        logger.info("REST, getting max ID = " + maxId);
        return maxId;
    }

    /**
     * Restarts IDs. Requires ALTER user permissions.
     */
    @GetMapping("/reset-ids-rest")
    public String resetIdsRest() throws SQLException {
        logger.info("REST, resetting IDs in database");
        productService.resetIds();
        return "IDs reset in database";
    }

    /**
     * Sets out of sale products to deleted state instead of physically
     * deleting them.
     *
     * @return Number of products set to deleted state
     */
    @GetMapping("/set-all-out-of-sale-deleted-rest")
    public long setAllOutOfSaleDeleted_Rest() throws SQLException {
        long numberOfSetDeleted = productService.setAllOutOfSaleDeleted();
        logger.info("REST, setting all out of sale products to deleted state, "
                + "their count " + numberOfSetDeleted);
        return numberOfSetDeleted;
    }

    @PostMapping("/save-rest")
    public Product save_Rest(Product product) throws SQLException {
        productService.save(product);
        logger.info("REST, saving " + product.toString());
        return product;
    }

    @PostMapping("/update-rest/{id}/{price}")
    public Product update_Rest(@PathVariable(value = "id") long id,
                             @PathVariable(value = "price") BigDecimal price)
            throws SQLException, EShopException {
        Product product = productService.getById(id);
        logger.info("REST, updating " + product.toString()
                + " with price " + price);
        Product updatedProduct = productService.updatePrice(id, price);

        // Update product in database
        productService.update(updatedProduct);

        return updatedProduct;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerException(Exception e) {
        return new ErrorResponse(e.getLocalizedMessage(),
                nameOfAuthor, LocalDateTime.now(), e.getStackTrace());
    }
}
