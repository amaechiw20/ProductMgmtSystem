package pkg.cogent.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
			System.out.println("|-----------------------------|");
			System.out.println("|-----Product Manager Menu----|");
			System.out.println("|-----------------------------|");
			System.out.println("1-Add Product");
			System.out.println("2-Display Products");
			System.out.println("3-Update Product");
			System.out.println("4-Delete Product");
			System.out.println("5-Find Product");
			System.out.println("6-Exit");
			System.out.print("Please enter your choice?: ");
			try {
				choice = Integer.parseInt(user.readLine());
				pc.accept(choice);
			} catch (NumberFormatException ime) {
				System.out.println("Input Error: Pls Try Again");
			}
		}while(choice!=7);
		user.close();
	}
}
