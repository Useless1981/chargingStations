import java.util.LinkedList;
import java.util.List;

public class LocalSearcher {
    int currentPosition;
    final Scenario scenario;


    public LocalSearcher(int startingPosition, Scenario scenario) {
        this.currentPosition = startingPosition;
        this.scenario = scenario;
    }

    public Position searchLocalMax() {
        return Position.unscored(0.0, 0.0);
    }

    private List<Position> scoreNeighbor() { return new LinkedList<>(); }
}
