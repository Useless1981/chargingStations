import java.io.IOException;

public class Main {
    public static void main(String[] args) {
//        onlyOne(2);
//        allTestsOnce();
        allTestsMulti(30);
    }

    public static void onlyOne(int i) {
        try {
            Scenario s = Scenario.loadSettings("2022-isys2-data/test" + i + ".txt");
            Evaluator e = Evaluator.create(s);
            e.print = true;
            Score score = e.evaluate();
            e.toCsv("testPositions" + i + ".csv");
            score.toCsv("testScore" + i + ".csv");
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void allTestsMulti(int amount) {
        for(int i = 0; i < 5; i++) {
            try {
                Scenario s = Scenario.loadSettings("2022-isys2-data/test" + (i + 1) + ".txt");
                Evaluator e = MultiEvaluator.randomStarts(s, amount);
                Score score = e.evaluate();
                e.toCsv("testPositions" + (i + 1) + ".csv");
                score.toCsv("testScore" + (i + 1) + ".csv");
            } catch(IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void allTestsOnce() {
        for(int i = 0; i < 5; i++) {
            try {
                Scenario s = Scenario.loadSettings("2022-isys2-data/test" + (i + 1) + ".txt");
                Evaluator e = Evaluator.create(s);
                Score score = e.evaluate();
                e.toCsv("testPositions" + (i + 1) + ".csv");
                score.toCsv("testScore" + (i + 1) + ".csv");
            } catch(IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}