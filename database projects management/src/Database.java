import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class creates a connection to an existing database PoisedPMS, which
 * the program uses to access and maintain information about a company's construction
 * projects.
 * 
 * 
 * @author Cherol Phoshoko
 * @version 16.0.2, 2021-09-30
 */
public abstract class Database {

	Connection connection;
	Statement statement;
	ResultSet results;
	int rowsChanged;

	/**
	 * This constructor creates access to the database
	 *
	 * @throws SQLException if error occurs
	 */
	Database() throws SQLException {
		// connection creates a connection to the poisedpms database and statement, uses
		// connection to help run commands.
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/poisedpms?useSSL=false", "otheruser",
				"swordfish");
		statement = connection.createStatement();

	}

	/**
	 * This method gets results from database table after an executed query and uses loop to print them all
	 * 
	 * 
	 * @throws SQLException if error occurs
	 */
	void getResults() throws SQLException {
		while (results.next()) {
			System.out.println(results.getInt("Proj_Num") + ", " + results.getString("Proj_Name") + ", "
					+ results.getString("Proj_manager") + ", " + results.getString("Building_Type") + ", "
					+ results.getString("Proj_Address") + ", " + results.getString("Erf_Num") + ", "
					+ results.getDate("Deadline") + ", " + results.getDouble("Total_Fee") + ", "
					+ results.getDouble("Total_paid") + ", " + results.getString("Completion") + ", "
					+ results.getDate("Completion_Date") + ", " + results.getString("Customer") + ", "
					+ results.getString("Architect") + ", " + results.getString("Contractor") + ", "
					+ results.getString("Engineer") + "\n");
		}
	}

	/**
	 * Closes the database and all connections to it.
	 *
	 * @throws SQLException if error occurs
	 */
	void quit() throws SQLException {
		connection.commit();
		results.close();
		statement.close();
		connection.close();
	}
}
