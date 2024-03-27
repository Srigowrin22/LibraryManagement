package com.zsgs.LibraryManagement.manageusers;

import java.util.Scanner;

import com.zsgs.LibraryManagement.LibraryManagement;
import com.zsgs.LibraryManagement.datalayer.LibraryDatabase;
import com.zsgs.LibraryManagement.managebook.ManageBookView;
import com.zsgs.LibraryManagement.model.User;

public class ManageUsersView {

	private ManageUsersModel manageUsersModel;

	public ManageUsersView() {
		manageUsersModel = new ManageUsersModel(this);
	}

	public void initAdd() {
		System.out.println("Enter the User Details: ");
		User user = new User();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the User Name: ");
		user.setName(scanner.next());
		System.out.println("Enter the User EmailId: ");
		user.setEmailId(scanner.next());
		System.out.println("Set password: ");
		user.setPassword(scanner.next());
		manageUsersModel.addNewUser(user);
	}

	public void userLogin() {
		Scanner scanner = new Scanner(System.in);
		User user = new User();
		System.out.println("Hello User nice to meet you!");
		System.out.println("Enter your MailID: ");
		user.setEmailId(scanner.next());
		System.out.println("Enter your password");
		user.setPassword(scanner.next());
		manageUsersModel.loginCheck(user);

	}

	public void onLoginOperation(User user) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Account Logged In");
		while (true) {
			System.out.println("Enter your choice: ");
			System.out.println(
					"1.Search Books \n2.Borrow Books \n3.Return Book \n4.Make Payment \n5.MyActivity \n6.EXIT");
			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
				new ManageBookView().initSearch();
				break;
			case 2:
				boolean status1 = new ManageBookView().bookActivity(true, user);
				if (status1) {
					System.out.println("Make payment of rupees 100");
					manageUsersModel.makePayment(scanner.nextInt(), true, user);
					System.out.println("Book Borrowed successfully!");
				}
				break;

			case 3:
				boolean status2 = new ManageBookView().bookActivity(false, user);
				if (status2) {
					System.out.println("Keep visiting our Library! Have a nice day!");
				}
				break;

			case 4:
				int due = user.getDue();
				System.out.println("Due of " + due + " rupees has to be paid!");
				System.out.println("Make the payment now...!");
				int pay = scanner.nextInt();
				int rDue = LibraryDatabase.getInstance().makePayment(pay, user);
				if (pay > due) {
					System.out.println("Balance returned: " + rDue + " rupees!");
				} else if (rDue > 0) {
					System.out.println("Due pending: " + rDue + " rupees..!Pay as soon as possible :)");
				}else {
					System.out.println("Due for the retuned book is cleared");
				}
				break;

			case 5:
				manageUsersModel.checkActivity(user);
				break;

			case 6:
				System.out.println("Thanks for using " + LibraryManagement.getInstance().getName());
				return;

			default:
				System.out.println("Enter a valid choice");
			}
		}
	}

	public void onUserAdded(User user) {
		System.out.println("The user '" + user.getName() + "' is added successfully");
		checkForAddNewUser();
	}

	public void onUserExists(User user) {
		System.out.println("The user '" + user.getName() + "' already exists!");
		checkForAddNewUser();
	}

	public void showAlert(String alertText) {
		System.out.println(alertText);
	}

	private void checkForAddNewUser() {
		System.out.println("Do you want to add more users? Yes/No? ");
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.next();
		if (choice.equalsIgnoreCase("YES")) {
			initAdd();
		} else if (choice.equalsIgnoreCase("NO")) {
			System.out.println("Thanks for adding!");
		} else {
			System.out.println("Invalid Choice! Enter valid choice!");
			checkForAddNewUser();
		}
	}
}
