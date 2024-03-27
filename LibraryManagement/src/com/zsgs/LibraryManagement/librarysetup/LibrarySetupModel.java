package com.zsgs.LibraryManagement.librarysetup;

import com.zsgs.LibraryManagement.datalayer.LibraryDatabase;
import com.zsgs.LibraryManagement.model.Library;

public class LibrarySetupModel {

	private LibrarySetupView librarySetupView;

	private Library library;

	LibrarySetupModel(LibrarySetupView librarySetupView) {
		this.librarySetupView = librarySetupView;
		library = LibraryDatabase.getInstance().getLibrary();

	}

	// To check if the library object is null or has got a reference
	public void startSetup() {
		if (library == null || library.getLibraryId() == 0) {
			librarySetupView.initiateSetup();
		} else {
			librarySetupView.onSetupComplete(library);
		}
	}

	public void createLibrary(Library library) {
		if (library.getLibraryName().length() < 3 || library.getEmail().length() > 50) {
			librarySetupView.showAlert("Enter Valid Library Name and Email");
			return;
		}
		this.library = LibraryDatabase.getInstance().insertLibrary(library);
		librarySetupView.onSetupComplete(library);
	}
}
