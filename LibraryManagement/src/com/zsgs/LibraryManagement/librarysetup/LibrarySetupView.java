package com.zsgs.LibraryManagement.librarysetup;

import java.util.Scanner;

import com.zsgs.LibraryManagement.LibraryManagement;
import com.zsgs.LibraryManagement.datalayer.LibraryDatabase;
import com.zsgs.LibraryManagement.managebook.ManageBookView;
import com.zsgs.LibraryManagement.manageusers.ManageUsersView;
import com.zsgs.LibraryManagement.model.Library;

public class LibrarySetupView {

	private LibrarySetupModel librarySetupModel;

	public LibrarySetupView() {
		librarySetupModel = new LibrarySetupModel(this);
	}

	// Library Setup
	public void init() {
		librarySetupModel.startSetup();
	}

	public void showAlert(String alertText) {
		System.out.println(alertText);
	}

	// Library setup complete
	public void onSetupComplete(Library library) {
		System.out.println("\nLibrary Setup completed");
		System.out.println("Current Library: " + library.getLibraryName());
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\n 1. Add Book\n 2. Add user \n 3. Search Book \n 4. Update Book "
					+ "\n 5. Users History \n 6. Books List \n 7. Books Issued \n 9. Logout \n 0. Exit \n Enter your Choice:");
			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
				new ManageBookView().initAdd();
				break;

			case 2:
				new ManageUsersView().initAdd();
				break;

			case 3:
				new ManageBookView().initSearch();
				break;

			case 4:
				new ManageBookView().update();
				break;

			case 5:
				LibraryDatabase.getInstance().getAllUser();
				break;

			case 6:
				LibraryDatabase.getInstance().getAllBooks();
				break;

			case 7:
				LibraryDatabase.getInstance().BookIssued();
				break;

			case 9:
				System.out.println("You have been successfully logged out!");
				LibraryManagement.getInstance().create();
				return; // return the control from switch case

			case 0:
				System.out.println("Thanks for using " + LibraryManagement.getInstance().getName());
				return;

			default:
				System.out.println("Please enter a valid choice!");
			}
		}
	}

	public void initiateSetup() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the Library Details");
		// Object Creation for library
		Library library = new Library();
		System.out.println("Enter the Library Name: ");
		library.setLibraryName(scanner.nextLine());
		System.out.println("Enter the Library EmailId: ");
		library.setEmail(scanner.nextLine());
		librarySetupModel.createLibrary(library);
	}
}
