package library_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
}
