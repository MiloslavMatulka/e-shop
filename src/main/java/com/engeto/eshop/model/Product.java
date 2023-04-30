package com.engeto.eshop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;

/**
 * Thymeleaf requirements:
 * Field types have to use wrapper classes instead of primitives for
 * placeholder attributes in html. Thymeleaf seems to have a problem
 * with placeholder attributes of input tag for boolean values.
 * Getters and setters have to be named with get and set prefixes only.
 */
@Entity
public class Product {

	/**
	 * Controls visibility of all out of sale products in table.
	 */
	private static boolean isAllOutOfSaleVisible = true;

	private String description;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long id;
	private boolean isDeleted = false;
	private Boolean isForSale = false;
	private boolean isVisible = true;
	private String name;
	private Long partNo;
	private BigDecimal price;

	public Product() {}

	public Product(Long partNo, String name, String description,
				   Boolean isForSale, BigDecimal price) {
		this.partNo = partNo;
		this.name = name;
		this.description = description;
		this.isForSale = isForSale;
		this.price = price;
	}

	//region Getters and setter
	public static boolean getIsAllOutOfSaleVisible() {
		return isAllOutOfSaleVisible;
	}

	public static void setIsAllOutOfSaleVisible(boolean visible) {
		isAllOutOfSaleVisible = visible;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean deleted) {
		isDeleted = deleted;
	}

	public Boolean getIsForSale() {
		return isForSale;
	}

	public void setIsForSale(Boolean isForSale) {
		this.isForSale = isForSale;
	}

	public boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(boolean visible) {
		isVisible = visible;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPartNo() {
		return partNo;
	}

	public void setPartNo(Long partNo) {
		this.partNo = partNo;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	//endregion

	public static void switchIsAllOutOfSaleVisible() {
		isAllOutOfSaleVisible = !isAllOutOfSaleVisible;
	}

	@Override
	public String toString() {
		return "Product{" +
				"id=" + id +
				", partNo=" + partNo +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", isForSale=" + isForSale +
				", price=" + price +
				", isVisible=" + isVisible +
				", isDeleted=" + isDeleted +
				'}';
	}
}
