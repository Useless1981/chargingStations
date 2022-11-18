import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Scenario scenario = Scenario.loadSettings("2022-isys2-data/test3.txt");
            Evaluator e = Evaluator.create(scenario);
            e.evaluate();
//            System.out.println(scenario);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}