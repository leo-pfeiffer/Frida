package modelTests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class ModelTestSuiteRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(ModelTestSuite.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        if(result.wasSuccessful()) {
            System.out.println("Passed all " + result.getRunCount() +" Model JUnit tests!");
            System.exit(0);
        }
        else {
            System.out.println("Failed " + result.getFailureCount() + " out of " + result.getRunCount() +" Model JUnit tests!");
            System.exit(1);
        }
    }
}
