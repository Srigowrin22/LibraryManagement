package com.zsgs.LibraryManagement.datalayer;

import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zsgs.LibraryManagement.model.Admin;
import com.zsgs.LibraryManagement.model.Book;
import com.zsgs.LibraryManagement.model.BookService;
import com.zsgs.LibraryManagement.model.Library;
import com.zsgs.LibraryManagement.model.User;

public class LibraryDatabase {

	private static LibraryDatabase libraryDatabase;

	private Library library;
	private ArrayList<Book> bookList = new ArrayList();
	private ArrayList<User> userList = new ArrayList<User>();
	private ArrayList<Admin> adminList = new ArrayList();
	private ArrayList<BookService> bookHistory = new ArrayList<>();
	
	private LibraryDatabase(){}

	public static LibraryDatabase getInstance() {
		if (libraryDatabase == null) {
			libraryDatabase = new LibraryDatabase();
		}
		return libraryDatabase;
	}

	public Library getLibrary() {
		return library;
	}

	public Library insertLibrary(Library library2) {
		this.library = library2;
		this.library.setLibraryId(1);
		return library;
	}

	public boolean insertBook(Book book) {
		boolean hasBook = false;
		for (Book addedBook : bookList) {
			if (addedBook.getName().equals(book.getName()) && addedBook.getAuthor().equals(book.getAuthor())) {
				hasBook = true;
				break;
			}
		}
		if (hasBook) {
			return false;
		} else {
			bookList.add(book);
			return true;
		}
	}

	public ArrayList<Book> getAllBooks() {
		System.out.printf("%-15s %-30s %-20s %-10s%n", "SERIES", "BOOK", "AUTHOR", "COUNT");
		for (Book book : bookList) {
			System.out.printf("%-15s %-30s %-20s %-10d%n", book.getSeries(), book.getName(), book.getAuthor(),
					book.getAvailableCount());
		}
		return bookList;
	}
	
	public ArrayList<Book> getAllBook(){
		return bookList;
	}

	private ArrayList<BookService> getAllBooksServices() {
		return bookHistory;
	}

	private ArrayList<Admin> getAllAdmin() {
		return adminList;
	}

	private ArrayList<User> getAllUsers() {
		return userList;
	}
	
	public List<Book> searchList(String name) {
		List<Book> searchList = new ArrayList();
		for (Book book : bookList) {
			if (book.getName().contains(name)) {
				searchList.add(book);
			} else if (book.getAuthor().contains(name)) {
				searchList.add(book);
			} else if ((book.getSeries().contains(name))) {
				searchList.add(book);
			}
		}
		// select query with where condition.
		return searchList;
	}

	public boolean updateBooks(String bookname, String changes, int choice) {
		// List of searched books
		List<Book> searchList = new ArrayList();
		for (Book book : bookList) {
			if (book.getName().equals(bookname)) {
				if (choice == 1) {
					book.setName(changes);
				} else if (choice == 2) {
					book.setAuthor(changes);
				} else if (choice == 3) {
					book.setSeries(changes);
				} else if (choice == 4) {
					book.setAvailableCount(Integer.parseInt(changes));
				}
				return true;
			}
		}
		// select query with where condition.
		return false;
	}

	public boolean insertUser(User user) {
		boolean hasUser = false;
		for (User addedUser : userList) {
			if (addedUser.getEmailId().equals(user.getEmailId())) {
				hasUser = true;
				break;
			}
		}
		if (hasUser) {
			return false;
		} else {
			userList.add(user);
			return true;
		}
	}

	public boolean checkUser(User user) {
		boolean hasUser = false;
		if (userList.size() == 0)
			return false;
		for (User addedUser : userList) {
			if (addedUser.getEmailId().equals(user.getEmailId())
					&& addedUser.getPassword().equals(user.getPassword())) {
				user.setName(addedUser.getName());
				hasUser = true;
				return hasUser;
			}
		}
		return hasUser;
	}

	public boolean insertHistory(String bookName, boolean b, String date, User user) {
		BookService bookService = new BookService();
		for (Book book : bookList) {
			if (book.getName().equals(bookName) && b && book.getAvailableCount() > 0) {
				int count = book.getAvailableCount();
				book.setAvailableCount(--count);
				bookService.setBookName(bookName);
				bookService.setName(user.getName());
				bookService.setBorrow(true);
				bookService.setbDate(date);
				bookHistory.add(bookService);
				return true;
			} else if (book.getName().equals(bookName) && b == false) {
				for (BookService bookHis : bookHistory) {
					if (bookHis.getName().equals(user.getName()) && bookHis.getBookName().equals(bookName)) {
						int count = book.getAvailableCount();
						book.setAvailableCount(++count);
						bookHis.setReturns(true);
						bookService.setrDate(date);
						bookHis.setrDate(date);
						System.out.println("Book returned");
						return true;
					}
				}
				return false;
			}
		}
		return false;
	}

	private LocalDate dateConverter(String date) {
		String[] t = date.split("/");
		return LocalDate.of(Integer.parseInt(t[2]), Integer.parseInt(t[1]), Integer.parseInt(t[0]));
	}

