package project;
/**
 * 
 * @author Cherol Phoshoko
 * @version 16.0.2, 2021-09-30
 */
public class Person{
	/**
	 * Person name, surname and type
	 */
	public  String name, surname, userType;
	/**
	 * Person's personal information
	 */
	public String telephoneNum, email, physicalAdd;

	/**
	 * Constructor 
	 * 
	 * 
	 * @param name String for person's name
	 * @param surname  String for person's surname
	 * @param userType  String for person type
	 * @param telephoneNum  String for person's telephone number
	 * @param email  String for person's email
	 * @param physicalAdd  String for person's address
	 */
	public Person( String name, String surname, String userType, String telephoneNum, String email, String physicalAdd){
		
		this.name = name;
        this.surname = surname;
        this.userType = userType;
        this.telephoneNum = telephoneNum;
        this.email = email;
        this.physicalAdd = physicalAdd;
		
	}

	/**
	 * 
	 * @return  String method Returns formatted person details
	 */
	public String personDetails() {
		return userType + "\nName: " + name + " " + surname + "\nTelephone Number: " + telephoneNum
                + "\nEmail address: " + email + "\nPhysical Address: " + physicalAdd;
	}

}
