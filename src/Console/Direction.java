package Console;

import java.util.Random;

/**
 * Direction Class
 * 
 * Direction class is responsible of managing the direction faced by the drone
 * 
 * The class has an automatically generated "next" direction based on the
 * existing one, as well as a random one
 * 
 * The directions are the 4 compass points N/W/S/E
 *
 * @version Week5
 * @author Andreas Paridis
 */

public enum Direction {
	NORTH, EAST, SOUTH, WEST;

	/**
	 * Randomly gets a value from one of the 4 in the enum
	 * 
	 */
	public static Direction getRandom() {

		Random rand = new Random(); // creates a randomiser
		return values()[rand.nextInt(values().length)]; // returns random direction value

	}

	/**
	 * Gets the next direction Checks if the current direction is the ordinal which
	 * then faces it back the first direction Else it changes to the next direction
	 * 
	 */
	public Direction nextDirection() {
		int size = Direction.values().length - 1;
		System.out.println(size);
		if (this.ordinal() == size)
			return values()[0];
		else
			return values()[this.ordinal() + 1];
	}
}
