package pkg.cogent.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

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
		int year;
		int month;
		int day;
		try {
			System.out.print("Please enter Product ID: ");
			int pId = Integer.parseInt(user.readLine());

			System.out.print("Please enter Product Name: ");
			String pName = user.readLine();

			System.out.print("Enter Product Category: ");
			String pCat = user.readLine();

			System.out.print("Enter Product Price: ");
			double price = Double.parseDouble(user.readLine());
			System.out.print("\n");

			System.out.println("Enter Manufacture Date (YYYY-MM-DD): ");
			System.out.print("Manufacturing Year: ");
			year = Integer.parseInt(user.readLine());

			System.out.print("Manufacturing Month: ");
			month = Integer.parseInt(user.readLine());

			System.out.print("Manufacturing Day: ");
			day = Integer.parseInt(user.readLine());

			String manufactureDate = new DecimalFormat("0000").format(year) + "-" +
									 new DecimalFormat("00").format(month) + "-" +
									 new DecimalFormat("00").format(day);
			System.out.print("\n");

			System.out.println("Please enter Expiry Date (YYYY/MM/DD): ");
			System.out.print("Expiry Year: ");
			year = Integer.parseInt(user.readLine());

			System.out.print("Expiry Month: ");
			month = Integer.parseInt(user.readLine());

			System.out.print("Expiry Day: ");
			day = Integer.parseInt(user.readLine());
			System.out.print("\n");

			String expiryDate = new DecimalFormat("0000").format(year) + "-" +
								new DecimalFormat("00").format(month) + "-" +
								new DecimalFormat("00").format(day);

			dao.addProduct(pId, pName, pCat, price, manufactureDate, expiryDate);
			
		} catch(IOException ioe) {
			System.err.println("Failed to Add Record: Input Error");
			ioe.printStackTrace();
		} catch (NumberFormatException ime) {
			System.err.println("Failed to Add Record: Input Error");
			ime.printStackTrace();	
		}
	}

	//Display All Product in DB
	public void display() { 
		dao.displayProducts();
	}

	//Updates A Product in DB
	public void update() {
		dao.updateProduct();
	}
	public void fetch() {
		//	dao.read();			// Calls the DAOImpl to read all Customer info. in the Customer Array
	}

	public void modify(String cID) {
		//	dao.update(cID);	// Calls the DAOImpl to update a Customer in the Customer Array
	}

	public void delete(String cID) {
		//	dao.delete(cID);	// Calls the DAOImpl to delete a Customer the Customer Array
	}

	public void findCustomerByID(String cID) {
		//	dao.findCustomerByID(cID);	// Calls the DAOImpl to find a Customer in the Customer Array
	}

	public void findYoungestCustomer() {
		//	dao.findYoungestCustomer();	//	Calls the DAOImple to find the Youngest Customer in Customer Array
	}
}
