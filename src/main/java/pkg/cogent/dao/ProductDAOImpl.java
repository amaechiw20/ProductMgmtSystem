package pkg.cogent.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import pkg.cogent.exception.DateFieldException;
import pkg.cogent.jdbc.JDBCUtilsProductDB;
import pkg.cogent.model.Product;

public class ProductDAOImpl implements ProductDAO{
	private static String INSERT_PRODUCT_SQL = 	"INSERT IGNORE INTO product " + 
			"(pID, pName, category, price, manufacture_date, expiry_date) VALUES " +
			"(?, ?, ?, ?, ?, ?);";
	private static String QUERY = "SELECT * FROM product;";
	private static String UPDATE_PRODUCTNAME_SQL = 		"UPDATE product SET pName = ? WHERE pID = ?;" ;
	private static String UPDATE_PRODUCTCATEGORY_SQL =	"UPDATE product SET category = ? WHERE pID = ?;" ;
	private static String UPDATE_PRODUCTPRICE_SQL =		"UPDATE product SET price = ? WHERE pID = ?;" ;
	private static String UPDATE_PRODUCTMANU_DATE_SQL = "UPDATE product SET manufacture_date = ? WHERE pID = ?;" ;
	private static String UPDATE_PRODUCTEXPI_DATE_SQL =	"UPDATE product SET expiry_date = ? WHERE pID = ?;" ;
	private BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
	
