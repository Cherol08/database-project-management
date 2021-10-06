import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Collectors;

import project.*;

/**
 * This java program manages construction projects by storing pending and
 * complete project details, as well as maintaining updates about the project.
 *
 * @author Cherol Phoshoko
 * @version 16.0.2, 2021-09-30
 */
public class Main {
	/**
	 * Field variables
	 *
	 */
	static String name, surname;
	static String userType;
	static String telephoneNum, email, physicalAdd;
	static String projectName, buildingType, erfNum, deadline;
	static double totalFee, totalPaid;
	
	
	/**
	 * Main method of the program used to access the existing projects, add updates,
	 * finalize them, or even add new projects to the database at runtime. <br>
	 * @param args command line arguments
	 * @throws SQLException if error occurs.
	 */
	public static void main(String[] args) throws SQLException {
		// Field variables used for project and persons' details.

		// User prompted to add new project details when program runs
		while (true) {
			try {
				System.out.println("""
						\nProgram menu:
						1 - Display all projects
						2 - Add new project
						3 - Edit existing project
						4 - Display incomplete projects
						5 - Display overdue projects
						6 - Select a project
						0 - exit
						Enter option:""");

				@SuppressWarnings("resource")
				Scanner input = new Scanner(System.in);
				int option = input.nextInt();
				if (option == 1) {
					new Select().allProjects();

				} else if (option == 2) {

					Project newProject;
					input.nextLine();
					System.out.println("\nEnter Project name:");
					projectName = input.nextLine();
					System.out.println("Enter building type:");
					buildingType = input.nextLine();
					System.out.println("Enter building address:");
					physicalAdd = input.nextLine();
					System.out.println("Enter building ERF number:");
					erfNum = input.nextLine();
					System.out.println("Enter project deadline(YYYY-MM-DD):");
					deadline = input.nextLine();

					while (validDateFormat(deadline) == false) {
						System.out.println("\nInvalid date format. Try again.");
						System.out.println("Enter project deadline(yy-MM-dd):");
						deadline = input.nextLine();
					}
					while (true) {
						try {
							System.out.println("Overall Project Fee:");
							totalFee = input.nextDouble();
							System.out.println("Total paid to date:");
							totalPaid = input.nextDouble();

							// new project Object
							newProject = new Project(toTitleCase(projectName.strip()),
									toTitleCase(buildingType.strip()), toTitleCase(physicalAdd.strip()), erfNum.strip(),
									deadline.strip(), totalFee, totalPaid);
							break;
						} catch (InputMismatchException e) {
							System.out.println("Invalid Input!\nPlease enter valid amount.");
							input.nextLine();
						}
					}
					// created person objects, they're accessible out of the loops
					Person customer = null;
					Person architect = null;
					Person engineer;
					Person manager = null;
					Person contractor = null;

					// loop used to add details of project customer, architect and contractor,
					// manager and engineer
					System.out.println("\nCustomer Details".toUpperCase());
					input.nextLine();
					int loop = 0;

					while (true) {
						loop++;
						createPersons();
						if (loop == 1) {
							userType = "Customer";
							customer = new Person(toTitleCase(name.strip()), toTitleCase(surname.strip()),
									toTitleCase(userType.strip()), telephoneNum.strip(), email.strip(),
									toTitleCase(physicalAdd.strip()));
							if (projectName.isBlank()) {
								// if project name not provided Building type & client surname used in place of.
								newProject.projectName = newProject.buildingType + " " + toTitleCase(surname.strip());
							}
							System.out.println("\nProject Manager Details".toUpperCase());
						} else if (loop == 2) {
							userType = "Manager";
							manager = new Person(toTitleCase(name.strip()), toTitleCase(surname.strip()),
									toTitleCase(userType), telephoneNum.strip(), email.strip(),
									toTitleCase(physicalAdd.strip()));
							System.out.println("\nProject Architect Details".toUpperCase());
						} else if (loop == 3) {
							userType = "Architect";
							architect = new Person(toTitleCase(name.strip()), toTitleCase(surname.strip()),
									toTitleCase(userType.strip()), telephoneNum.strip(), email.strip(),
									toTitleCase(physicalAdd.strip()));
							System.out.println("\nProject Contractor Details".toUpperCase());
						} else if (loop == 4) {
							userType = "Contractor";
							contractor = new Person(toTitleCase(name.strip()), toTitleCase(surname.strip()),
									toTitleCase(userType.strip()), telephoneNum.strip(), email.strip(),
									toTitleCase(physicalAdd.strip()));
							System.out.println("\nProject Engineer Details".toUpperCase());
						}

						else {
							userType = "Engineer";
							engineer = new Person(toTitleCase(name.strip()), toTitleCase(surname.strip()),
									toTitleCase(userType.strip()), telephoneNum.strip(), email.strip(),
									toTitleCase(physicalAdd.strip()));
							break;

						}

					}
					// assert used to declare null values as false.
					System.out.println("\n\nInformation Successfully Saved!\n\n");
					System.out.println(newProject.projectDetails() + "\n\n");
					System.out.println(customer.personDetails() + "\n\n");
					System.out.println(manager.personDetails() + "\n\n");
					System.out.println(architect.personDetails() + "\n\n");
					System.out.println(contractor.personDetails() + "\n\n");
					System.out.println(engineer.personDetails());

					new Insert().project(newProject, customer, manager, architect, contractor, engineer);

					// calling projectList methods with Project name as input specifies project to
					// edit
				} else if (option == 3) {
					System.out.println("\nEnter Project number:");
					int project2Edit = input.nextInt();
					new Select().project(project2Edit);
					while (true) {
						try {
							System.out.println("""
									\n1 - Edit contractor's details
									2 - Edit project deadline
									3 - Edit Amount paid to date
									4 - Finalize project
									0 - Return to main menu
									""");

							int choice = input.nextInt();
							if (choice == 1) {
								input.nextLine();
								System.out.println("\nEnter contractor's new telephone number:");
								telephoneNum = input.nextLine();
								System.out.println("Enter contractor's new email Address: ");
								email = input.nextLine();
								new Update().setContractorDetails(project2Edit, telephoneNum.strip(), email.strip());
								new Select().contractor(project2Edit);
							} else if (choice == 2) {
								input.nextLine();
								System.out.println("\nEnter New deadline(YYYY-MM-DD):");
								deadline = input.nextLine();
								while ((validDateFormat(deadline)) == false) {
									System.out.println("\nInvalid date format. Try again." + deadline);
									System.out.println("\nEnter New deadline(YYYY-MM-DD):");
									deadline = input.nextLine();
								}
								new Update().setDeadline(project2Edit, deadline);

							} else if (choice == 3) {
								while (true) {
									try {
										System.out.println("\nEnter amount paid:");
										Double paidAmount = input.nextDouble();
										new Update().setTotalPaid(project2Edit, paidAmount);
										break;
									} catch (InputMismatchException e) {
										System.out.println("Invalid Input!\nPlease enter valid amount.");
										input.nextLine();
									}
								}

							} else if (choice == 4) {
								new Update().setAsFinalized(project2Edit);
							} else if (choice == 0) {
								break;
							} else {
								System.out.println("Invalid option!");
							}
						} catch (InputMismatchException e) {
							System.out.println("Invalid input, try again!");
							input.nextLine();
						}
					}

				} else if (option == 4) {
					new Select().incompleteProject();

				} else if (option == 5) {
					new Select().overdueProject();

				} else if (option == 6) {

					input.nextLine();
					String project2Edit;
					System.out.println("\nEnter project name or number:");
					project2Edit = input.nextLine();

					try {
						new Select().project(Integer.parseInt(project2Edit));
					} catch (NumberFormatException e) {
						new Select().project(toTitleCase(project2Edit.strip()));
					}

				} else if (option == 0) {
					System.out.println("\nLogout Successful!");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input, try again!");
			}
		}

	}

	/**
	 * Method for entering person object details
	 */
	public static void createPersons() {
		try {
			@SuppressWarnings("resource")
			Scanner input = new Scanner(System.in);
			System.out.println("\nEnter name:");
			name = input.nextLine();
			System.out.println("Enter surname:");
			surname = input.nextLine();
			System.out.println("Enter telephone number:");
			telephoneNum = input.nextLine();
			System.out.println("Enter email address:");
			email = input.nextLine();
			System.out.println("Enter physical address:");
			physicalAdd = input.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("Invalid Input!\nPlease enter valid amount.");
		}
	}

	/**
	 * This method validates if user inputs the correct date format for the project
	 * deadlines <br>
	 *
	 * @param deadline String will be formatted to a date
	 * @return boolean value for valid date.
	 */

	public static boolean validDateFormat(String deadline) {
		boolean valid;
		try {
			String dateFormat = "yyyy-MM-dd";
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat)
					.withResolverStyle(ResolverStyle.LENIENT);
			LocalDate date = LocalDate.parse(deadline, dateFormatter);
			valid = true;
		} catch (DateTimeParseException e) {
			valid = false;
		}
		return valid;
	}

	/**
	 * This recursive method is used to convert strings to title case. <br>
	 *
	 * @param string is the string to be converted to title case.
	 * @return returns the formatted string.
	 */

	public static String toTitleCase(String string) {
		String word_separator = " ";
		if (string == null || string.isEmpty()) {
			return string;
		}
		// this splits string at space and converts each 1st character of substring to
		// title case and following characters to lower case
		return Arrays.stream(string.split(word_separator)).map(
				word -> word.isEmpty() ? word : Character.toTitleCase(word.charAt(0)) + word.substring(1).toLowerCase())
				.collect(Collectors.joining(word_separator));
	}

}
