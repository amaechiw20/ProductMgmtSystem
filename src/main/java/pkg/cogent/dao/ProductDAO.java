package pkg.cogent.dao;

/**
 * 
 * @author: William U. Amaechi
 * @date: 	Jan 30, 2023
 */

public interface ProductDAO {
	public void addProduct(int pId, String pName, String pCat, double price, String manufactureDate, String expiryDate);
	public void deleteProduct();
	public void findProduct();
	public void updateProduct();
}