	//Adds Product to Product DB
	@Override
	public void addProduct() {
		try (Connection connect = JDBCUtilsProductDB.getConnection()){	//Connects to Product DB
			System.out.println("|---------------------|");
			System.out.println("|-----Add Product-----|");
			System.out.println("|---------------------|");
			Product product = productSetUp();
			if(product != null) {
				PreparedStatement pstmt = connect.prepareStatement(INSERT_PRODUCT_SQL); //Inserts Statement to add Product to our Product DB
				//Inserts Value of Product to Product DB
				pstmt.setInt(1, product.getpId()); 				//Column 1: pID
				pstmt.setString(2, product.getpName());			//Column 2: name
				pstmt.setString(3, product.getpCat());			//Column 3: category
				pstmt.setDouble(4, product.getPrice());			//Column 4: price
				pstmt.setDate(5, product.getManufactureDate());	//Column 5: manufacture_date
				pstmt.setDate(6, product.getExpiryDate());		//Column 6: expiry_date
				int count = pstmt.executeUpdate();
				if(count>0){
					System.out.println("Record added into Product DB");
				}
				else {
					System.err.println("Failed to add Record to Product DB: Duplicate ID");
				}
				connect.close();
				pstmt.close();
			} 
		} catch(SQLException sqle) {
			System.err.println("Connection Error");
		} catch (DateFieldException mfe) { //Prints Message from MandatoryField Exceptions
			System.err.println("Failed to add Record to Product DB: Date Error");
		}
	}
	//Displays all Products in DB
	@Override
	public void displayProducts() {
		displayQuery(JDBCUtilsProductDB.getConnection(), QUERY);
	}
	//
	@Override
	public void updateProduct() {
		int count;
		int year;
		int month;
		int day;
		int searchID = 0;
		int choice = 0;
		ResultSet rs = null;
		PreparedStatement ps = null;
		do {
			count = 0;
			System.out.println("|-----------------------------|");
			System.out.println("|-----Update Product Menu-----|");
			System.out.println("|-----------------------------|");
			System.out.println("1-Update Name");
			System.out.println("2-Update Category");
			System.out.println("3-Update Price");
			System.out.println("4-Update Manufacture Date");
			System.out.println("5-Update Expiry Date");
			System.out.println("6-Display Products");
			System.out.println("7-Exit Update Menu");
			System.out.print("Please enter your choice?: ");

			try(Connection connect = JDBCUtilsProductDB.getConnection()){
				choice = Integer.parseInt(user.readLine());
				if(choice < 6 && choice > 0) {
					System.out.print("Update Record at ID: ");
					searchID = Integer.parseInt(user.readLine());
				}
				switch (choice) {
				case 1:															//1-Update Name
					updateName(connect, ps, searchID);
					break;
				case 2:															//2-Update Category
					System.out.println("Update Product Category");
					System.out.print("Enter New Category: ");
					String updatedCategory = user.readLine();
					ps = connect.prepareStatement(UPDATE_PRODUCTCATEGORY_SQL);
					ps.setString(1, updatedCategory);
					ps.setInt(2, searchID);

					count = ps.executeUpdate();
					if(count > 0) {
						System.out.println("Record Updated");
						displayQuery(connect, sql);
					}
					else {
						System.err.println("Error, Record not Updated " + "\n");
					}
					break;
				case 3:															//3-Update Price
					System.out.println("Updated Product Price");
					System.out.print("Enter New Price: ");
					double updatedPrice = Double.parseDouble(user.readLine());
					ps = connect.prepareStatement(UPDATE_PRODUCTPRICE_SQL);
					ps.setDouble(1, updatedPrice);
					ps.setInt(2, searchID);

					count = ps.executeUpdate();
					if(count > 0) {
						System.out.println("Record Updated");
						displayQuery(connect, sql);
					}
					else {
						System.err.println("Error, Record not Updated " + "\n");
					}
					break;
				case 4:															//4-Update Manufacture Date
					System.out.println("Updating Manufacturing Date");
					System.out.print("New Manufacturing Year: ");
					year = Integer.parseInt(user.readLine());
					System.out.print("New Manufacturing Month: ");
					month = Integer.parseInt(user.readLine());
					System.out.print("New Manufacturing Day: ");
					day = Integer.parseInt(user.readLine());
					String manuDate = new DecimalFormat("0000").format(year) + "-" +
							new DecimalFormat("00").format(month) + "-" +
							new DecimalFormat("00").format(day);
					Date updatedManuDate = java.sql.Date.valueOf(LocalDate.parse(manuDate));

					ps = connect.prepareStatement("SELECT expiry_date FROM product WHERE pID = " + searchID + ";");
					rs = ps.executeQuery();
					while (rs.next()) {
						validateDateFields(updatedManuDate, rs.getDate(1));
					}
					ps = connect.prepareStatement(UPDATE_PRODUCTMANU_DATE_SQL);
					ps.setDate(1, updatedManuDate);
					ps.setInt(2, searchID);
					count = ps.executeUpdate();

					if(count > 0) {
						System.out.println("Record Updated");
						displayQuery(connect, sql);
					}
					else {
						System.err.println("Error, Record not Updated " + "\n");
					}
					break;
				case 5:														//5-Update Expiry Date
					System.out.println("Updating Expiry Date");
					System.out.print("New Expiry Year: ");
					year = Integer.parseInt(user.readLine());
					System.out.print("New Expiry Month: ");
					month = Integer.parseInt(user.readLine());
					System.out.print("New Expiry Day: ");
					day = Integer.parseInt(user.readLine());
					String expiDate = new DecimalFormat("0000").format(year) + "-" +
							new DecimalFormat("00").format(month) + "-" +
							new DecimalFormat("00").format(day);
					Date updatedExpiDate = java.sql.Date.valueOf(LocalDate.parse(expiDate)); 

					ps = connect.prepareStatement("SELECT manufacture_date FROM product WHERE pID = " + searchID + ";");
					rs = ps.executeQuery();
					if(rs.next()) {
						validateDateFields(rs.getDate(1), updatedExpiDate);
					}
					ps = connect.prepareStatement(UPDATE_PRODUCTEXPI_DATE_SQL);
					ps.setDate(1, updatedExpiDate);
					ps.setInt(2, searchID);
					count = ps.executeUpdate();

					if(count > 0) {
						System.out.println("Record Updated");
						displayQuery(connect, sql);
					}
					else {
						System.err.println("Error, Record not Updated " + "\n");
					}
					break;
				case 6:	
					displayProducts();
					break;
				default:
					System.out.println("Choose Option from Update Menu.");
				}
			} catch (NumberFormatException ime) {
				System.err.println("Failed to Update: Input Error");
			} catch(SQLException sqle) {
				System.err.println("Connection Error");
			} catch (DateTimeParseException pe) {	//Fails to Add Record if LocalDate Fails to Parse Dates
				System.err.println("Failed to Update Record: Invalid Valid Date Format please Try again");
			} catch (IOException e) {
				System.err.println("Failed to Update: Input Error");
			} catch (DateFieldException mfe) { //Prints Message from DateField Exceptions
				mfe.printStackTrace();
			}

		}while(choice != 7);

	}
	@Override
	public void deleteProduct() {
		// TODO Auto-generated method stub
	}


