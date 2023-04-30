package com.engeto.eshop.service;

import com.engeto.eshop.model.Product;
import com.engeto.eshop.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepo;

	public ProductServiceImpl(ProductRepository productRepo) {
		this.productRepo = productRepo;
	}

	@Override
	public long deleteAll() throws SQLException {
		return productRepo.deleteAll();
	}

	@Override
	public List<Product> getAll() throws SQLException {
		return productRepo.getAll();
	}

	@Override
	public List<Product> getAllVisibleSetNotDeleted() throws SQLException {
		return productRepo.getAll()
				.stream()
				.filter(product -> product.getIsVisible()
						&& !product.getIsDeleted())
				.toList();
	}

	@Override
	public Product getById(long id) throws SQLException, EShopException {
		Product product = productRepo.getById(id);
		if (product != null) {
			return product;
		} else {
			throw new EShopException("Product ID " + id + " not found");
		}

	}

	@Override
	public long getMaxId() throws SQLException {
		return productRepo.getMaxId();
	}

	@Override
	public void populateTable() throws SQLException, EShopException {
//		if (!Product.getIsAllOutOfSaleVisible()) {
//			switchIsAllOutOfSaleVisible();
//		}
		productRepo.setAllOutOfSaleVisible();
		productRepo.populateTable();
	}

	@Override
	public void resetIds() throws SQLException {
		productRepo.resetIds();
	}

	@Override
	public void save(Product product) throws SQLException {
//		if (product.getIsForSale() || Product.getIsAllOutOfSaleVisible()) {
//			product.setIsVisible(true);
//		} else {
//			product.setIsVisible(false);
//		}
		productRepo.setAllOutOfSaleVisible();
		productRepo.save(product);
	}

	@Override
	public long setAllDeleted() throws SQLException {
		return productRepo.setAllDeleted();
	}

	@Override
	public long setAllOutOfSaleDeleted() throws SQLException {
		return productRepo.setAllOutOfSaleDeleted();
	}

	@Override
	public void setByIdDeleted(long id) throws SQLException {
		productRepo.setByIdDeleted(id);
	}

	@Override
	public boolean switchIsAllOutOfSaleVisible() throws SQLException {
		if (getMaxId() > 0) {
			Product.switchIsAllOutOfSaleVisible();
			return productRepo.switchIsAllOutOfSaleVisible();
		} else {
			// Visibility change cannot be applied to the empty table
			Product.setIsAllOutOfSaleVisible(true);
			return Product.getIsAllOutOfSaleVisible();
		}
	}

	@Override
	public void update(Product product) throws SQLException {
		productRepo.update(product);
	}

	@Override
	public Product updatePrice(long id, BigDecimal price) throws SQLException {
		Product product = productRepo.getById(id);
		product.setPrice(price);
		return product;
	}

	@Override
	public Product updateProduct(long id, Model model) throws SQLException {
		return productRepo.getById(id);
	}
}
