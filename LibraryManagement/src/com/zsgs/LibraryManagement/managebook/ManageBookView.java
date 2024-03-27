package com.zsgs.LibraryManagement.managebook;

import java.util.List;
import java.util.Scanner;
import com.zsgs.LibraryManagement.model.Book;
import com.zsgs.LibraryManagement.model.User;

public class ManageBookView {

	private ManageBookModel manageBookModel;

	public ManageBookView() {
		this.manageBookModel = new ManageBookModel(this);
	}

	public void initAdd() {
		System.out.println("Enter Book Details: ");
		Scanner scanner = new Scanner(System.in);
//		Object creation for book
		Book book = new Book();
		System.out.println("Enter the book series:");
		book.setSeries(scanner.nextLine());
		System.out.println("Enter the book name:");
		book.setName(scanner.nextLine());
		System.out.println("Enter the book Author: ");
		book.setAuthor(scanner.nextLine());
		System.out.println("Enter the book Count: ");
		book.setAvailableCount(scanner.nextInt());
		manageBookModel.addNewBook(book);
	}

	public void initSearch() {
		System.out.println("Search Book");
		Scanner scanner = new Scanner(System.in);
		while (true) {

			int choice;
			System.out.println("Enter your choice:");
			System.out.println("1.Name \t2.Author \t3.Series \t4.EXIT");
			try {
				choice = scanner.nextInt();
			} catch (Exception e) {
				System.out.println("Give Valid Input");
				continue;
			}
			String search = "";
			if (choice != 4) {
				scanner.nextLine();
				System.out.println("Enter your search: ");
				search = scanner.nextLine();
			}
			switch (choice) {
			case 1:

				manageBookModel.searchBook(search);
				break;
			case 2:

				manageBookModel.searchBook(search);
				break;
			case 3:

				manageBookModel.searchBook(search);
				break;
			case 4:
				return;
			default:
				System.out.println("Enter a valid choice");
			}
//			Thread.dumpStack();
		}
	}

	public void display(List<Book> searchBooks) {
		System.out.println("Series \tName \tAuthor \tCount");
		for (Book book : searchBooks) {
			System.out.println(book.getSeries() + "\t" + book.getName() + "\t" + book.getAuthor() + "\t"
					+ book.getAvailableCount());
		}
	}

	public void update() {
		System.out.println("Update Book");
		Scanner scanner = new Scanner(System.in);
		while (true) {
			int choice;
			System.out.println("Enter the book name: ");
			String bookName = scanner.nextLine();
			System.out.println("Enter your choice to update:");
			System.out.println("1.Name \t2.Author \t3.Series \t4.Available Count \t5.EXIT");
			try {
				choice = scanner.nextInt();
			} catch (Exception e) {
				System.out.println("Give Valid Input");
				continue;
			}
			String changes;
			scanner.nextLine();
			System.out.println("Make changes: ");
			changes = scanner.nextLine();
			switch (choice) {
			case 1:
				manageBookModel.updateBook(bookName, changes, choice);
				return;
			case 2:
				manageBookModel.updateBook(bookName, changes, choice);
				return;
			case 3:
				manageBookModel.updateBook(bookName, changes, choice);
				return;
			case 4:
				manageBookModel.updateBook(bookName, changes, choice);
				return;
			case 5:
				return;
			default:
				System.out.println("Enter a valid choice");
			}
		}
	}

	public boolean bookActivity(boolean b, User user) {
		Scanner scanner = new Scanner(System.in);
		if (b) {
			System.out.println("Enter the book name:");
			String name = scanner.nextLine();
			System.out.println("Todays date(dd/mm/yyyy):");
			String date = scanner.nextLine();
			manageBookModel.borrow(name, b, date, user);
			return true;
		} else {
			System.out.println("Enter the book name that you return:");
			String name = scanner.nextLine();
			System.out.println("Todays date(dd/mm/yyyy):");
			String date = scanner.nextLine();
			manageBookModel.returns(name, b, date, user);
			return true;
		}
	}

	public void onBookAdded(Book book) {
		System.out.println("The Book '" + book.getName() + "' is added successfully!");
		checkForAddNewBook();
	}

	public void onBookExists(Book book) {
		System.out.println("The Book '" + book.getName() + "' already exists!");
		checkForAddNewBook();
	}

	private void checkForAddNewBook() {
		System.out.println("Do you want to add more books? Yes/No");
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.next();
		if (choice.equalsIgnoreCase("YES")) {
			initAdd();
		} else if (choice.equalsIgnoreCase("NO")) {
			System.out.println("Thanks for Adding");
		} else {
			System.out.println("Invalid choice!  Enter a valid choice!");
			checkForAddNewBook();
		}
	}

	public void showAlert(String alertText) {
		System.out.println(alertText);

	}
}