	@Override
	public void findProduct() {

		int choice = 0;
		do {
			System.out.println("|-----------------------------|");
			System.out.println("|-----Search Product Menu-----|");
			System.out.println("|-----------------------------|");
			System.out.println("1-Search Product By Name");
			System.out.println("2-Search Product By Id");
			System.out.println("3-Search Product By Category");
			System.out.println("4-Search For Cheapest Product in Category");
			System.out.println("5-Search For Expired Products");
			System.out.println("6-Display Products");
			System.out.println("7-Exit Search Menu");
			System.out.print("Please enter your choice?: ");
			try(Connection connect = JDBCUtilsProductDB.getConnection()){
				choice = Integer.parseInt(user.readLine());
				switch(choice) {
				case 1:
					displayQuery(connect, findProductByName());
					break;
				case 2:
					displayQuery(connect, findProductById());
					break;
				case 3:
					displayQuery(connect, findProductInCat());
					break;
				case 4:
					displayQuery(connect, findCheapestProductInCat());
					break;
				case 5:
					displayQuery(connect, findExpiredProducts());
					break;
				case 6:
					displayProducts();
					break;
				default:
					break;

				}
			} catch (IOException e) {
				System.err.println("Failed to Update: Input Error");
			} catch (SQLException sqle) {
				System.err.println("Connection Error");
			} catch (DateFieldException mfe) { //Prints Message from MandatoryField Exceptions
				System.err.println("Failed to add Record to Product DB: Date Error");
			}

		}	while(choice != 7);
	}

	//Checks if the Mandatory Fields in Customer is valid
	private void validateDateFields(Date manufacturing_date, Date expiry) {
		//Fails to add Record if no Manufacturing Date or Expiry Date is not added to Product
		if((manufacturing_date  == null) || (expiry == null )){	
			throw new DateFieldException();
		}
		//Fails to add Record if Manufacturing Date is After Current Date
		else if(manufacturing_date.toLocalDate().isAfter(LocalDate.now())){
			throw new DateFieldException();
		}
		//Fails to add Record if Manufacturing Date is After Expiry Date
		else if(manufacturing_date.toLocalDate().isAfter(expiry.toLocalDate())) {
			throw new DateFieldException();
		}
		//Fails to add Record if Manufacturing Date is more than 50 Years before Current Day
		else if(manufacturing_date.toLocalDate().isBefore(LocalDate.now().minusYears(50))){
			throw new DateFieldException();
		}

	}
	//Displays a A Product or Row of Products based on a Query String
	private void displayQuery(Connection connect, String query) {
		if(query != null) {
			try(Statement stmt = connect.createStatement()){
				ResultSet rs = stmt.executeQuery(query);
				int count = 0;
				while(rs.next()) {
					System.out.println("-----------------------------------");
					System.out.println("Product ID: " + rs.getInt(1));
					System.out.println("Product Name and Category: " + rs.getString(2) + "; " + rs.getString(3));
					System.out.println("Price of Product: " + rs.getDouble(4));
					System.out.println("Manufacturing Date: " + rs.getDate(5));
					System.out.println("Expiry Date: " + rs.getDate(6));
					System.out.println("-----------------------------------");
					count++;
				}

				if(count == 0) {
					System.out.println("Product DB is empty");
				}
				rs.close();
				connect.close();

			} catch(SQLException sqle) {
				System.err.println("Failed to Add Record: Connection Error");
			}
		}
	}
	//Update the name of Product in DB
	private void updateName(Connection connect, PreparedStatement ps, int searchID){
		System.out.println("Updating Product Name");
		System.out.print("Enter New Name: ");
		try {
			String updatedName = user.readLine();
			ps = connect.prepareStatement(UPDATE_PRODUCTNAME_SQL);
			ps.setString(1, updatedName);
			ps.setInt(2, searchID);

			int count = ps.executeUpdate();
			if(count > 0) {
				System.out.println("Record Updated");
				displayQuery(connect, ("SELECT * FROM product WHERE pID = " + searchID + ";"));
			}
			else {
				System.err.println("Error, Record not Updated " + "\n");
			}
		} catch (IOException e) {
			System.err.println("Failed to Update: Input Error");
		} catch (SQLException sqle) {
			System.err.println("Connection Error");
		}
	}
	
