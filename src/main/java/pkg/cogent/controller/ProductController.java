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
			service.delete();
			break;
		case 5:
			service.find();
			break;
		case 6:
			System.exit(choice); // Is called to close program
			break;
		}
	}

}
