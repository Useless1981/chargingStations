import java.util.List;

/**
 * Evaluates a given position
 */
public class PositionEvaluator {
    /**
     * Evaluates a given position with a score determent be the given scenario
     * @param position Position: position to evaluate
     * @param scenario Scenario: scenario used to evaluate the given position
     * @return int: evaluated score of given position
     */
    public static int evaluatePosition(Position position, Scenario scenario) { return -1; } //TODO: implement scoring positions
    // quadratischer Abstand zur basisstation (Normalisiert) + quadratische Abstaende homes/anzahl + -1*(verhaeltnis von bestverteiltem einzugsbereich zu tatsaechlich)

    public static List<Position> evaluateAll(List<Position> positions, Scenario scenario) {
        return positions.stream().map(position -> Position.scorePostion(position, evaluatePosition(position, scenario))).toList();
    }
}
