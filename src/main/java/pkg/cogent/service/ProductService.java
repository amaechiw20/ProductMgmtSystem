package pkg.cogent.service;

import pkg.cogent.dao.ProductDAOImpl;
/**
 * 
 * @author: William U. Amaechi
 * @date: 	Jan 18, 2023
 */
public class ProductService {
	ProductDAOImpl dao = new ProductDAOImpl();

	public ProductService() {
		dao = new ProductDAOImpl();
	}
	//Adds Product to DB
	public void add() {
		dao.addProduct();
	}
	//Display All Products in DB
	public void display() { 
		dao.displayProducts();
	}
	//Updates A Product in DB
	public void update() {
		dao.updateProduct();
	}
	//Deletes A Product in DB
	public void delete() {
		dao.deleteProduct();
	}
	//Searches For Product in DB
	public void find() {
		dao.findProduct();	// Calls the DAOImpl to find a Customer in the Customer Array
	}

}
