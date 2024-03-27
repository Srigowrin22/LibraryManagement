package com.zsgs.LibraryManagement.login;

import com.zsgs.LibraryManagement.librarysetup.LibrarySetupView;
import com.zsgs.LibraryManagement.manageusers.ManageUsersView;

import java.util.Scanner;
import com.zsgs.LibraryManagement.LibraryManagement;

public class LoginView {

	private LoginModel loginModel;

	public LoginView() {
		loginModel = new LoginModel(this);
	}

	public void init() {
		Scanner scanner = new Scanner(System.in);
//		Thread.dumpStack();
		System.out.println("--- " + LibraryManagement.getInstance().getName() + " --- \nversion "
				+ LibraryManagement.getInstance().getVersion());
		System.out.println("\n\nPlease login to proceed.");
		while (true) {
			System.out.println("1.AdminLogin \n2.UserLogin \n3.EXIT");
			System.out.println("Enter your choice: ");
			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
				proceedLogin();
				break;
			case 2:
				new ManageUsersView().userLogin();
				break;
			case 3:
				System.out.println("Thanks for using " + LibraryManagement.getInstance().getName());
				return;
			default:
				System.out.println("Enter a valid choice");
			}
		}

	}

	private void proceedLogin() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Name: ");
		String userName = sc.nextLine();
		System.out.println("Enter the password: ");
		String password = sc.nextLine();
		loginModel.validateUser(userName, password);
	}

	public void onLoginSuccess() {
		System.out.flush();
		System.out.println("\n\nLogin successful...\n\n ---- welcome to " + LibraryManagement.getInstance().getName()
				+ " - v" + LibraryManagement.getInstance().getVersion() + "----");

		LibrarySetupView librarySetupView = new LibrarySetupView();
		librarySetupView.init();
	}

	public void onLoginFailed(String alertText) {
		System.out.println(alertText);
		checkForLogin();
	}

	private void checkForLogin() {
		System.out.println("Do you want to try again? Yes/No: ");
		Scanner scanner = new Scanner(System.in);
		String choice = scanner.next();
		if (choice.equalsIgnoreCase("YES")) {
			proceedLogin();
		} else if (choice.equalsIgnoreCase("No")) {
			System.out.println("Thank YOU! See You Soon!");
		} else {
			System.out.println("Invalid Choice! Enter a valid choice!");
			checkForLogin();
		}
	}
}
