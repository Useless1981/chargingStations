package position;

import java.util.LinkedList;
import java.util.List;

/**
 * Stores a 2D-position
 */
public class Position {
    protected double x;
    protected double y;

    /**
     * Private constructor for Position class
     * @param x int: x-coordinate
     * @param y int: y-coordinate
     */
    protected Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Method to get the x coordinate of the position
     * @return the x coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Method to get the y coordinate of the position
     * @return the y coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Factory method to return a new Position
     * @param x Double: x coordinate
     * @param y Double: y coordinate
     * @return Position: new Position
     */
    public static Position create(double x, double y) {
        return new Position(x, y);
    }

    /**
     * Returns a list of neighbor Positions of a given Position
     * @return List<Position>: List of neighbor Positions
     */
    public List<Position> neighbors() {
        List<Position> neighbors = new LinkedList<>();
        neighbors.add(Position.create(round(x - 0.1), round(y - 0.1)));
        neighbors.add(Position.create(round(x - 0.1), y));
        neighbors.add(Position.create(round(x - 0.1), round(y + 0.1)));
        neighbors.add(Position.create(x, round(y - 0.1)));
        neighbors.add(Position.create(x, round(y + 0.1)));
        neighbors.add(Position.create(round(x + 0.1), round(y - 0.1)));
        neighbors.add(Position.create(round(x + 0.1), y));
        neighbors.add(Position.create(round(x + 0.1), round(y + 0.1)));
        return neighbors;
    }

    /**
     * Calculates the quadratic distance between this Position and another Position and returns the result
     * @param p The other Position
     * @return the quadratic distance between this and the other Position
     */
    public double quadraticDistance(Position p) {
        return Math.pow(x - p.x, 2) + Math.pow(y - p.y, 2);
    }

    /**
     * Method to round the given double to a maximum of 1 decimal
     * @param a the double to round
     * @return a as rounded to 1 decimal
     */
    private double round(double a) {
        return Math.round(a * 10.0) / 10.0;
    }

    /**
     * toString()-method for the Position class
     * @return String: Position as String
     */
    @Override
    public String toString() {
        return "{" + x + ", " + y + '}';
    }
}
