package com.engeto.eshop.data;

import com.engeto.eshop.model.Product;
import com.engeto.eshop.service.EShopException;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Products {
    private List<Product> products = new ArrayList<>();

    public Products() {
    }



    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public static List<Product> importProducts(String file)
            throws EShopException {
        List<Product> listOfProducts = new ArrayList<>();
        File input = new File(file);
        long lineNumber = 0L;
        try (Scanner scanner = new Scanner(input)) {
            while (scanner.hasNextLine()) {
                ++lineNumber;
                String record = scanner.nextLine();
                listOfProducts.add(parseProduct(record));
            }
        } catch (FileNotFoundException e) {
            throw new EShopException("File " + e.getLocalizedMessage());
        } catch (EShopException e ) {
            throw new EShopException(e.getLocalizedMessage()
                    + ", line " + lineNumber);
        }
        return listOfProducts;
    }

    public static Product parseProduct(String data) throws EShopException {
        Scanner scanner = new Scanner(data);
        scanner.useDelimiter(";");
        long partNo;
        String name;
        String description;
        boolean isForSale;
        BigDecimal price;
        try {
            partNo = scanner.nextLong();
            name = scanner.next();
            description = scanner.next();
            isForSale = scanner.nextBoolean();
            price = scanner.nextBigDecimal();
        } catch (InputMismatchException e) {
            throw new EShopException("Parsing error, incorrect number format: "
                    + e.getLocalizedMessage());
        }
        return new Product(partNo, name, description, isForSale, price);
    }
}
