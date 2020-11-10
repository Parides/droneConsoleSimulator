package Console;

import java.util.Random;
import java.util.ArrayList;

/**
 * Drone Arena class
 * 
 * This is the overall arena of the drone arena
 * 
 * NOTE: X POSITIONS ARE VERTICAL Y POSITIONS ARE HORIZONTAL
 *
 * @version Week5
 * @author Andreas Paridis
 */
public class DroneArena {
	// Arena dimensions
	private int arenaX;
	private int arenaY;
	Random randomGenerator; // Random Drone Coordinates generation
	
	/* Array list type Drone that will include a listing of our drone */
	ArrayList<Drone> droneList;

	/**
	 * CONSTRUCTOR
	 * 
	 * @param X arena coordinate
	 * @param Y arena coordinate
	 * 
	 *          randomGenerator is used for the generation of random coordinates of
	 *          the drones. Constructor also resets drone ID each new ID
	 * 
	 */
	DroneArena(int x, int y) {
		arenaX = x;
		arenaY = y;
		randomGenerator = new Random();// Initialisation of random number
		droneList = new ArrayList<Drone>(); // Initialisation of drone list
		Drone d = new Drone (1,1,Direction.WEST); // does not appear anywhere its only for d.resetID();
		d.resetID();//resets id
	}

	/* ==================== Getter Functions ==================== */

	/**
	 * @return the private attributes of the Arena
	 */
	public int getX() {
		return arenaX;
	}

	public int getY() {
		return arenaY;
	}
	/* ==================== Getter Functions ==================== */

	/**
	 * Displays arena dimensions and drone information Checks if arena exists Checks
	 * if drones exist in the arena
	 * 
	 * 
	 * @return the appropriate message based on the criteria
	 */
	public String toString() {
		String s = "";
		if (arenaX > 0 && arenaY > 0 && droneList.isEmpty() == false) { // both exist
			s = "";
			s += "The arena size is: " + arenaX + "x" + arenaY + " and ";
			for (int i = 0; i < droneList.size(); i++) {
				s += "\n" + droneList.get(i).toString();
			}
		} else if (arenaX > 0 && arenaY > 0 && droneList.isEmpty() == true) { // if only arena exists but no drones
			s += "The arena size is: " + arenaX + "x" + arenaY;
			System.err.print("\nThere are no drones to display information about\n");
		} else {
			//System.err.print("\nThe arena does not exist!\n");
			s += ""; // if nothing exists

		}
		return s;
	}

	/**
	 * Add drones in the arena list
	 * 
	 * Checks if arena is full: max contents of an array 10 by 10 are 100 (10*10)
	 * 
	 * Randomly generates a number using randomGenerator until an empty slot is
	 * found. It then creates a new drone. It gives it the generated coordinates
	 * while generating a direction and adds it to the arena
	 * 
	 * Also checks to give the appropriate message if the list is full/empty
	 * 
	 * @see getDroneAt
	 * 
	 */
	public void addDrone() {
		int varX, varY; // to be assigned random values
		if (droneList.size() < arenaX * arenaY) {
			do {
				varX = randomGenerator.nextInt(arenaX);
				varY = randomGenerator.nextInt(arenaY);
			} while (getDroneAt(varX, varY) != null); // checks if generated position is empty

			Drone ranDrone = new Drone(varX, varY, Direction.getRandom()); // creates drone
			droneList.add(ranDrone); // adds to list
		} else if (!droneList.isEmpty()) {
			System.err.println("\nThe arena is already full!\n");
		} else {
		//	System.err.println("\nThe arena does not exist");
		}
	}

	/**
	 * Displays drones on canvas
	 * 
	 * @param gets the canvas to display drones on
	 * 
	 *             Goes through the drone list and displays drones
	 * 
	 * @see displayDrone
	 * 
	 */
	public void showDrones(ConsoleCanvas c) {
		for (Drone d : droneList) {
			d.displayDrone(c);
		}
	}

	/**
	 * Retrieves drone based on given coordinates (if exists)
	 * 
	 * @param The coordinates that need to be checked if occupied
	 * 
	 *            Goes through the drone list Checks if drone is there
	 * 
	 * @see isHere
	 * @return returns drone occupying position
	 * 
	 * 
	 */
	public Drone getDroneAt(int x, int y) {
		Drone drone = null; // sets temporary drone in null
		for (Drone a : droneList) {
			if (a.isHere(x, y) == true) {
				drone = a; // replaces temporary null drone with the occupying drone
			}
		}
		return drone;
	}

	/**
	 * Checks if drone can move to given coordinates
	 * 
	 * @param The coordinates need to be checked for the drone to move on to
	 * 
	 *            Checks if there is a drone in the given coordinates. It also
	 *            checks if position for the drone to be moved to is out of arena
	 *            bounds
	 * @see getDroneAt
	 * 
	 * @return boolean allowance of movement
	 * 
	 */
	public boolean canMoveHere(int x, int y) {
		if (getDroneAt(x, y) != null || x >= arenaX || y >= arenaY || x < 0 || y < 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * show all the drones in the interface
	 * 
	 * @param c the canvas in which drones are shown
	 * 
	 * @see tryToMove
	 */
	public void moveAllDrones(DroneArena a) {
		for (Drone d : droneList)
			d.tryToMove(a);
	}
}
