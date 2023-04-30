package com.engeto.eshop.repository;

import com.engeto.eshop.model.Product;
import com.engeto.eshop.data.Products;
import com.engeto.eshop.service.EShopException;
import com.engeto.eshop.settings.Settings;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {

	private final Connection connection = DriverManager.getConnection(
			"jdbc:mysql://localhost:3306/eshop",
			"mysql.eshop",
			"sho281piFy");
	private final Statement statement = connection.createStatement();
	private static final String table = "product";

	public ProductRepository() throws SQLException {
	}

	/**
	 * Irreversibly deletes all products.
	 *
	 * @return Number of deleted products
	 */
	public long deleteAll() throws SQLException {
		return statement.executeUpdate("DELETE FROM " + table+ ";");
	}

	public List<Product> getAll() throws SQLException {
		ResultSet resultSet =
				statement.executeQuery("SELECT * FROM " + table + ";");

		List<Product> products = new ArrayList<>();
		while (resultSet.next()) {
			Product product = new Product();
			product.setId(resultSet.getLong("id"));
			product.setPartNo(resultSet.getLong("part_no"));
			product.setName(resultSet.getString("name"));
			product.setDescription(resultSet.getString("description"));
			product.setIsForSale(resultSet.getBoolean("is_for_sale"));
			product.setPrice(resultSet.getBigDecimal("price"));
			product.setIsVisible(resultSet.getBoolean("is_visible"));
			product.setIsDeleted(resultSet.getBoolean("is_deleted"));
			products.add(product);
		}

		return products;
	}

	public Product getById(Long id) throws SQLException {
		String query = "SELECT * FROM " + table + " WHERE id = " + id + ";";
		ResultSet resultSet = statement.executeQuery(query);
		Product product = new Product();
		if (resultSet.next()) {
			product.setId(resultSet.getLong("id"));
			product.setPartNo(resultSet.getLong("part_no"));
			product.setName(resultSet.getString("name"));
			product.setDescription(
					resultSet.getString("description"));
			product.setIsForSale(resultSet.getBoolean("is_for_sale"));
			product.setPrice(resultSet.getBigDecimal("price"));
			product.setIsVisible(resultSet.getBoolean("is_visible"));
			product.setIsDeleted(resultSet.getBoolean("is_deleted"));
		}
		return product;
	}

	public long getMaxId() throws SQLException {
		String query = "SELECT MAX(ID) FROM " + table + ";";
		ResultSet resultSet = statement.executeQuery(query);
		resultSet.next();
		return resultSet.getLong(1);
	}

	public void populateTable()
			throws SQLException, EShopException {
		// Load testing data
		Products products = new Products();
        products.setProducts(Products.importProducts(Settings.getFile()));

		for (Product product : products.getProducts()) {
			statement.executeUpdate("INSERT INTO " + table
					+ " (part_no, name, description, is_for_sale, price, "
					+ "is_visible, is_deleted) VALUES ("
					+ product.getPartNo() + ", '"
					+ product.getName() + "', '"
					+ product.getDescription() + "', "
					+ product.getIsForSale() + ", "
					+ product.getPrice() + ", "
					+ product.getIsVisible() + ", "
					+ product.getIsDeleted() + ");"
			);
		}
	}

	/**
	 * Restarts IDs. Requires ALTER user permissions.
	 */
	public void resetIds() throws SQLException {
		statement.executeUpdate("ALTER TABLE " + table
				+ " AUTO_INCREMENT = 1;");
	}

	public void save(Product product) throws SQLException {
		String query = "INSERT INTO " + table + " (part_no, name, "
				+ "description, is_for_sale, price, is_visible, is_deleted) "
				+ "VALUES ("
				+ product.getPartNo() + ", '"
				+ product.getName() + "', '"
				+ product.getDescription() + "', "
				+ product.getIsForSale() + ", "
				+ product.getPrice() + ", "
				+ product.getIsVisible() + ", "
				+ product.getIsDeleted() + ");";
		statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
		ResultSet resultSet = statement.getGeneratedKeys();
		resultSet.next();
		long generatedId = resultSet.getLong(1);
		product.setId(generatedId);
	}

	/**
	 * Sets all products to deleted state instead of physically deleting them.
	 *
	 * @return Number of products set to deleted state
	 */
	public long setAllDeleted() throws SQLException {
		return statement.executeUpdate("UPDATE " + table
				+ " SET is_deleted = 1;");
	}

	/**
	 * Sets all out of sale products to deleted state instead of physically
	 * deleting them.
	 *
	 * @return Number of products set to deleted state
	 */
	public long setAllOutOfSaleDeleted() throws SQLException {
		String query = "UPDATE " + table + " SET is_deleted = 1 "
				+ "WHERE is_for_sale = 0;";
		return statement.executeUpdate(query);
	}

	public void setAllOutOfSaleVisible() throws SQLException {
		String query = "UPDATE " + table + " SET is_visible = 1 WHERE "
				+ "is_for_sale = 0;";
		statement.executeUpdate(query);
	}

	/**
	 * Sets a product by ID to deleted state instead of physically deleting it.
	 */
	public void setByIdDeleted(long id) throws SQLException {
		String query = "UPDATE " + table + " SET is_deleted = 1 WHERE id = "
				+ id + ";";
		statement.executeUpdate(query);
	}

	/**
	 * Switches visibility of all out of sale and set not deleted products.
	 *
	 * @return If all out of sale products are visible
	 */
	public boolean switchIsAllOutOfSaleVisible() throws SQLException {
		String query = "UPDATE " + table + " SET is_visible = 1 - is_visible "
				+ "WHERE is_for_sale = 0;";
		statement.executeUpdate(query);
		ResultSet resultSet = statement.executeQuery("SELECT is_visible "
				+ "FROM " + table + " WHERE is_for_sale = 0 LIMIT 1");
		resultSet.next();
		return resultSet.getBoolean(1);
	}

	public void update(Product product) throws SQLException {
		String query = "UPDATE " + table + " SET "
				+ "part_no = " + product.getPartNo() + ", "
				+ "name = '" + product.getName() + "', "
				+ "description = '" + product.getDescription() + "', "
				+ "is_for_sale = " + product.getIsForSale() + ", "
				+ "price = " + product.getPrice() + ", "
				+ "is_visible = " + product.getIsVisible() + ", "
				+ "is_deleted = " + product.getIsDeleted()
				+ " WHERE id = " + product.getId() + ";";
		statement.executeUpdate(query);
	}
}
