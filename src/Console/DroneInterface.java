/* 
 * Drone Class
 * 
 * Week 3
 *  
 * @author ANDREAS PARIDIS
 * 
 */
package Console;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.JFileChooser;

import java.awt.Insets;
import java.io.*;
import javax.swing.filechooser.FileFilter;

/**
 * DroneInterface Class
 * 
 * It is a menu for the user. It gives him the ability to
 * 
 * Create a new arena. Add drones. Move drones. Load pre existing arenas from
 * files. Save arenas to files
 * 
 * NOTE: X POSITIONS ARE VERTICAL Y POSITIONS ARE HORIZONTAL
 *
 * @version Week5
 * @author Andreas Paridis
 */
public class DroneInterface {

	private Scanner s; // scanner used for input from user
	private DroneArena myArena; // arena in which drones are shown

	/**
	 * Constructor
	 * 
	 * Sets up menu for the user. Creates a scanner to get users input and acts
	 * accordingly
	 * 
	 * @see addDrone
	 * @see toString
	 * @see moveAllDrones
	 * @see droneList
	 * @see doDisplay
	 */
	public DroneInterface() {
		s = new Scanner(System.in); // set up scanner for user input
		/*
		 * An empty drone arena is needed so the program doesn't crash due to null
		 * pointer
		 */
		int userX = 0;
		int userY = 0;
		myArena = new DroneArena(0, 0);

		char ch = ' '; // initial empty char
		do {
			// Checks if arena exists so it warns the user
			if (myArena.getX() == 0 || myArena.getY() == 0) {
				System.err.println("Arena does not exist");
				System.err.println("Create a new arena with (N)");
			}
			// The menu
			System.out.print("\nEnter:" + "\n\t(N)ew Arena " + "\n\t(A)dd drone," + "\n\t(I)Get information, "
					+ "\n\t(D)isplay Arena & Drones, " + "\n\t(M)ove Drones Once, " + "\n\t(S)Move Drones 10 times "
					+ "\n\t(F)ile Options" + "\n\t(X)EXIT > ");
			ch = s.next().charAt(0); // gets user input
			s.nextLine();
			// The following are the outcomes based on the user's input
			switch (ch) {
			case 'A':
			case 'a':
				myArena.addDrone(); // Adds a drone in the arena
				break;
			case 'I':
			case 'i':
				System.out.print(myArena.toString()); // prints arena dimensions and included drones
				break;
			case 'M':
			case 'm':
				myArena.moveAllDrones(myArena); // Move all drones
				doDisplay(); // Display new arena with moved drones
				break;
			case 'N':
			case 'n':
				/*
				 * The following allows the user to create a new arena with their desired
				 * Dimensions. It also includes exception handling for the dimensions for when
				 * the user inputs a non numerical character
				 */
				System.out.print("\n NEW ARENA DIMENTIONS");
				System.out.print("\nX > ");
				try {
					userX = s.nextInt(); // checks input
				} catch (Exception a) { // catches exception
					System.err.println("Only numbers allowed"); // prints warning message
					System.out.print("X > "); // asks user to enter a value again
					s.nextLine();
					userX = s.nextInt();
				}
				// repeats above but for Y dimension
				System.out.print("\nY > ");
				try {
					userY = s.nextInt();
				} catch (Exception b) {
					System.err.println("Only numbers allowed");
					System.out.print("X > ");
					s.nextLine();

					userY = s.nextInt();
				}
				myArena = new DroneArena(userX, userY); // arena is created with user input
				break;
			case 'S':
			case 's':
				/*
				 * The following option moves the drones 10 times, display new arena each move
				 * It checks if the arena is empty for according error message and includes
				 * error handing for interruption of the movement
				 * 
				 */
				if (myArena.droneList.isEmpty() == false) {
					for (int i = 0; i < 10; i++) {
						System.out.println("-----------------------------------");
						myArena.moveAllDrones(myArena);
						doDisplay();
						System.out.print(myArena.toString());
						try {
							TimeUnit.MILLISECONDS.sleep(200); // Wait for 200ms
						} catch (InterruptedException e) {
							System.err.format("IOException: %s%n", e);
						}
					}
				} else if (myArena.droneList.isEmpty() == true && myArena.getX() > 0 && myArena.getY() > 0) {
					System.err.println("No drones to move!"); // if list is empty there are no drones to move
				}
				break;
			case 'x':
				ch = 'X'; // when X detected program ends
				break;
			case 'd':
			case 'D':
				doDisplay(); // Displays arena with drones
				break;
			case 'F':
			case 'f':
				fileOptions(); // opens file options menu
			}
		} while (ch != 'X');

		s.close(); // close scanner
	}

