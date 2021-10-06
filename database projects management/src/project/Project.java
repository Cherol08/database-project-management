package project;

/**
 * 
 * @author Cherol Phoshoko
 * @version 16.0.2, 2021-09-30
 */
public class Project {

	/**
	 * Project details
	 */
	public String projectName,  buildingType,  buildingAddress, erfNum, deadline;
	/**
	 * Project fees
	 */
	public double totalFee, totalPaid;

	/**
	 * Constructor 
	 * 
	 * @param projectName String for project Name
	 * @param buildingType  String for project Building Type
	 * @param buildingAddress  String for project address
	 * @param erfNum  String for project ERF number
	 * @param deadline  String for project deadline
	 * @param totalFee Double for project fee
	 * @param totalPaid Double for amount paid
	 */
	public Project(String projectName, String buildingType, String buildingAddress, String erfNum,
			String deadline, double totalFee, double totalPaid) {
		this.projectName = projectName;
		this.buildingType = buildingType;
		this.buildingAddress = buildingAddress;
		this.deadline = deadline;
		this.erfNum = erfNum;
		this.totalFee = totalFee;
		this.totalPaid = totalPaid;

	}

	/**
	 * 
	 * @return String method Returns formatted project Details
	 */
	public String projectDetails() {

		return "Project: " + projectName + "\nBuilding Type: " + buildingType
				+ "\nBuilding Address: " + buildingAddress + "\nERF number: " + erfNum + "\nProject deadline: "
				+ deadline + "\n\nTotal Project fee: R" + totalFee + "\nTotal amount paid to date: R" + totalPaid;
	}
}
