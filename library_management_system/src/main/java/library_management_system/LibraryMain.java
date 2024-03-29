package library_management_system;

import java.util.ArrayList;
import java.util.Scanner;

public class LibraryMain {

	public static void main(String[] args) throws Exception {

		boolean flag = true;
		while (flag) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("welcome to library !!!!!!!!! \n1. SignUp \n2. LogIn \n3.Exit");
			int choice = scanner.nextInt();

			LibraryCRUD crud = new LibraryCRUD();
			User user = new User();
			switch (choice) {
			case 1: // Signup
			{
				System.out.print("Enter your id :");
				int id = scanner.nextInt();
				System.out.print("Enter your Name : ");
				String name = scanner.next();
				System.out.print("Enter your phone : ");
				long phone = scanner.nextLong();
				System.out.print("Enter your email : ");
				String email = scanner.next();
				System.out.print("Enter your password : ");
				String password = scanner.next();

				user.setId(id);
				user.setName(name);
				user.setPhone(phone);
				user.setEmail(email);
				user.setPassword(password);

				int result = crud.signUpUser(user);

				if (result != 0) {
					System.out.println("SignUp sucessfull!!!!!!!");
				} else {
					System.out.println("SignUp Failed");

				}
			}
				break;

			case 2:// login user
			{
				System.out.print("Enter your email : ");
				String email = scanner.next();
				System.out.print("Enter your password : ");
				String password = scanner.next();
				boolean login = false;
				boolean failed = false;
				String dbPassword = crud.getPassword(email);

				if (dbPassword != null) {
					if (password.equals(dbPassword)) {
						System.out.println("Login Scessfull!!!!");
						login = true;
					} else {
						System.out.println("Invalid password");
						failed = true;

					}
				} else {
					System.out.println("User is not registered of email :" + email);
				}

				if (login) {

					System.out.println("Please select option: \n1.Add Book\n2.Search Book\n3.Delete Book ");
					int bookChoice = scanner.nextInt();

					switch (bookChoice) {
					case 1: {

						System.out.print("Enter the number of books you want to add: ");
						int numOfBooks = scanner.nextInt();

						/* Created an ArrayList */

						ArrayList<Book> booksList = new ArrayList<>();

						for (int i = 0; i < numOfBooks; i++) {

							Book book = new Book();

							System.out.print("Enter Book Id: ");
							int id = scanner.nextInt();
							scanner.nextLine();
							System.out.print("Enter Book Name: ");
							String bookName = scanner.nextLine();

							System.out.print("Enter Book Author Name: ");
							String author = scanner.nextLine();

							System.out.print("Enter Book Genre: ");
							String genre = scanner.nextLine();

							book.setId(id);
							book.setName(bookName);
							book.setAuthor(author);
							book.setGenre(genre);

							/* Added book in ArrayList using add method */
							booksList.add(book);
						}
						/* List is pass as argument to addBooksBatch method of LibraryCRUD */
						int result = crud.addBooksBatch(booksList);

						if (result != 0) {
							System.out.println("Books added succefully");
						} else {
							System.out.println("Failed to add books");
						}
						break;
					}
					}

				}
				if (failed) {
					System.out.println("1. Forget password");
					int key = scanner.nextInt();

					if (key == 1) {
						System.out.print("Enter your registered email : ");
						String oldEmail = scanner.next();

						if (email.equals(oldEmail)) {
							System.out.print("Enter your new password :");
							String pass = scanner.next();
							int result = crud.updatepassowrd(pass);
							if (result != 0) {
								System.out.println("Pasword updated!!!!");
							} else {
								System.out.println("Password updation failed ");
							}
						} else {
							System.out.println("User is not registered of email :" + email);
						}

					}
				}
			}
				break;

			case 3: {
				System.out.println("Application exited !!!!!!!!");
				flag = false;
			}
				break;
			}

		}

	}
}
