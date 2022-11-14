import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Stores data for test scenarios
 */
public final class TestSettings {
    final int numberOfStationsToPlace;
    final Double[] base;
    final List<Double[]> homes;

    /**
     * Private constructor od settings object
     * @param numberOfStationsToPlace int
     * @param base Double[]
     * @param homes List<Double[]>
     */
    private TestSettings(int numberOfStationsToPlace, Double[] base, List<Double[]> homes) {
        this.numberOfStationsToPlace = numberOfStationsToPlace;
        this.base = base;
        this.homes = homes;
    }

    /**
     * Fabric method for TestSettings: loads as test scenario from a given .txt-file
     * @param sourceFile String: file path
     * @return TestSettings
     * @throws IOException when the file is not readable
     */
    public static TestSettings loadSettings(String sourceFile) throws IOException {
        String[] lines = readFile(sourceFile).split("\n");
        List<Double[]> homes = new LinkedList<>();
        for (int i = 2; i < lines.length; i++) {
            homes.add(positionFromString(lines[i]));
        }
        return new TestSettings(Integer.parseInt(String.valueOf(lines[0].charAt(0))), positionFromString(lines[1]), homes);
    }

    /**
     * Reads a file and returns its content as a String
     * @param fileName String: path and filename
     * @return String: file content
     * @throws IOException when the file is not readable
     */
    private static String readFile(String fileName) throws IOException {
        Path filePath = Path.of(fileName);
        return Files.readString(filePath);
    }

    /**
     * Translates a postion String into a Double[] representation
     * @param positionString String: position
     * @return Double[]: position as double array
     */
    private static Double[] positionFromString(String positionString) {
        String[] valuesAsString = positionString.split("\\s+");
        return new Double[] {Double.parseDouble(valuesAsString[0]), Double.parseDouble(valuesAsString[1])};
    }

    public String toString() {
        return "Stations to place: " + numberOfStationsToPlace + "\nBase: " + Arrays.toString(base) + "\nHomes:\n" + homes;
    }
}
