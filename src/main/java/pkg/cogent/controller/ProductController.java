package pkg.cogent.controller;

import pkg.cogent.service.ProductService;

public class ProductController {

	ProductService service;

	public ProductController() {
		service = new ProductService();
	}
	public void accept(int choice) {

		switch (choice) {
		case 1:
			service.add();
			break;
		case 2:
			service.display();
			break;
		case 3:
			service.update();
			break;
		case 4:
			System.out.println("Please enter the customer ID");
			//	cID = sc.next(); 
			//	cs.delete(cID); // Is called by client to delete Customer from Customer based on their Client ID
			break;
		case 5:
			System.out.println("Please enter the customer ID");
			//	cID = sc.next();
			//	cs.findCustomerByID(cID); // Is called by client to find Customer in Customer Array based on their Client ID
			break;
		case 6:
			//	cs.findYoungestCustomer(); // Is called by client to find the youngest Customer in our Client Array
			break;
		case 7:
			System.exit(choice); // Is called to close program
			break;
		}
	}

}