	/**
	 * The following takes care of the displaying of the arena and drones in the
	 * canvas
	 * 
	 * It checks if arena exists or is empty and gives the user the appropriate
	 * message
	 * 
	 * @see showDrones
	 * @see ConsoleCanvas
	 * @see droneList
	 * 
	 */
	void doDisplay() {
		if (myArena.getX() > 0 && myArena.getY() > 0) { // exist check
			/*
			 * Canvas dimensions are increased by 2 since we don't want the barriers of the
			 * arena to be a space where drones move in hence we are making it "unreachable"
			 * by moving it out of bounds
			 *
			 */
			ConsoleCanvas canvas = new ConsoleCanvas(myArena.getX() + 2, myArena.getY() + 2);
			myArena.showDrones(canvas);
			System.out.println(canvas.toString()); // displays arena
			if (myArena.droneList.isEmpty()) { // if no drones exist
				System.err.println("\nNo drones added yet!");
			}
		} else {
			// System.out.println("\nArena does not exist!"); // if arena does not exist
		}
	}

	/**
	 * Sets up menu for the file options.
	 * 
	 * The user is given the option to save the current arena/drone build in a file
	 * with their preferred name or load a pre existing one.
	 * 
	 * Creates a scanner to get users input and acts accordingly
	 * 
	 * Note that the user must choose the file he wants to save into or load from.
	 * 
	 * Load/Save options incorporate error handling in case of something song wrong
	 * in loading
	 */
	void fileOptions() {
		s = new Scanner(System.in); // scanner
		char ch = ' ';
		System.out.print("Enter: " + "\n(S)ave File" + "\n(L)oad File" + "\n(X)Exit >");
		ch = s.next().charAt(0);
		s.nextLine();
		switch (ch) {
		case 'S':
		case 's':
			try {
				saveFile();
			} catch (Exception e) {
				System.out.print(" ");// error message
			}
			break;
		case 'L':
		case 'l':
			try {
				loadFile();
			} catch (Exception a) {
				System.out.print(" "); // error message
			}

			break;
		case 'x':
			ch = 'X';
			break;
		default:
			break;
		}

	}

	/*
	 * Allows the user to chose a file name and directory to save a file
	 * 
	 * The method throws exception so the when it is eventually called, error
	 * handling can take place
	 *
	 * No filter added to give user freedom of choosing whatever file they want
	 */
	void saveFile() throws IOException {

		// JFileChooser properties
		JFileChooser chooser = new JFileChooser("C:\\Users\\antre\\Desktop\\Year 2\\Java\\Week 5\\");// directory
		chooser.setDialogTitle("Save arena to: "); // Window title
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // What files are shown

		/**
		 * The following is a filter for the file chooser. It only displays directories
		 * and files with the .arena extensions that are created from saving an arena
		 */
		chooser.setFileFilter(new FileFilter() {
			public String getDescription() {
				return "Arena Files (*.arena)";
			}

			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else {
					String fileName = f.getName().toLowerCase();
					return fileName.endsWith(".arena");
				}
			}
		});

