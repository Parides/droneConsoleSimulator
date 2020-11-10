package Console;

/**
 * Drone class
 * 
 * Drone Object. Class responsible for position checks and Drone movement
 * 
 * NOTE: X POSITIONS ARE VERTICAL Y POSITIONS ARE HORIZONTAL
 *
 * @version Week5
 * @author Andreas Paridis
 */
public class Drone {
	// Drone Coordinates
	private int droneX;
	private int droneY;
	private int id; // Actual ID of the drone
	private static int nextId = 1; // Static to constantly count existing drones
	private Direction dir; // Where drone faces

	/**
	 * CONSTRUCTOR
	 * 
	 * @param X drone coordinate
	 * @param Y drone coordinate
	 * 
	 *          Static nextID keeps track of every new drone created When a new
	 *          drone is created the running 'current' nextID is its id nextID gets
	 *          incremented for the next drone to be created
	 * 
	 * @see Direction.java for @param d
	 * 
	 */
	Drone(int x, int y, Direction d) {
		droneX = x;
		droneY = y;
		id = nextId;
		nextId = nextId + 1;
		dir = d;
	}

	/* ==================== Getter Functions ==================== */

	/**
	 * @return the private attributes of Drones
	 */
	public int getX() {
		return droneX;
	}

	public int getY() {
		return droneY;
	}

	public Direction getDir() {
		return dir;
	}
	/* ==================== Getter Functions ==================== */

	/* ==================== Setter Functions ==================== */
	public void resetID()
	{
		nextId = 1;
	}
	/* ==================== Setter Functions ==================== */
	/**
	 * Displays drone information
	 * 
	 * @return drone information in string format
	 * 
	 * @see Direction.java for dir.toString
	 */
	public String toString() {
		return "Drone " + id + " is at " + droneX + "," + droneY + " with direction " + dir.toString();
	}

	/**
	 * Compares parameters with existing drone positions and checks if position is
	 * occupied
	 * 
	 * @param X,Y Coordinates to be checked
	 * 
	 * @return occupancy of position
	 */
	public boolean isHere(int x, int y) {
		if (x == droneX && y == droneY)
			return true;
		else
			return false;
	}

	/**
	 * Displays drone on canvas
	 * 
	 * @param What canvas to print the drone on
	 * @see showIt
	 */
	public void displayDrone(ConsoleCanvas c) {
		char droneChar = 'D'; // What character represents the drones
		c.showIt(droneX, droneY, droneChar);
	}

	/**
	 * Moves drones
	 * 
	 * Checks if drone can move Moves it if it can Else changes drone's facing
	 * direction and tries again
	 * 
	 * @param The arena it moves the drone on
	 * 
	 *            Checks the drone's facing direction and eligibility of according
	 *            movement
	 * 
	 * @see canMoveHere
	 * 
	 *      If possible drone's coordinates change accordingly else makes drone face
	 *      the next direction
	 * 
	 * @see Direction.java
	 * 
	 *      IMPORTANT: X POSITIONS ARE VERTICAL Y POSITIONS ARE HORIZONTAL
	 * 
	 * 
	 * 
	 */
	public void tryToMove(DroneArena a) {

		switch (dir) {
		case NORTH:
			if (a.canMoveHere(droneX - 1, droneY)) // checks move eligibility
				droneX = droneX - 1; // drone moves
			else
				dir = dir.nextDirection(); // changes direction
			break;
		case EAST:
			if (a.canMoveHere(droneX, droneY + 1))
				droneY = droneY + 1;
			else
				dir = dir.nextDirection();
			break;
		case SOUTH:
			if (a.canMoveHere(droneX + 1, droneY))
				droneX = droneX + 1;
			else
				dir = dir.nextDirection();
			break;
		case WEST:
			if (a.canMoveHere(droneX, droneY - 1))
				droneY = droneY - 1;
			else
				dir = dir.nextDirection();
			break;
		default:
			break;
		}
	}
}
