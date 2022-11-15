import java.util.LinkedList;
import java.util.List;

/**
 * Stores a 2D-position
 */
public final class Position {
    final Double x;
    final Double y;
    final int score;

    /**
     * Private constructor for Position class
     * @param x int: x-coordinate
     * @param y int: y-coordinate
     * @param score int: score of the Position
     */
    private Position(Double x, Double y, int score) {
        this.x = x;
        this.y = y;
        this.score = score;
    }

    public Double x() { return x; }

    public Double y() { return y; }

    public int score() { return score; }

    /**
     * Fabric method to return a new Position with score
     * @param position Position: Position to score
     * @param score int: score value
     * @return Position: a new Position with score
     */
    public static Position scorePostion(Position position, int score) {
        return new Position(position.x(), position.y(), score);
    }

    /**
     * Fabric method to return a new Position without score
     * @param x Double: x coordinate
     * @param y Double: y coordinate
     * @return Position: new Position without a score
     */
    public static Position unscored(Double x, Double y) {
        return new Position(x, y, 0);
    }

    /**
     * Returns a list of be-neighbored Positions to a given Position
     * @param position Position: Position of which to get its neighbors
     * @return List<Position>: List of be-neighbored Positions
     */
    public static List<Position> neighbors(Position position) {
        List<Position> neighbors = new LinkedList<>();
        neighbors.add(Position.unscored((position.x() - 0.1), (position.y()) - 0.1));
        neighbors.add(Position.unscored((position.x() - 0.1), position.y()));
        neighbors.add(Position.unscored((position.x() - 0.1), (position.y()) + 0.1));
        neighbors.add(Position.unscored(position.x(), (position.y()) - 0.1));
        neighbors.add(Position.unscored(position.x(), (position.y()) + 0.1));
        neighbors.add(Position.unscored((position.x() + 0.1), position.y() - 0.1));
        neighbors.add(Position.unscored((position.x() + 0.1), position.y()));
        neighbors.add(Position.unscored((position.x() + 0.1), (position.y()) + 0.1));
        return neighbors;
    }

    /**
     * toString()-method for Position class
     * @return String: Position as String
     */
    @Override
    public String toString() { return "{" + x + ", " + y + '}'; }
}
