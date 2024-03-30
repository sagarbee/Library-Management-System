package library_management_system;

import java.util.ArrayList;
import java.util.Iterator;
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
				String name = scanner.nextLine();
				scanner.nextLine();
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

					boolean goBack = true;
					while (goBack) {
						System.out.println(
								"Please select option: \n1.Add Book\n2.Search Book\n3.Delete Book \n4.Go Back");
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
						case 2: {

							System.out
									.print("How you want to search: \n1.Book ID\n2.Book Name\n3.Author Name\n4.Genre");
							int searchOption = scanner.nextInt();

							switch (searchOption) {
							case 1: {
								System.out.print("Enter Book ID: ");
								int bookId = scanner.nextInt();
								scanner.nextLine();
								Book foundBook = crud.searchById(bookId);
								if (foundBook != null) {
									System.out.println("Book Found");
									System.out.println("ID: " + foundBook.getId());
									System.out.println("Name: " + foundBook.getName());
									System.out.println("Author: " + foundBook.getAuthor());
									System.out.println("Genre: " + foundBook.getGenre());
								} else {
									System.out.println("Book not found.");
								}
								break;
							}
							case 2: {
								scanner.nextLine();
								System.out.print("Enter Book Name: ");
								String bookName = scanner.nextLine();

								Book foundBook = crud.searchByBookName(bookName);
								if (foundBook != null) {
									System.out.println("Book Found");
									System.out.println("ID: " + foundBook.getId());
									System.out.println("Name: " + foundBook.getName());
									System.out.println("Author: " + foundBook.getAuthor());
									System.out.println("Genre: " + foundBook.getGenre());

								} else {
									System.out.println("Book not found.");
								}
								break;

							}
							case 3: {
								scanner.nextLine();
								System.out.println("Enter Book Author: ");
								String bookName = scanner.nextLine();

								ArrayList<Book> foundBook = crud.searchByAuthorList(bookName);

								if (!foundBook.isEmpty()) {
									System.out.println("Book found");
									for (Book found : foundBook) {
										System.out.println("ID: " + found.getId());
										System.out.println("Name: " + found.getName());
										System.out.println("Author: " + found.getAuthor());
										System.out.println("Genre: " + found.getGenre());
									}
								} else {
									System.out.println("Book not found.");
								}
								break;
							}
							case 4: {
								scanner.nextLine();
								System.out.println("Enter Book Genre: ");
								String bookName = scanner.nextLine();

								ArrayList<Book> foundBook = crud.searchByGenreList(bookName);

								if (!foundBook.isEmpty()) {
									System.out.println("Book found");
									for (Book found : foundBook) {
										System.out.println("ID: " + found.getId());
										System.out.println("Name: " + found.getName());
										System.out.println("Author: " + found.getAuthor());
										System.out.println("Genre: " + found.getGenre());
									}
								} else {
									System.out.println("Book not found.");
								}
								break;
							}
							}
							break;
						}
						case 3: {

							System.out.println("Delete book by \n1.ID\n2.Name");
							int deleteBy = scanner.nextInt();

							switch (deleteBy) {
							case 1: {

								scanner.nextLine();
								System.out.print("Enter Book ID: ");
								int bookId = scanner.nextInt();
								crud.deleteBookById(bookId);

							}
							case 2: {

								scanner.nextLine();
								System.out.print("Enter Book Name: ");
								String deleteBookName = scanner.nextLine();
								crud.deleteBookByName(deleteBookName);
							}
							}

							break;
						}
						case 4: {

							goBack = false;
							break;
						}

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
