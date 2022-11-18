import position.Position;
import position.Station;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Evaluates a given scenario
 */
public class Evaluator {

    private final int numberOfStations;
    private final List<Position> homes;
    private final List<Station> stations;
    private final List<Position> initialStations;
    private final Score score;
    public boolean print = false;

    /**
     * Private constructor for the evaluator
     * @param scenario the scenario to evaluate
     */
    private Evaluator(Scenario scenario) {
        numberOfStations = scenario.numberOfStationsToPlace;
        homes = scenario.homes;
        stations = placeLoadingStationsRandom();
        initialStations = stations.stream().map(s -> Position.create(s.getX(), s.getY())).toList();
        findNearestHomes();
        score = Score.create(stations, scenario);
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
     * @return the calculated score of the scenario
     */
    public Score evaluate() {
        // If we have moved we continue searching as we are currently "climbing"
        boolean moved = true;
        while(moved) {
            // Variable to determine if we moved at least one station
            int moveCounter = 0;
            // We iterate each station once each step and look for a better position around each station
            for(Station station : stations) {
                findNearestHomes();
                List<Position> neighbors = station.neighbors();
                // The score of the current position
                double stationTotalScore = score.calculateScores(stations);
                // We are saving the current position to reset the position if we haven't found a better neighbor
                Position startingPosition = Position.create(station.getX(), station.getY());
                // Init the best neighbor as null as we have not found a better neighbor yet
                Position bestNeighbor = null;
                // We score each neighbor and compare the values with each other and take the lowest score (since here low is better)
                for(Position neighbor : neighbors) {
                    station.moveStation(neighbor);
                    findNearestHomes();
                    if(stationTotalScore > score.calculateScores(stations)) {
                        bestNeighbor = neighbor;
                        moveCounter++;
                    }
                }
                // If we found a better neighbor position we move the station
                station.moveStation(Objects.requireNonNullElse(bestNeighbor, startingPosition));
            }
            if(print) printRound();
            if(moveCounter == 0) moved = false;
        }
        return score;
    }

    private void printRound() {
        System.out.println("----------------------------------------------------------------------------------");
        System.out.println(score);
        System.out.println("Station positions: \n" + stations + "\n");
    }

    /**
     * Method to determine which homes are closest to which station
     */
    private void findNearestHomes() {
        // We check for each home the distance to each station and attach the house position to the nearest station id
        for(Station station : stations) {
            station.nearestHomes.clear();
        }
        for(Position home : homes) {
            double distanceBefore = Double.MAX_VALUE;
            int id = 0;
            for(Station station : stations) {
                double distance = station.quadraticDistance(home);
                if(distanceBefore > distance) {
                    distanceBefore = distance;
                    id = station.id;
                }
            }
            stations.get(id).nearestHomes.add(home);
        }
    }

    /**
     * Initially places the loading stations at 0.0
     * @return list of positions where the stations got placed
     */
    private List<Station> placeLoadingStations() {
        List<Station> loadingStationPositions = new ArrayList<>();
        for(int i = 0; i < numberOfStations; i++) {
            loadingStationPositions.add(Station.create(0.0, 0.0, i));
        }
        return loadingStationPositions;
    }

    /**
     * Initially places the loading stations at random locations in the boundaries of the homes location
     * @return list of positions where the stations got placed
     */
    private List<Station> placeLoadingStationsRandom() {
        Random rand = new Random();
        double xMax = homes.stream().mapToDouble(Position::getX).max().orElse(0);
        double xMin = homes.stream().mapToDouble(Position::getX).min().orElse(0);
        double yMax = homes.stream().mapToDouble(Position::getY).max().orElse(0);
        double yMin = homes.stream().mapToDouble(Position::getY).min().orElse(0);
        List<Station> loadingStationPositions = new ArrayList<>();
        for(int i = 0; i < numberOfStations; i++) {
            double x = Math.round(((rand.nextDouble() * (xMax - xMin)) + xMin) * 10.0) / 10.0;
            double y = Math.round(((rand.nextDouble() * (yMax - yMin)) + yMin) * 10.0) / 10.0;
            loadingStationPositions.add(Station.create(x, y, i));
        }
        return new ArrayList<>(loadingStationPositions);
    }

    /**
     * Encodes and writes a results to a csv file
     * @param fileName String: Filename
     */
    public void toCsv(String fileName) {

        try {
            StringBuilder toWrite = new StringBuilder("InitialX, InitialY, FinalX, FinalY\n");
            for(int i = 0; i < numberOfStations; i++) {
                Position initStations = initialStations.get(i);
                Position finalStations = stations.get(i);
                toWrite.append(
                        initStations.getX()).append(", ")
                        .append(initStations.getY()).append(", ")
                        .append(finalStations.getX()).append(", ")
                        .append(finalStations.getY()).append("\n");
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(toWrite.toString());
            writer.close();
        } catch(IOException e) {
            System.out.println("Couldn't write to file.");
        }
    }
}
