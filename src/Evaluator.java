import java.util.*;

/**
 * Evaluates a given scenario
 */
public class Evaluator {

    /*
     * TODO: Create Station class to make binding houses to stations easier?
     */

    final int scalingA = 5;
    final int scalingB = 2;
    final int scalingC = 3;

    final Random rand;
    final int numberOfStations;
    final double perfectDistribution;
    final Position base;
    final List<Position> homes;
    final double xMax, xMin, yMax, yMin;

    Map<Integer, Position> stations;

    /**
     * Private constructor for the evaluator
     * @param scenario the scenario to evaluate
     */
    private Evaluator(Scenario scenario) {
        numberOfStations = scenario.numberOfStationsToPlace;
        base = scenario.base;
        homes = scenario.homes;
        rand = new Random();
        perfectDistribution = homes.size() / (double) numberOfStations;
        System.out.println(perfectDistribution);
        xMax = homes.stream().mapToDouble(p -> p.x).max().orElse(0);
        xMin = homes.stream().mapToDouble(p -> p.x).min().orElse(0);
        yMax = homes.stream().mapToDouble(p -> p.y).max().orElse(0);
        yMin = homes.stream().mapToDouble(p -> p.y).min().orElse(0);
        stations = placeLoadingStations(true);
    }

    /**
     * Factory method to create an evaluator
     * @param scenario the scenario to evaluate
     * @return an evaluator with the scenario attached
     */
    public static Evaluator create(Scenario scenario) {
        return new Evaluator(scenario);
    }

    /**
     * Evaluates a given scenario
     */
    public void evaluate() {
        // If we have moved we continue searching as we are currently "climbing"
        boolean moved = true;
        // Just a counter for string representation later
        int searches = 0;
        while(moved) {
            // Variable to determine if we moved at least one station
            int moveCounter = 0;
            // Refresh the current house distribution
            Map<Integer, List<Position>> currentDistribution = findNearestHomes();
            // Just a counter for string representation later
            double totalScore = 0;
            double totalScoreA = 0;
            double totalScoreB = 0;
            double totalScoreC = 0;
            // We iterate each station once each step and look for a better position around each station
            for(var station : stations.entrySet()) {
                List<Position> neighbors = station.getValue().neighbors();
                // Scoring the current station position
                double stationScore = getTotalScore(station.getValue(), currentDistribution.get(station.getKey()));
                // Init the best neighbor as -1 as we have not found a better neighbor yet
                int bestNeighbor = -1;
                // We score each neighbor and compare the values with each other and take the lowest score (since here low is better)
                for(int i = 0; i < neighbors.size(); i++) {
                    double neighborScore = getTotalScore(neighbors.get(i), currentDistribution.get(station.getKey()));
                    if(neighborScore < stationScore) {
                        stationScore = neighborScore;
                        bestNeighbor = i;
                        moveCounter++;
                    }
                }
                // If we found a better neighbor position we move the station
                if(bestNeighbor != -1) stations.put(station.getKey(), neighbors.get(bestNeighbor));
                // Only used for string representation later
                totalScore += stationScore;
                totalScoreA += getScoreA(station.getValue(), currentDistribution.get(station.getKey()));
                totalScoreB += getScoreB(currentDistribution.get(station.getKey()));
                totalScoreC += getScoreC(station.getValue());
            }
            searches++;

            System.out.println("Search number " + searches + ":");
            System.out.println("A Score: " + totalScoreA);
            System.out.println("B Score: " + totalScoreB);
            System.out.println("C Score: " + totalScoreC);
            System.out.println("Total score: " + totalScore);
            System.out.println("Station positions: " + stations.values());
            //System.out.println("Current distribution: " + currentDistribution);
            System.out.println();

            if(moveCounter == 0) {
                moved = false;
            }
        }
    }

    /**
     * Method to determine which homes are closest to which station
     * @return a map with station id's as keys and a list of positions for the nearest homes to that station
     */
    private Map<Integer, List<Position>> findNearestHomes() {
        Map<Integer, List<Position>> belongingMap = new HashMap<>();
        // We check for each home the distance to each station and attach the house position to the nearest station id
        for(Position home : homes) {
            int station = 0;
            double distanceBefore = 0;

            for(int i = 0; i < numberOfStations; i++) {
                double distance = euclidean(home, stations.get(i));
                if(i == 0 || distanceBefore > distance) {
                    distanceBefore = distance;
                    station = i;
                }
            }

            List<Position> belongingList = belongingMap.get(station);
            if(belongingList == null) {
                belongingList = new ArrayList<>();
                belongingList.add(home);
                belongingMap.put(station, belongingList);
            } else {
                belongingList.add(home);
            }
        }
        return belongingMap;
    }

    /**
     * Returns a score based on euclidean distance from the station to the nearest houses
     * @param p the stations position
     * @param homeList the stations nearest neighbors
     * @return the A score of the position
     */
    private double getScoreA(Position p, List<Position> homeList) {
        if(homeList == null) return 9999.0;
        double sum = 0;
        for(var home : homeList) {
            sum += euclidean(home, p);
        }
        return sum / homeList.size() * scalingA;
    }

    /**
     * Returns a score based on how close the number of near houses is to a perfect distribution
     * @param homeList the stations nearest neighbors
     * @return the B score of the position
     */
    private double getScoreB(List<Position> homeList) {
        return Math.abs((homeList == null ? 0 : homeList.size() - perfectDistribution)) * scalingB;
    }

    /**
     * Returns a score based on the euclidean distance from the given station to the base station
     * @param p the stations position
     * @return the C score of the position
     */
    private double getScoreC(Position p) {
        return euclidean(p, base) * scalingC;
    }

    /**
     * Returns the total score of the given station
     * @param p the stations position
     * @param homeList the stations nearest neighbors
     * @return the total score of the position
     */
    private double getTotalScore(Position p, List<Position> homeList) {
        return getScoreA(p, homeList) + getScoreB(homeList) + getScoreC(p);
    }

    /**
     * Initially places the loading stations
     * @param randomStart used to let the initial starting position be randomized or not
     * @return list of positions where the stations got placed
     */
    private Map<Integer, Position> placeLoadingStations(boolean randomStart) {
        Map<Integer, Position> loadingStationPositions = new HashMap<>();
        for(int i = 0; i < numberOfStations; i++) {
            double x = randomStart ? nextDouble(xMin, xMax) : 0.0;
            double y = randomStart ? nextDouble(yMin, yMax) : 0.0;
            loadingStationPositions.put(i, Position.create(x, y));
        }
        return loadingStationPositions;
    }

    /**
     * Method to generate a random double in a specified range
     * @param min the minimum value that the double can be
     * @param max the maximum value that the double can be
     * @return a value between min and max rounded to 1 decimal
     */
    private double nextDouble(double min, double max) {
        return Math.round(((rand.nextDouble() * (max - min)) + min) * 10.0) / 10.0;
    }

    /**
     * Calculates the euclidean distance between two Positions and returns the result
     * @param a The first Position
     * @param b The second Position
     * @return the euclidean distance between a and b
     */
    private double euclidean(Position a, Position b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }
}
