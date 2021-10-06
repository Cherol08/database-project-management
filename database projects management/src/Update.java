import java.sql.Date;
import java.sql.SQLException;
/**
 * 
* @author Cherol Phoshoko
 * @version 16.0.2, 2021-09-30
 */
public class Update extends Database {

	/**
	 * 
	 * @throws SQLException
	 */
	Update() throws SQLException {
		super();

	}


	/**
	 * 
	 * @param projNum
	 * @param newTelNum
	 * @param newEmail
	 * @throws SQLException
	 */
	void setContractorDetails(int projNum, String newTelNum, String newEmail) throws SQLException {

		rowsChanged = statement.executeUpdate("UPDATE Contractors SET Contractor_tel='" + newTelNum + "', Contractor_email ='"
				+ newEmail + "' WHERE Proj_Num=" + projNum);
        System.out.println("Query complete, " + rowsChanged + " rows changed.");
        quit();
	}


	/**
	 * 
	 * @param projNum
	 * @param newDeadline
	 * @throws SQLException
	 */
	void setDeadline(int projNum, String newDeadline) throws SQLException {
		Date deadline = Date.valueOf(newDeadline);
		rowsChanged = statement.executeUpdate("UPDATE Projects SET Deadline='" + deadline + "' WHERE Proj_Num=" + projNum );
        System.out.println("Query complete, " + rowsChanged + " rows changed.");
        quit();
	}


	/**
	 * 
	 * @param projNum
	 * @param paidAmount
	 * @throws SQLException
	 */
	void setTotalPaid(int projNum, Double paidAmount) throws SQLException {
		rowsChanged = statement.executeUpdate("UPDATE Projects SET Total_Paid= Total_paid + " + paidAmount + " WHERE Proj_Num=" + projNum);
        System.out.println("Query complete, " + rowsChanged + " rows changed.");
        quit();

	}
	
	/**
	 * 
	 * @param projNum
	 * @throws SQLException
	 */
	void setAsFinalized(int projNum) throws SQLException {
		long millis = System.currentTimeMillis();
		Date currentDate = new java.sql.Date(millis);
		rowsChanged = statement.executeUpdate("UPDATE Projects SET Completion= 'Finalized', Completion_Date='" + currentDate + "' WHERE Proj_Num=" + projNum);
        System.out.println("Query complete, " + rowsChanged + " rows changed.");
        quit();
	}
	
	@Override
	void quit() {

	}

}
