
import java.sql.SQLException;

import project.*;

/**
 * 
 * @author Cherol Phoshoko
 * @version 16.0.2, 2021-09-30
 */
public class Insert extends Database {

	/**
	 * Constructor
	 * @throws SQLException
	 */
	Insert() throws SQLException {
		super();

	}

	/**
	 * This methosd is used to insert projects/ project details into the database.
	 * 
	 * @param newProject Project being added to the database
	 * @param customer Person represents project's customer
	 * @param architect Person represents project's architect
	 * @param contractor Person represents project's contractor
	 * @param engineer Person represents project's engineer
	 * @param manager Person represents project's customer manager
	 * @throws SQLException if error occurs
	 */
	void project(Project newProject, Person customer, Person architect, Person contractor, Person engineer,
			Person manager) throws SQLException {
		int projectNum = 0;
		rowsChanged = statement.executeUpdate("INSERT INTO Projects VALUES(default, \"" + newProject.projectName
				+ "\", \"" + manager.name + "\" \"" + manager.surname + "\"" + ", '" + newProject.buildingType + "', '"
				+ newProject.buildingAddress + "', '" + newProject.erfNum + "', '" + newProject.deadline + "', "
				+ newProject.totalFee + ", " + newProject.totalPaid + ", 'Not Finalized'," + " null, \"" + customer.name
				+ "\" \"" + customer.surname + "\", \"" + architect.name + "\" \"" + architect.surname + "\", \""
				+ contractor.name + "\" \"" + contractor.surname + "\", \"" + engineer.name + "\" \"" + engineer.surname
				+ "\");");

		results = statement.executeQuery("Select proj_num from projects where proj_name=\"" + newProject.projectName
				+ "\" and customer= \"" + customer.name + "\" \"" + customer.surname + "\"");
		// getting project Number for child tables
		while (results.next()) {
			projectNum = results.getInt("proj_Num");
			System.out.println(projectNum);
		}
		// temporarily disabling foreign key to be able to insert values into child
		// tables
		statement.executeUpdate("Set foreign_key_checks = 0");

		changeTable(customer, projectNum, "Customers");
		changeTable(architect, projectNum, "Architects");
		changeTable(contractor, projectNum, "Contractors");
		changeTable(engineer, projectNum, "Engineers");
		changeTable(manager, projectNum, "Managers");

		statement.executeUpdate("Set foreign_key_checks = 1");
		System.out.println("Query Complete " + rowsChanged + " rows changed.");
		quit();

	}

	/**
	 * This method adds values into the database person's tables <br>
	 * 
	 * @param person Person Object who's values will be inserted into an appropriate table.
	 * @param projectNum Integer for the number of the project person is linked to.
	 * @throws SQLException if error occurs.
	 */
	private void changeTable(Person person, int projectNum, String tableName) throws SQLException {
		rowsChanged += statement.executeUpdate("INSERT INTO " + tableName + " VALUES(default, \"" + person.name + " "
				+ person.surname + "\", " + projectNum + ", '" + person.telephoneNum + "', '" + person.email + "', \""
				+ person.physicalAdd + "\");");
	}

	@Override
	void quit() {

	}
}