		// this where the windows opens
		s = new Scanner(System.in); // scanner
		String userInput = " "; // to be the users file name
		System.out.println("Please enter the name of the file");
		userInput = s.next(); // gets file name
		chooser.setApproveButtonText("Save");// changes approve button
		chooser.setApproveButtonToolTipText("Save location");
		int returnVal = chooser.showOpenDialog(null); // null for now, stores if user clicks open/cancel
		if (returnVal == JFileChooser.APPROVE_OPTION) { // if user presses open
			// gets file selected by user
			File userFile = new File(chooser.getSelectedFile() + "\\" + userInput + ".arena");
			System.out.println("Arena saved!\n" + "File Name: " + userInput + ".arena" + "\nDirectory: "
					+ userFile.getAbsolutePath()); // prints file chosen and directory
			// Saving Process
			FileWriter fileWriter = new FileWriter(userFile); // creates a new file writer
			BufferedWriter writer = new BufferedWriter(fileWriter); // adds to buffer
			// First saves the arena dimensions on first line
			writer.write(Integer.toString(myArena.getX()));
			writer.write(" ");
			writer.write(Integer.toString(myArena.getY()));
			writer.newLine(); // change line
			// Each line store one drone in the form X Y DIRECTION
			for (Drone d : myArena.droneList) {
				writer.write(Integer.toString(d.getX()));
				writer.write(" ");
				writer.write(Integer.toString(d.getY()));
				writer.write(" ");
				writer.write(Integer.toString(d.getDir().ordinal()));
				writer.newLine();
			}
			writer.close();
		}
	}

	/*
	 * Allows the user to pick a file to load a pre existing arena build using
	 * JFileChooser and buffer reader to read from text files
	 * 
	 * The method throws exception so the when it is eventually called, error
	 * handling can take place
	 * 
	 * No filter added to give user freedom of choosing whatever file they want
	 */
	void loadFile() throws IOException {

		// JFileChooser properties
		JFileChooser chooser = new JFileChooser("C:\\Users\\antre\\Desktop\\Year 2\\Java\\Week 5\\FILES");// directory
		chooser.setDialogTitle("Load arena from: ");// Window title
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);// What files are shown
		// This will eventually store the file contents
		String fileContents = " ";
		/**
		 * The following is a filter for the file chooser. It only displays directories
		 * and files with the .arena extensions that are created from saving an arena
		 */
		chooser.setFileFilter(new FileFilter() {
			public String getDescription() {
				return "Arena Files (*.arena)";
			}

			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				} else {
					String fileName = f.getName().toLowerCase();
					return fileName.endsWith(".arena");
				}
			}
		});
		// This where the windows opens
		int returnVal = chooser.showOpenDialog(null); // stores if user clicks open/cancel
		if (returnVal == JFileChooser.APPROVE_OPTION) {// if user presses open
			File userFile = chooser.getSelectedFile(); // gets the file selected by user
			if (chooser.getSelectedFile().isFile()) { // if the file exists
				System.out.println("Arena Loaded!\n" + "File Name: " + userFile.getName() + "\nDirectory: "
						+ userFile.getAbsolutePath());// prints file chosen and directory

				// Clear the current drone list
				if (!myArena.droneList.isEmpty()) {
					myArena.droneList.clear();
				}
				// Loading process
				FileReader fileReader = new FileReader(userFile);
				BufferedReader reader = new BufferedReader(fileReader);

				fileContents = reader.readLine();
				String[] loadSize = fileContents.split(" "); // Store file contents separated by spaces in array
				int loadX = Integer.parseInt(loadSize[0]); // First integer is arena X dimension
				int loadY = Integer.parseInt(loadSize[1]); // Second integer is arena Y dimension
				myArena = new DroneArena(loadX, loadY); // creates a new arena with the gathered dimensions
				while (fileContents != null) { // while not in the end of the file
					fileContents = reader.readLine();
					String[] numbers = fileContents.split(" ");
					int x = Integer.parseInt(numbers[0]); // First integer is drone X coordinate
					int y = Integer.parseInt(numbers[1]); // Second integer is drone Y coordinate
					int ordinal = Integer.parseInt(numbers[2]); // Third integer is drone facing Direction
					// creates drone and adds it do list
					myArena.droneList.add(new Drone(x, y, Direction.values()[ordinal]));

				}
				reader.close();
			}
		}

	}

	public static void main(String[] args) {
		DroneInterface r = new DroneInterface(); // just call the interface
	}
}
