package com.zsgs.LibraryManagement;

import java.io.IOException;

import com.zsgs.LibraryManagement.datalayer.LibraryDatabase;
import com.zsgs.LibraryManagement.login.LoginView;
import com.zsgs.LibraryManagement.model.Book;

public class LibraryManagement {

	// Private static, hence only one instance is created and other class cannot
	// access the object
	// Singleton pattern

	private static LibraryManagement libraryManagement;

	private String appName = "Library Management System";
	private String appVersion = "0.0.1";

	private LibraryManagement() {
		// Object cannot be created from another class
	}

	public static LibraryManagement getInstance() {
		if (libraryManagement == null) {
			libraryManagement = new LibraryManagement();
		}
		return libraryManagement;
	}

	public String getName() {
		return appName;
	}

	public String getVersion() {
		return appVersion;
	}

	public static void create() {
		LoginView loginView = new LoginView();
		loginView.init();
	}

	public static void main(String[] args) throws IOException {
		LibraryDatabase.getInstance().getDataFromJSON();	
		LibraryManagement.getInstance().create();		
		LibraryDatabase.getInstance().setDataToJSON();
	}
}
