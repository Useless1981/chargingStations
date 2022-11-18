import position.Station;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Class to represent a given score
 */
public class Score {

	final double scalingA = 2;
	final double scalingB = 5;
	final double scalingC = 1;

	private double scoreA;
	private double scoreB;
	private double scoreC;
	private double totalScore;

	private final double initialScoreA;
	private final double initialScoreB;
	private final double initialScoreC;
	private final double initialTotalScore;

	private final Scenario scenario;

	/**
	 * Private constructor for the Score class
	 */
	private Score(List<Station> stations, Scenario scenario) {
		this.scenario = scenario;
		calculateScores(stations);
		initialScoreA = scoreA;
		initialScoreB = scoreB;
		initialScoreC = scoreC;
		initialTotalScore = totalScore;
	}

	/**
	 * Factory method to return a new Station with the worst score possible
	 * @param scenario the scenario which to calculate the score to
	 * @return new Score with the specified values
	 */
	public static Score create(List<Station> stations, Scenario scenario) {
		return new Score(stations, scenario);
	}

	/**
	 * Returns a score based on quadratic distance from the stations to the nearest houses
	 */
	private double calcScoreA(List<Station> stations) {
		double sum = 0;
		for(var s : stations) {
			double sumStation = 0;
			for(var home : s.nearestHomes) {
				sumStation += s.quadraticDistance(home);
			}
			if(s.nearestHomes.size() != 0) sum += sumStation / s.nearestHomes.size();
				// If a station has 0 nearest neighbors we punish it by increased the total score by times 2
			else sum *= 2;
		}
		return sum * scalingA;
	}

	/**
	 * Returns a score based on how close the number of near houses is to a perfect distribution
	 */
	private double calcScoreB(List<Station> stations) {
		double sum = 0;
		for(var s : stations) {
			sum += Math.abs((s.nearestHomes.size() - scenario.homes.size() / scenario.numberOfStationsToPlace));
		}
		return sum * scalingB;
	}

	/**
	 * Returns a score based on the quadratic distance from all station to the base station
	 */
	private double calcScoreC(List<Station> stations) {
		double sum = 0;
		for(var s : stations) {
			sum += s.quadraticDistance(scenario.base);
		}
		return sum * scalingC;
	}

	/**
	 * Recalculates the score and returns the calculated score
	 * @param stations the list of the stations used to calculate a score
	 * @return the total score that got calculated
	 */
	public double calculateScores(List<Station> stations) {
		scoreA = calcScoreA(stations);
		scoreB = calcScoreB(stations);
		scoreC = calcScoreC(stations);
		totalScore = scoreA + scoreB + scoreC;
		return totalScore;
	}

	/**
	 * Returns the total score for that scenario
	 * @return the total score of the scenario
	 */
	public double getTotalScore() {
		return totalScore;
	}

	/**
	 * String representation of a score
	 * @return score as string
	 */
	@Override
	public String toString() {
		return "A score: " + scoreA + "\nB score: " + scoreB + "\nC score: " + scoreC + "\nTotal score: " + totalScore;
	}

	/**
	 * Encodes and writes a results to a csv file
	 * @param fileName String: Filename
	 */
	public void toCsv(String fileName) {
		try {
			String toWrite =
					  "InitialScoreA," + initialScoreA + "\n"
					+ "InitialScoreB," + initialScoreB + "\n"
					+ "InitialScoreC," + initialScoreC + "\n"
					+ "InitialTotalScore," + initialTotalScore + "\n"
					+ "scoreA," + scoreA + "\n"
					+ "scoreB," + scoreB + "\n"
					+ "scoreC," + scoreC + "\n"
					+ "totalScore," + totalScore + "\n";

			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(toWrite);
			writer.close();
		} catch(IOException e) {
			System.out.println("Couldn't write to file.");
		}
	}

}
