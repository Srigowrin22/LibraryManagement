package com.zsgs.LibraryManagement.managebook;

import java.util.ArrayList;
import java.util.List;

import com.zsgs.LibraryManagement.datalayer.LibraryDatabase;
import com.zsgs.LibraryManagement.model.Book;
import com.zsgs.LibraryManagement.model.User;

class ManageBookModel {

	private ManageBookView manageBookView;

	public ManageBookModel(ManageBookView manageBookView) {
		this.manageBookView = manageBookView;
	}

	public void addNewBook(Book book) {
		if (LibraryDatabase.getInstance().insertBook(book)) {
			manageBookView.onBookAdded(book);
		} else {
			manageBookView.onBookExists(book);
		}
	}

	public void searchBook(String name) {
		List<Book> searchBooks = new ArrayList();
		searchBooks = LibraryDatabase.getInstance().searchList(name);
		if (searchBooks.size() != 0) {
			manageBookView.display(searchBooks);
		} else {
			manageBookView.showAlert("Book Not Found");
		}
	}

	public void updateBook(String bookname, String changes, int choice) {
		if (LibraryDatabase.getInstance().updateBooks(bookname, changes, choice)) {
			manageBookView.showAlert("Changes Made!");
		} else {
			manageBookView.showAlert("Book Not Found");
		}
	}

	public boolean borrow(String bookName, boolean b, String date, User user) {
		if (LibraryDatabase.getInstance().insertHistory(bookName, b, date, user)) {
			manageBookView.showAlert("Payment Pending!");
		} else {
			manageBookView.showAlert("Book Not Found");
			return false;
		}
		return true;
	}

	public void returns(String bookName, boolean b, String date, User user) {
		if (LibraryDatabase.getInstance().insertHistory(bookName, b, date, user)) {
			int due = LibraryDatabase.getInstance().payment(user, bookName);
			manageBookView.showAlert("Due Pending! " + due + " rupees");
		} else {
			manageBookView.showAlert("Book wasn't borrowed ");
		}
	}
}
