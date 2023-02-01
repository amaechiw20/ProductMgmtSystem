package pkg.cogent.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import pkg.cogent.dao.ProductDAOImpl;
/**
 * 
 * @author: William U. Amaechi
 * @date: 	Jan 18, 2023
 */
public class ProductService {
	ProductDAOImpl dao = new ProductDAOImpl();
	BufferedReader user = new BufferedReader(new InputStreamReader(System.in));

	public ProductService() {
		dao = new ProductDAOImpl();
	}

	public void add() {
		dao.addProduct();
	}

	//Display All Product in DB
	public void display() { 
		dao.displayProducts();
	}

	//Updates A Product in DB
	public void update() {
		dao.updateProduct();
	}
	public void delete() {
		dao.deleteProduct();	// Calls the DAOImpl to delete a Customer the Customer Array
	}

	public void find() {
		dao.findProduct();	// Calls the DAOImpl to find a Customer in the Customer Array
	}

}
