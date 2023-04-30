package com.engeto.eshop.service;

import com.engeto.eshop.model.Product;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

@Service
public interface ProductService {

	long deleteAll() throws SQLException;

	List<Product> getAll() throws SQLException;

	List<Product> getAllVisibleSetNotDeleted() throws SQLException;

	Product getById(long id) throws SQLException, EShopException;

	long getMaxId() throws SQLException;

	void populateTable() throws SQLException, EShopException;

	void resetIds() throws SQLException;

	void save(Product product) throws SQLException;

	long setAllDeleted() throws SQLException;

	long setAllOutOfSaleDeleted() throws SQLException;

	void setByIdDeleted(long id) throws SQLException;

	boolean switchIsAllOutOfSaleVisible() throws SQLException, EShopException;

	void update(Product product) throws SQLException;

	Product updatePrice(long id, BigDecimal price) throws SQLException;

	Product updateProduct(long id, Model model) throws SQLException;
}
