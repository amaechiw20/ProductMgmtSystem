package pkg.cogent.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import pkg.cogent.controller.ProductController;

/**
 * 
 * @author William Amaechi
 * @date   Jan 30, 2023
 */
public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
		int choice = 0;
		ProductController pc = new ProductController();
		do {
			System.out.println("1-Add");
			System.out.println("2-Display");
			System.out.println("3-Update");
			System.out.println("4-Delete");
			System.out.println("5-Exit");
			System.out.print("Please enter your choice?: ");
			
			try {
				int year;
				int month;
				int day;
				choice = Integer.parseInt(user.readLine());
				System.out.print("\n");
				switch(choice)
				{
				case 1:
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
					
					String manufactureDate = (new DecimalFormat("0000").format(year) + "-" +
											  new DecimalFormat("00").format(month) + "-" +
											  new DecimalFormat("00").format(day));
					System.out.print("\n");
					
					System.out.println("Please enter Expiry Date (YYYY/MM/DD): ");
					System.out.print("Expiry Year: ");
					year = Integer.parseInt(user.readLine());
							
					System.out.print("Expiry Month: ");
					month = Integer.parseInt(user.readLine());
					
					System.out.print("Expiry Day: ");
					day = Integer.parseInt(user.readLine());
					
					String expiryDate = (new DecimalFormat("0000").format(year) + "-" +
							 			 new DecimalFormat("00").format(month) + "-" +
							 			 new DecimalFormat("00").format(day));
					
					pc.create(pId, pName, pCat, price, manufactureDate, expiryDate);
				}
			} catch(IOException ioe) {
				System.out.println("Input Error: Pls Try Again");
				ioe.printStackTrace();
			} catch (NumberFormatException ime) {
				System.out.println("Input Error: Pls Try Again");
				ime.printStackTrace();
			}
		}while(choice!=5);
		user.close();
	}
}
