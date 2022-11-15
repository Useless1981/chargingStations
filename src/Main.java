import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try {
            Scenario scenario = Scenario.loadSettings("2022-isys2-data/test2.txt");
            System.out.println(scenario);
            System.out.println(Position.neighbors(Position.unscored(0.0, 0.0)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}