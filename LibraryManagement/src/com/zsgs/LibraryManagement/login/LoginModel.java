package com.zsgs.LibraryManagement.login;

class LoginModel {

	private LoginView loginView;

	public LoginModel(LoginView loginView) {
		this.loginView = loginView;
	}

	public void validateUser(String userName, String password) {
		if (isValidUserName(userName)) {
			if (isValidPassword(password)) {
				loginView.onLoginSuccess();
			} else {
				loginView.onLoginFailed("Invalid password :(");
			}
		} else {
			loginView.onLoginFailed("Invalid Username :(");
		}
	}

	private boolean isValidUserName(String userName) {
		return (userName.equals("zsgs"));
	}

	private boolean isValidPassword(String password) {
		return (password.equals("admin"));
	}
}
