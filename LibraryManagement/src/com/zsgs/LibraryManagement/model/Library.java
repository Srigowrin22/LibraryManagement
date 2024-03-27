package com.zsgs.LibraryManagement.model;

public class Library {
	private String libraryName;
	private int libraryId;
	private String phone;
	private String email;
	private String address;

	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}

	public void setLibraryId(int libraryId) {
		this.libraryId = libraryId;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLibraryName() {
		return libraryName;
	}

	public int getLibraryId() {
		return libraryId;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress() {
		return address;
	}
}