	public int payment(User user, String bookName) {
		for (BookService bookHis : bookHistory) {
			if (bookHis.getName().equals(user.getName()) && bookHis.getBookName().equals(bookName)) {
				LocalDate bDate = dateConverter(bookHis.getbDate());
				LocalDate rDate = dateConverter(bookHis.getrDate());
				Period p = Period.between(bDate, rDate);
				int difference = p.getDays() + (p.getMonths() * 30) + (p.getYears() * 365);
				if (difference > 6) {
					int due = bookHis.getPayment();
					due += ((difference - 6) * 2);
					bookHis.setPayment(due);
					user.setDue(due);
					return due;
				}
			}
		}
		return 0;
	}

	public int makePayment(int pay, User user) {
		int due = 0;
		for (BookService bookHis : bookHistory) {
			if (bookHis.getName().equals(user.getName()) && bookHis.getrDate()!=null && bookHis.getPayment()!=0) {
				due = bookHis.getPayment();
				if (pay < due) {
					due = due - pay;
					bookHis.setPayment(due);
					user.setDue(due);
//					return due;
				} else {
					due = pay - due;
					bookHis.setPayment(0);
					user.setDue(0);
//					return due;
				}
			}
		}
		return due;
	}

	public boolean getHistory(User user) {
		boolean b = false;
		System.out.printf("%-20s %-30s %-20s %-20s %-10s%n", "NAME", "BOOK", "BORROW DATE", "DUE DATE", "TOTAL FINE");
		for (BookService bookHis : bookHistory) {
			if (bookHis.getName().equals(user.getName())) {
				System.out.printf("%-20s %-30s %-20s %-20s %-10s%n", bookHis.getName(), bookHis.getBookName(),
						bookHis.getbDate(), bookHis.getrDate(), bookHis.getPayment());
				b = true;
			}
		}
		return b;
	}

	public void getAllUser() {
		System.out.printf("%-20s %-30s %-20s %-20s %-10s%n", "NAME", "BOOK", "BORROW DATE", "DUE DATE", "TOTALFINE");
		for (BookService bookHis : bookHistory) {
			System.out.printf("%-20s %-30s %-20s %-20s %-10s%n", bookHis.getName(), bookHis.getBookName(),
					bookHis.getbDate(), bookHis.getrDate(), bookHis.getPayment());
		}
	}

	public void BookIssued() {
		System.out.printf("%-20s %-30s %-20s %-10s%n", "NAME", "BOOK", "BORROW DATE", "TOTALFINE");
		for (BookService bookHis : bookHistory) {
			if (bookHis.getrDate() == null) {
				System.out.printf("%-20s %-30s %-20s %-10s%n", bookHis.getName(), bookHis.getBookName(),
						bookHis.getbDate(), bookHis.getPayment());
			}
		}
	}
	
	public void getDataFromJSON() {
		ArrayList<Book> bookList = LibraryDatabase.getInstance().getAllBook();
		ArrayList<User> userList = LibraryDatabase.getInstance().getAllUsers();
		ArrayList<Admin> adminList = LibraryDatabase.getInstance().getAllAdmin();
		ArrayList<BookService> bookHistory = LibraryDatabase.getInstance().getAllBooksServices();
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("C:\\Users\\Srigowri N\\Desktop\\LibraryManagement\\bookList.json");
		if(file.exists())
		try {
			bookList.addAll(mapper.readValue(new File("C:\\Users\\Srigowri N\\Desktop\\LibraryManagement\\bookList.json"), new TypeReference<List<Book>>() {}));	
			userList.addAll(mapper.readValue(new File("C:\\Users\\Srigowri N\\Desktop\\LibraryManagement\\userList.json"), new TypeReference<List<User>>() {}));	
			adminList.addAll(mapper.readValue(new File("C:\\Users\\Srigowri N\\Desktop\\LibraryManagement\\adminList.json"), new TypeReference<List<Admin>>() {}));	
			bookHistory.addAll(mapper.readValue(new File("C:\\Users\\Srigowri N\\Desktop\\LibraryManagement\\bookHistory.json"), new TypeReference<List<BookService>>() {}));	

		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void setDataToJSON() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonObject1 = mapper.writeValueAsString(bookList);
			mapper.writeValue(new File("C:\\Users\\Srigowri N\\Desktop\\LibraryManagement\\bookList.json"), bookList);
			
			String jsonObject2 = mapper.writeValueAsString(userList);
			mapper.writeValue(new File("C:\\Users\\Srigowri N\\Desktop\\LibraryManagement\\userList.json"), userList);
			
			String jsonObject3 = mapper.writeValueAsString(adminList);
			mapper.writeValue(new File("C:\\Users\\Srigowri N\\Desktop\\LibraryManagement\\adminList.json"), adminList);
			
			String jsonObject4 = mapper.writeValueAsString(bookHistory);
			mapper.writeValue(new File("C:\\Users\\Srigowri N\\Desktop\\LibraryManagement\\bookHistory.json"), bookHistory);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
