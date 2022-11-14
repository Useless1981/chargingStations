import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try {
            TestSettings testSettings = TestSettings.loadSettings("2022-isys2-data/test1.txt");
            System.out.println(testSettings);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}