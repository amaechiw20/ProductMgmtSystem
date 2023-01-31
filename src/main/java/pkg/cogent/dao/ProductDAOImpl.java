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

import pkg.cogent.exception.MandatoryFieldException;
import pkg.cogent.jdbc.JDBCUtilsProductDB;

public class ProductDAOImpl implements ProductDAO{
	private static String INSERT_PRODUCT_SQL = 	"INSERT INTO product " + 
			"(pID, pName, category, price, manufacture_date, expiry_date) VALUES " +
			"(?, ?, ?, ?, ?, ?);";
	private static String QUERY = "select * from product";

	private static String UPDATE_PRODUCTNAME_SQL = "UPDATE product " +
			"SET name = ? " + 
			"WHERE pID = ?;" ;
	private static String UPDATE_PRODUCTCATEGORY_SQL = "UPDATE product " +
			"SET category = ? " + 
			"WHERE pID = ?;" ;
	private static String UPDATE_PRODUCTPRICE_SQL =	"UPDATE product " +
			"SET price = ? " + 
			"WHERE pID = ?;" ;
	private static String UPDATE_PRODUCTMANU_DATE_SQL = "UPDATE product " +
			"SET manufacture_date = ? " + 
			"WHERE pID = ?;" ;
	private static String UPDATE_PRODUCTEXPI_DATE_SQL =	"UPDATE product " +
			"SET expiry_date = ? " + 
			"WHERE pID = ?;" ;

	private BufferedReader user = new BufferedReader(new InputStreamReader(System.in));

	//Adds Product to Product DB
	@Override
	public void addProduct(int pId, String pName, String pCat, double price, String manufactureDate, String expiryDate) {
		try {
			Date manDate = java.sql.Date.valueOf(LocalDate.parse(manufactureDate)); //Creates a ManufactureDate Date Object
			Date expDate = java.sql.Date.valueOf(LocalDate.parse(expiryDate));		//Creates a ManufactureDate Date Object

			Connection connect = JDBCUtilsProductDB.getConnection();				//Connects to Product DB
			PreparedStatement pstmt = connect.prepareStatement(INSERT_PRODUCT_SQL); //Inserts Statement to A Product to our Product DB;

			validateMandatoryDatePeriod(manDate, expDate); //Checks if this is a valid Product Object

			//Inserts Value of Product to Product DB
			pstmt.setInt(1, pId);
			pstmt.setString(2, pName);
			pstmt.setString(3, pCat);
			pstmt.setDouble(4, pId);
			pstmt.setDate(5, manDate);
			pstmt.setDate(6, expDate);
			int count = pstmt.executeUpdate();
			if(count>0){
				System.out.println("Record added into Product DB");
			}
			else {
				System.out.println("Failed to add Record to Product DB");
			}
			connect.close();
			pstmt.close();

		} catch(SQLException sqle) {
			System.out.println("Failed to Add Record: Connection Error");
		} catch (DateTimeParseException pe) {	//Fails to Add Record if LocalDate Fails to Parse Dates
			System.out.println("Failed to Add Record: Invalid Valid Date Format please Try again");
		} catch (MandatoryFieldException mfe) { //Prints Message from MandatoryField Exceptions
			mfe.printStackTrace();
		} catch (NumberFormatException ime) {
			System.out.println("Failed to Add to Record: Input Error");
			ime.printStackTrace();	// Calls the DAOImpl to create the Customer Array
		}
	}

	//Checks if the Mandatory Fields in Customer is valid
	private void validateMandatoryDatePeriod(Date manu, Date expiry) {
		//Fails to add Record if no Manufacturing Date or Expiry Date is not added to Product
		if((manu  == null) || (expiry == null )){	
			System.err.println("Failed Operaction: Invalid Date");
			throw new MandatoryFieldException();
		}
		//Fails to add Record if Manufacturing Date is After Current Date
		else if(manu.toLocalDate().isAfter(LocalDate.now())){
			System.err.println("Failed Operation: Manufacturing Date Cannot be After The Current Date");
			throw new MandatoryFieldException();
		}
		//Fails to add Record if Manufacturing Date is After Expiry Date
		else if(manu.toLocalDate().isAfter(expiry.toLocalDate())) {
			System.err.println("Failed Operation: Manufacturing Date Cannot be After The Expiry Date");
			throw new MandatoryFieldException();
		}
		//Fails to add Record if Manufacturing Date is more than 5 Years before Current Day
		else if(manu.toLocalDate().isBefore(LocalDate.now().minusYears(5))){
			System.err.println("Failed Operation: Manufacturing Date can not be before " + LocalDate.now().minusYears(5));
			throw new MandatoryFieldException();
		}
		//Fails to add Record if Expiry Date is more than 10 Years Before Current Day
		else if(expiry.toLocalDate().isBefore(LocalDate.now().minusYears(10))){
			System.err.println("Failed Operation: Expiry Date can not be before " + LocalDate.now().minusYears(10));
			throw new MandatoryFieldException();
		}

	}

