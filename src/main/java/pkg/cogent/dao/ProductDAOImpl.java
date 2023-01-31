package pkg.cogent.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import pkg.cogent.exception.MandatoryFieldException;
import pkg.cogent.jdbc.JDBCUtilsProductDB;
import pkg.cogent.model.Product;

public class ProductDAOImpl implements ProductDAO{
	private static String INSERT_PRODUCT_SQL = "INSERT INTO product " + 
			"(pID, pName, category, price, manufacture_date, expiry_date) VALUES " +
			"(?, ?, ?, ?, ?, ?);";


	@Override
	public void addProduct(int pId, String pName, String pCat, double price, String manufactureDate, String expiryDate) {
		try
		{
			Date manDate = java.sql.Date.valueOf(LocalDate.parse(manufactureDate)); //Creates a ManufactureDate Date Object
			Date expDate = java.sql.Date.valueOf(LocalDate.parse(expiryDate));		//Creates a ManufactureDate Date Object

			Connection connect = JDBCUtilsProductDB.getConnection();				//Connects to Product DB
			PreparedStatement pstmt = connect.prepareStatement(INSERT_PRODUCT_SQL); //Inserts Statement to A Product to our Product DB;

			validateMandatoryField(new Product(pId, pName, pCat, price, manDate, expDate)); //Checks if this is a valid Product Object
			
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
			System.out.println("Failed to add Record to Product DB");

		} catch (DateTimeParseException pe) {							//Fails to Add Record if LocalDate Fails to Parse Dates
			System.out.println("Failed to Record: Invalid Valid Date Format please Try again");
		} catch (MandatoryFieldException mfe) {
			mfe.printStackTrace();
		}
	}
	
	//Checks if the Mandatory Fields in Customer is valid
	public void validateMandatoryField(Product product) {	
		//Fails to add Record if Product is empty
		if (product == null) {
			throw new MandatoryFieldException("Failed to Record: Product Object can not be left blank");
		}
		//Fails to add Record if no pId is given
		else if(product.getpId() == 0){
			throw new MandatoryFieldException("Failed to Record: Product ID can not be left blank");
		}
		//Fails to add Record if no Manufacturing Date or Expiry Date is not added to Product
		else if((product.getManufactureDate()  == null) || (product.getExpiryDate() == null )){	
			throw new MandatoryFieldException("Failed to Record: Invalid Date");
		}
		//Fails to add Record if Manufacturing Date is After Current Date
		else if(product.getManufactureDate().toLocalDate().isAfter(LocalDate.now())){
			throw new MandatoryFieldException("Failed to Record: Manufacturing Date Cannot be After The Current Date");
		}
		//Fails to add Record if Manufacturing Date is After Expiry Date
		else if(product.getManufactureDate().toLocalDate().isAfter(product.getExpiryDate().toLocalDate())) {
			throw new MandatoryFieldException("Failed to Record: Manufacturing Date Cannot be After The Expiry Date");
		}
		//Fails to add Record if Manufacturing Date is more than 5 Years before Current Day
		else if(product.getManufactureDate().toLocalDate().isBefore(LocalDate.now().minusYears(5))){
			throw new MandatoryFieldException("Failed to Record: Manufacturing Date can not be before " + LocalDate.now().minusYears(5));
		}
		//Fails to add Record if Expiry Date is more than 10 Years After Current Day
		else if(product.getExpiryDate().toLocalDate().isAfter(LocalDate.now().plusYears(10))){
			throw new MandatoryFieldException("Failed to Record: Expiry Date can not be after " + LocalDate.now().plusYears(10));
		}
		//Fails to add Record if Expiry Date is more than 10 Years Before Current Day
		else if(product.getExpiryDate().toLocalDate().isBefore(LocalDate.now().minusYears(10))){
			throw new MandatoryFieldException("Failed to Record: Expiry Date can not be before " + LocalDate.now().minusYears(10));
		}
	}

	@Override
	public void deleteProduct() {
		// TODO Auto-generated method stub

	}


	@Override
	public void findProduct() {
		// TODO Auto-generated method stub

	}


	@Override
	public void updateProduct() {
		// TODO Auto-generated method stub

	}

}
