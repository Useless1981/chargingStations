import java.util.LinkedList;
import java.util.List;

/**
 * Stores a 2D-position
 */
public class Position {
    final Double x;
    final Double y;

    /**
     * Private constructor for Position class
     * @param x int: x-coordinate
     * @param y int: y-coordinate
     */
    protected Position(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Factory method to return a new Position without score
     * @param x Double: x coordinate
     * @param y Double: y coordinate
     * @return Position: new Position without a score
     */
    public static Position create(Double x, Double y) {
        return new Position(x, y);
    }

    /**
     * Returns a list of neighbor Positions of a given Position
     * @return List<Position>: List of be-neighbored Positions
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
