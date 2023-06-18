package psallotment;

import java.util.*;

public abstract class User {
	private String name;
	private String email;
	private String password;
	
	// Constructor for User class
	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	// getters and setters for name and email
	public String getName() {
		String username = new String(name);
		return username;
	}
	
	public String getEmail() {
		String mail = new String(email);
		return mail;
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	public void setMail(String newMail) {
		email = newMail;
	}
	
	public String getPassword() {
		return password;
	}
	
	// Function to change password for an account
	public boolean changePassword() {
		Scanner sc = new Scanner(System.in);
		String pwd, retry;
		boolean pwdUpdated = false;
		System.out.println("You have requested to change your password.");
		while(true) {
			System.out.print("Enter your old password: ");
			pwd = sc.nextLine();
			if (pwd.compareTo(password) == 0) {
				System.out.print("Enter new password: ");
				password = sc.nextLine();
				pwdUpdated = true;
				break;
			}
			else {
				System.out.println("Wrong password, do you want to try again (Yes/No)?");
				retry = sc.nextLine();
				if (retry.compareToIgnoreCase("Yes") == 0)
					continue;
				else if (retry.compareToIgnoreCase("No") == 0) {
					System.out.println("Request to change password cancelled.");
					break;
				}
				else {
					System.out.println("Invalid input, request terminated");
					break;
				}
			}
		}
		sc.close();
		return pwdUpdated;
	}
}