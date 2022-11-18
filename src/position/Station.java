package position;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a station and its nearest homes
 */
public class Station extends Position {

	public int id;
	public List<Position> nearestHomes;

	/**
	 * Private constructor for Station class
	 * @param x int: x-coordinate
	 * @param y int: y-coordinate
	 */
	private Station(Double x, Double y, int id) {
		super(x, y);
		this.id = id;
		nearestHomes = new ArrayList<>();
	}

	/**
	 * Factory method to return a new Station
	 * @param x Double: x coordinate
	 * @param y Double: y coordinate
	 * @param id
	 * @return new Station at the specified position
	 */
	public static Station create(Double x, Double y, int id) {
		return new Station(x, y, id);
	}

	/**
	 * Method to move the station to the specified position
	 * @param p the position to move to
	 */
	public void moveStation(Position p) {
		this.x = p.x;
		this.y = p.y;
	}

	/**
	 * String representation of a station
	 * @return station as string
	 */
	@Override
	public String toString() {
		return id + ". Position: {" + x + ", " + y + "} | Nearest homes: " + nearestHomes + "\n";
	}

}
