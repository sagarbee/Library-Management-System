package library_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LibraryCRUD {

	public Connection getConnection() throws Exception {

		// 1.Load Driver
		Class.forName("com.mysql.cj.jdbc.Driver");

		// 2.Establish a connection
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/librarydb", "root",
				"Pass@123");
		return connection;
	}

	public int signUpUser(User user) throws Exception // exception object propogation
	{
		Connection connection = getConnection();

		String query = "INSERT INTO user (id, name, phone, email, password) VALUES (?, ?, ?, ?, ?)";
		// 3.Create Statement
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, user.getId());
		preparedStatement.setString(2, user.getName());
		preparedStatement.setLong(3, user.getPhone());
		preparedStatement.setString(4, user.getEmail());
		preparedStatement.setString(5, user.getPassword());
		// 4.Execute a statement
		int result = preparedStatement.executeUpdate();

		// 5.Close connection
		connection.close();
		return result;

	}

	public String getPassword(String email) throws Exception {
		Connection connection = getConnection();

		String query = "select password from user where email=?";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, email);

		ResultSet resultSet = preparedStatement.executeQuery();

		String password = null;
		if (resultSet.next()) {
			password = resultSet.getString("password");
		}

		// 5.Close connection
		connection.close();
		return password;
	}

	public int updatepassowrd(String password) throws Exception {
		Connection connection = getConnection();
		String query = "update user set password =?";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, password);

		int result = preparedStatement.executeUpdate();

		connection.close();
		return result;
	}

	public int addBooksBatch(ArrayList<Book> bookList) throws Exception {
		
		Connection connection = getConnection();
		String query = "insert into book(id, name, author, genre) values (?, ?, ?, ?)";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		
		bookList.forEach(book ->
		{
			try {
				preparedStatement.setInt(1, book.getId());
				preparedStatement.setString(2, book.getName());
				preparedStatement.setString(3, book.getAuthor());
				preparedStatement.setString(4, book.getGenre());
				preparedStatement.addBatch();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		});
		

		int[] result = preparedStatement.executeBatch();
		int count = 0;
		
		for(int r : result)
		{
			count+=r;
		}
		connection.close();
		return count;

	}
	
	public Book searchById(int id) throws Exception
	{
		Connection connection = getConnection();
		Book book = null;
		String query = "select * from book where id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if(resultSet.next())
		{
			book = new Book();
			book.setId(resultSet.getInt("id"));
			book.setName(resultSet.getString("name"));
			book.setAuthor(resultSet.getString("author"));
			book.setGenre(resultSet.getString("genre"));
			
		}
		connection.close();
		return book;
		
	}
	
	/* Creating a ArrayList which can store book data by given name*/
	public Book searchByBookName(String name) throws Exception{
		Connection connection = getConnection();
		
		Book book = null;

		String query = "select * from book where name = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1,name);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next())
		{
			book = new Book();
			book.setId(resultSet.getInt("id"));
			book.setName(resultSet.getString("name"));
			book.setAuthor(resultSet.getString("author"));
			book.setGenre(resultSet.getString("genre"));
			
		}
		connection.close();
		
		
		return book;
	}
	
	public ArrayList<Book> searchByAuthorList(String name) throws Exception{
		Connection connection = getConnection();
		
		ArrayList<Book> bookList = new ArrayList();

		String query = "select * from book where author = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1,name);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next())
		{
			Book book = new Book();
			book.setId(resultSet.getInt("id"));
			book.setName(resultSet.getString("name"));
			book.setAuthor(resultSet.getString("author"));
			book.setGenre(resultSet.getString("genre"));
			bookList.add(book);
		}
		connection.close();
		
		
		return bookList;
	}
	
	public ArrayList<Book> searchByGenreList(String name) throws Exception{
		Connection connection = getConnection();
		
		ArrayList<Book> bookList = new ArrayList();

		String query = "select * from book where genre = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1,name);
		
		ResultSet resultSet = preparedStatement.executeQuery();
		while(resultSet.next())
		{
			Book book = new Book();
			book.setId(resultSet.getInt("id"));
			book.setName(resultSet.getString("name"));
			book.setAuthor(resultSet.getString("author"));
			book.setGenre(resultSet.getString("genre"));
			bookList.add(book);
		}
		connection.close();
		
		
		return bookList;
	}
	
	public void deleteBookById(int id) throws Exception {
	    Connection connection = getConnection();

	    String query = "DELETE FROM book WHERE id = ?";
	    PreparedStatement preparedStatement = connection.prepareStatement(query);
	    preparedStatement.setInt(1, id);

	    int rowsDeleted = preparedStatement.executeUpdate();
	    if (rowsDeleted > 0) {
	        System.out.println("Book with ID " + id + " deleted successfully.");
	    } else {
	        System.out.println("No book found with the given ID.");
	    }

	    connection.close();
	}

	
	public void deleteBookByName(String name) throws Exception
	{
		Connection connection = getConnection();
		String query = "delete from book where name = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, name);
		
		int rowsDeleted = preparedStatement.executeUpdate();
	    if (rowsDeleted > 0) {
	        System.out.println("Book with name " + name + " deleted successfully.");
	    } else {
	        System.out.println("No book found with the given name.");
	    }

	    connection.close();
		
	}
	
}