	//Displays all Products in DB
	@Override
	public void displayProducts() {

		try(Connection connect = JDBCUtilsProductDB.getConnection()){
			System.out.println(QUERY + "\n");
			PreparedStatement ps = connect.prepareStatement(QUERY);
			ResultSet rs = ps.executeQuery();
			int count = 0;
			while(rs.next()) {
				System.out.println("Product ID: " + rs.getInt("pID"));
				System.out.println("Product Name and Category: " + rs.getString("pName") + "; " + rs.getString("category"));
				System.out.println("Price of Product: " + rs.getDouble("price"));
				System.out.println("Manufacturing Date: " + rs.getDate("manufacture_date"));
				System.out.println("Expiry Date: " + rs.getDate("expiry_date"));
				System.out.print("\n");
				count++;
			}

			if(count == 0) {
				System.out.print("Product DB is empty");
			}
			connect.close();
			rs.close();
			ps.close();

		}catch(SQLException sqle) {
			sqle.printStackTrace();
		}

	}

	//Update Product
	@Override
	public void updateProduct() {
		int choice = 0;
		int searchID;
		int count;
		int year;
		int month;
		int day;
		String sql;
		Statement st = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		do {
			System.out.println("1-Update Name");
			System.out.println("2-Update Category");
			System.out.println("3-Update Price");
			System.out.println("4-Update Manufacture Date");
			System.out.println("5-Update Expiry Date");
			System.out.println("6-Exit Update Menu");
			System.out.print("Please enter your choice?: ");
			try(Connection conn = JDBCUtilsProductDB.getConnection()){
				count = 0;
				choice = Integer.parseInt(user.readLine());
				System.out.print("\n");
				switch (choice) {
				case 1:
					System.out.println(UPDATE_PRODUCTNAME_SQL);
					System.out.print("Search for ID: ");
					searchID = Integer.parseInt(user.readLine());

					System.out.print("Updated Name: ");
					String updatedName = user.readLine();
					ps = conn.prepareStatement(UPDATE_PRODUCTNAME_SQL);
					ps.setString(1, updatedName);
					ps.setInt(2, searchID);
					count = ps.executeUpdate();
					if(count > 0) {
						System.out.println("Record Updated");
						System.out.print("\n");
					}
					else {
						System.out.println("Error, Record not Updated");
						System.out.print("\n");
					}
					break;
				case 2:
					System.out.println(UPDATE_PRODUCTCATEGORY_SQL);
					System.out.print("Search for ID: ");
					searchID  = Integer.parseInt(user.readLine());

					System.out.print("Updated Category: ");
					String updatedCategory = user.readLine();
					ps = conn.prepareStatement(UPDATE_PRODUCTCATEGORY_SQL);
					ps.setString(1, updatedCategory);
					ps.setInt(2, searchID);
					count = ps.executeUpdate();
					if(count > 0) {
						System.out.println("Record Updated");
						System.out.print("\n");
					}
					else {
						System.out.println("Error, Record not Updated");
						System.out.print("\n");
					}
					break;
				case 3:
					System.out.println(UPDATE_PRODUCTPRICE_SQL);
					System.out.print("Search for ID: ");
					searchID  = Integer.parseInt(user.readLine());

					System.out.print("Updated Price: ");
					double updatedPrice = Double.parseDouble(user.readLine());
					ps = conn.prepareStatement(UPDATE_PRODUCTPRICE_SQL);
					ps.setDouble(1, updatedPrice);
					ps.setInt(2, searchID);
					count = ps.executeUpdate();
					if(count > 0) {
						System.out.println("Record Updated");
						System.out.print("\n");
					}
					else {
						System.out.println("Error, Record not Updated");
						System.out.print("\n");
					}
					break;
				case 4:
					System.out.println(UPDATE_PRODUCTMANU_DATE_SQL);
					System.out.print("Search for ID: ");
					searchID  = Integer.parseInt(user.readLine());
					
					System.out.println("Updated Manufacturing Date: ");
					System.out.print("Manufacturing Year: ");
					year = Integer.parseInt(user.readLine());

					System.out.print("Manufacturing Month: ");
					month = Integer.parseInt(user.readLine());

					System.out.print("Manufacturing Day: ");
					day = Integer.parseInt(user.readLine());

					String manuDate = new DecimalFormat("0000").format(year) + "-" +
											 new DecimalFormat("00").format(month) + "-" +
											 new DecimalFormat("00").format(day);
					Date updatedManuDate = java.sql.Date.valueOf(LocalDate.parse(manuDate)); //Creates a ManufactureDate Date Object
					
					st = conn.createStatement();
					sql = ("SELECT * FROM product WHERE pID = " + searchID + ";");
					rs = st.executeQuery(sql);
					validateMandatoryDatePeriod(updatedManuDate, rs.getDate(6));
					ps = conn.prepareStatement(UPDATE_PRODUCTMANU_DATE_SQL);
					ps.setDate(1, updatedManuDate);
					ps.setInt(2, searchID);
					count = ps.executeUpdate();
					if(count > 0) {
						System.out.println("Record Updated");
						System.out.print("\n");
					}
					else {
						System.out.println("Error, Record not Updated");
						System.out.print("\n");
					}
					System.out.print("\n");
					break;
				case 5:
					System.out.println(UPDATE_PRODUCTEXPI_DATE_SQL);
					System.out.print("Search for ID: ");
					searchID  = Integer.parseInt(user.readLine());
					
					System.out.print("Expiry Year: ");
					year = Integer.parseInt(user.readLine());

					System.out.print("Expiry Month: ");
					month = Integer.parseInt(user.readLine());

					System.out.print("Expiry Day: ");
					day = Integer.parseInt(user.readLine());
					System.out.print("\n");

					String expiDate = new DecimalFormat("0000").format(year) + "-" +
											 new DecimalFormat("00").format(month) + "-" +
											 new DecimalFormat("00").format(day);
					Date updatedExpiDate = java.sql.Date.valueOf(LocalDate.parse(expiDate)); //Creates a ExpiryDate Date Object
					
					st = conn.createStatement();
					sql = ("SELECT * FROM product WHERE pID = " + searchID + ";");
					rs = st.executeQuery(sql);
					validateMandatoryDatePeriod(updatedExpiDate, rs.getDate(5));
					ps = conn.prepareStatement(UPDATE_PRODUCTEXPI_DATE_SQL);
					ps.setDate(1, updatedExpiDate);
					ps.setInt(2, searchID);
					count = ps.executeUpdate();
					if(count > 0) {
						System.out.println("Record Updated");
						System.out.print("\n");
					}
					else {
						System.out.println("Error, Record not Updated");
						System.out.print("\n");
					}
					System.out.print("\n");
					break;
				}
				ps.close();
				rs.close();
		        st.close();
		        conn.close();
				} catch (NumberFormatException ime) {
					System.err.println("Failed to Update: Input Error");
				} catch(SQLException sqle) {
					System.err.println("Failed to Update Record to Product DB");
				} catch (DateTimeParseException pe) {	//Fails to Add Record if LocalDate Fails to Parse Dates
					System.err.println("Failed to Update Record: Invalid Valid Date Format please Try again");
				} catch (MandatoryFieldException mfe) { //Prints Message from MandatoryField Exceptions
					mfe.printStackTrace();
				} catch (IOException e) {
					System.err.println("Failed to Update: Input Error");
				}
			}while(choice != 6);

	}

	@Override
	public void deleteProduct() {
		// TODO Auto-generated method stub
	}


	@Override
	public void findProduct() {
		// TODO Auto-generated method stub
	}





}
