package com.engeto.eshop.controller;

import com.engeto.eshop.model.Product;
import com.engeto.eshop.service.EShopException;
import com.engeto.eshop.service.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

/**
 * This class provides methods used by a template engine.
 */
@Controller
public class ProductController {

	private final ProductService productService;
	Logger logger = LoggerFactory.getLogger(ProductController.class);

	public ProductController(ProductService productServiceImpl) {
		this.productService = productServiceImpl;
	}

	@GetMapping("/")
	public String viewHomePage(Model model) throws SQLException {
		logger.info("Viewing home page");
		model.addAttribute("allProducts",
				productService.getAllVisibleSetNotDeleted());
		return "index";
	}

	@GetMapping("/add-new-form")
	public String addNewForm(Model model) {
		logger.info("Viewing add new product form");
		Product product = new Product();
		model.addAttribute("product", product);
		return "new";
	}

	@GetMapping("/get-max-id")
	public String getMaxId(Model model) throws SQLException {
		String maxId = String.valueOf(productService.getMaxId());
		logger.info("Getting max ID = " + maxId);
		model.addAttribute("maxId", maxId);
		return "redirect:/";
	}

	/**
	 * Populates the table of products with the test data.
	 *
	 * @return Home page
	 */
	@GetMapping("/populate-table")
	public String populateTable() throws SQLException, EShopException {
		logger.info("Populating table of products with test data");
		productService.populateTable();
		return "redirect:/";
	}

	@PostMapping("/save-product")
	public String saveProduct(@ModelAttribute("product") Product product)
			throws SQLException {
		productService.save(product);
		logger.info("Saving " + product.toString());
		return "redirect:/";
	}

	@GetMapping("/set-all-deleted")
	public String setAllDeleted() throws SQLException {
		long numberOfSetDeleted = productService.setAllDeleted();
		logger.info("Setting all products to deleted state, "
				+ "their count " + numberOfSetDeleted);
		return "redirect:/";
	}

	@GetMapping("/set-all-out-of-sale-deleted")
	public String setAllOutOfSaleDeleted() throws SQLException {
		long numberOfSetDeleted = productService.setAllOutOfSaleDeleted();
		logger.info("Setting all out of sale products to deleted state, "
				+ "their count " + numberOfSetDeleted);
		return "redirect:/";
	}

	@GetMapping("/set-by-id-deleted/{id}")
	public String setByIdDeleted(@PathVariable(value = "id") long id)
			throws SQLException {
		logger.info("Setting product ID " + id + " to deleted state");
		productService.setByIdDeleted(id);
		return "redirect:/";
	}

	@GetMapping("/switch-is-all-out-of-sale-visible")
	public String switchAllOutOfSaleVisibility()
			throws SQLException, EShopException {
		boolean isVisible = productService.switchIsAllOutOfSaleVisible();
		logger.info("Switching visibility of all out of sale products, "
				+ "currently visible " + isVisible);
		return "redirect:/";
	}

	@PostMapping("/update-product")
	public String updateProduct(@ModelAttribute("product") Product product)
			throws SQLException {
		logger.info("Updating with price " + product.getPrice() + ", "
				+ product.toString());
		productService.update(product);
		return "redirect:/";
	}

	@GetMapping("/update-form/{id}")
	public String updateForm(@PathVariable(value = "id") long id, Model model)
			throws SQLException {
		Product updatedProduct = productService.updateProduct(id, model);
		model.addAttribute("product", updatedProduct);
		logger.info("Viewing update product form for "
				+ updatedProduct.toString());
		return "update";
	}
}
