package Console;

/**
 * CosnoleCanvas Class
 * 
 * This is the actual area the drones can be displayed and move on
 * 
 * NOTE: X POSITIONS ARE VERTICAL Y POSITIONS ARE HORIZONTAL
 *
 * @version Week5
 * @author Andreas Paridis
 */
public class ConsoleCanvas {
	// movable area and its dimensions
	private char[][] canvas;
	private int canvasX;
	private int canvasY;

	/**
	 * CONSTRUCTOR
	 * 
	 * @param canvas X dimension based on the arena created
	 * @param canvas Y dimension based on the arena created
	 * 
	 *               Takes care of applying the appropriate character to indicate
	 *               the barriers of the arena the rest are filled with spaces
	 * 
	 */
	ConsoleCanvas(int arrayX, int arrayY) {
		canvasX = arrayX;
		canvasY = arrayY;
		canvas = new char[arrayX][arrayY];
		for (int i = 0; i < canvasX; i++) {
			for (int j = 0; j < canvasY; j++) {
				canvas[i][j] = ' ';
				if (i == 0) {
					canvas[i][j] = '#';
				}
				if (j == 0) {
					canvas[i][j] = '#';
				}
				if (j == arrayY - 1) {
					canvas[i][j] = '#';
				}
				if (i == arrayX - 1) {
					canvas[i][j] = '#';
				}

			}
		}
	};

	/**
	 * Displays the drone on the canvas
	 * 
	 * @param The drone's X coordinate
	 * @param The drone's Y coordinate
	 * @param The drone's appropriate display character
	 * 
	 *            In order for the drones to be displayed properly the drone's
	 *            coordinates must be incremented by one since we don't want our
	 *            drones to be displayed inside the borders
	 */
	public void showIt(int droneX, int droneY, char ch) {
		canvas[droneX + 1][droneY + 1] = ch;
	}

	/**
	 * Displays canvas information
	 * 
	 * @return canvas information to string form
	 *
	 */
	public String toString() {
		String res = "";
		for (int i = 0; i < canvasX; i++) {
			for (int j = 0; j < canvasY; j++) {
				res = res + canvas[i][j] + " ";
			}
			res = res + "\n";
		}
		return res;
	}
}