	//Returns Query to Search DB for Product by Product Name
	private String findProductByName() {
		System.out.println("Searching Product Name");
		System.out.print("Searching for: ");
		try {
			return ("SELECT * FROM product WHERE pName = " + "\'" + user.readLine() + "\'" + ";");

		} catch (IOException e) {
			System.err.println("Failed to Search: Input Error");
		}
		return null;
	}
	//Returns Query to Search DB for Product by Product ID
	private String findProductById() {
		System.out.println("Searching Product ID");
		System.out.print("Searching for: ");
		try {
			return ("SELECT * FROM product WHERE pID = " + "\'" + Integer.parseInt(user.readLine())  + "\'" + ";");

		} catch (IOException e) {
			System.err.println("Failed to Search: Input Error");
		}
		return null;
	}
	//Returns Query to Search DB for Products in Product Category
	private String findProductInCat() {
		System.out.println("Searching Cheapest Product in Category");
		System.out.print("Searching for Category: ");
		try {
			return ("SELECT * FROM product WHERE category = " + "\'" + user.readLine()  + "\'" + ";");

		} catch (IOException e) {
			System.err.println("Failed to Search: Input Error");
		}
		return null;
	}
	//Returns Query to Search DB for Cheapest Product in  Product Category
	private String findCheapestProductInCat() {
		System.out.println("Searching Cheapest Product in Category");
		System.out.print("Searching for Category: ");
		try {
			return ("SELECT * FROM product WHERE category = " + "\'" + user.readLine()  + "\'" +  " AND (SELECT MIN(price) FROM pieces;");

		} catch (IOException e) {
			System.err.println("Failed to Search: Input Error");
		}
		return null;
	}
	//Returns Query to Search DB for All Expired Products
	private String findExpiredProducts() {
		System.out.println("Searching All Expired Product in Category");
		return ("SELECT * FROM product WHERE expiry_date <= CURDATE();");
	}
	//Sets Up Product Object to be Added into DB
	private Product productSetUp() {
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
			Date manDate = java.sql.Date.valueOf(LocalDate.parse(manufactureDate)); //Creates a ManufactureDate Date Object
			Date expDate = java.sql.Date.valueOf(LocalDate.parse(expiryDate));		//Creates a ManufactureDate Date Object

			validateDateFields(manDate, expDate); //Checks if this is a valid Product Object

			return new Product(pId, pName, pCat, price, manDate, expDate);

		} catch (DateTimeParseException pe) {	//Fails to Add Record if LocalDate Fails to Parse Dates
			System.err.println("Failed to Add Record: Invalid Valid Date Format");
		} catch (NumberFormatException ime) {
			System.err.println("Failed to Add to Record: Input Error");
		} catch(IOException ioe) {
			System.err.println("Failed to Add Record: Input Error");
		}
		return null;

	}
}
