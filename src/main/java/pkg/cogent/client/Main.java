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
			System.out.println("1-Add");
			System.out.println("2-Display");
			System.out.println("3-Update");
			System.out.println("4-Delete");
			System.out.println("5-Exit");
			System.out.print("Please enter your choice?: ");
			try {
				choice = Integer.parseInt(user.readLine());
				System.out.print("\n");
				pc.accept(choice);
			} catch (NumberFormatException ime) {
				System.out.println("Input Error: Pls Try Again");
			}
		}while(choice!=5);
		user.close();
	}
}
