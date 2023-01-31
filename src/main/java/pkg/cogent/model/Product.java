package pkg.cogent.model;

import java.sql.Date;

/**
 * 
 * @author William Amaechi
 * @date   Jan 30, 2023
 */

public class Product {
	private int pId;				//Product ID
	private String pName;			//Product Name
	private String pCat;			//Product Category
	private double price;			//Product Price
	private Date manufactureDate;	//Product Manufacturing Date
	private Date expiryDate;		//Product Manufacturing Date
	
	public Product() {
		super();
	}
	
	public Product(int pId, String pName, String pCat, double price, Date manufactureDate, Date expiryDate) {
		super();
		this.pId = pId;
		this.pName = pName;
		this.pCat = pCat;
		this.price = price;
		this.manufactureDate = manufactureDate;
		this.expiryDate = expiryDate;
	}
	
	//Getter and Setter for Product ID
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	
	//Getter and Setter for Product Name
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	
	//Getter and Setter for Product Category
	public String getpCat() {
		return pCat;
	}
	public void setpCat(String pCat) {
		this.pCat = pCat;
	}
	
	//Getter and Setter for Product Price
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	//Getter and Setter for Product Manufacture Date
	public Date getManufactureDate() {
		return manufactureDate;
	}
	public void setManufactureDate(Date manufactureDate) {
		this.manufactureDate = manufactureDate;
	}
	
	//Getter and Setter for Product Expiration Date
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Override
	public String toString() {
		return String.format("[ Product ID: %1$s; Product Name: %2$s; Product Category: %3$s; Product Price: %4$s; Manufacturing Date: %5$s; Experation Date: %6$s ] \n",
																				this.pId, this.pName, this.pCat, this.price, this.manufactureDate, this.expiryDate);
	}
}
