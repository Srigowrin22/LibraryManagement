package com.zsgs.LibraryManagement.manageusers;

import com.zsgs.LibraryManagement.datalayer.LibraryDatabase;
import com.zsgs.LibraryManagement.model.User;

public class ManageUsersModel {

	private ManageUsersView manageUsersView;

	ManageUsersModel(ManageUsersView manageUsersView) {
		this.manageUsersView = manageUsersView;
	}

	public void addNewUser(User user) {
		if (LibraryDatabase.getInstance().insertUser(user)) {
			manageUsersView.onUserAdded(user);
		} else {
			manageUsersView.onUserExists(user);
		}
	}

	public void loginCheck(User user) {
		if (LibraryDatabase.getInstance().checkUser(user)) {
			manageUsersView.onLoginOperation(user);
		} else {
			manageUsersView.showAlert("Account Do no Exist! Reach out for Admin help :)");
		}
	}

	public void makePayment(int rupees, boolean b, User user) {
		if (b) {
			manageUsersView.showAlert("---Payment Done!---)");
		}
	}

	public void checkActivity(User user) {
		if (LibraryDatabase.getInstance().getHistory(user)) {

		} else {
			manageUsersView.showAlert("No records");
		}
	}
}
