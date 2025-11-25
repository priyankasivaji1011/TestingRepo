

import com.aventstack.extentreports.Status;
import com.evry.bdd.utils.ExtentTestManager;

public class Log {

    public static void info(String message) {
        ExtentTestManager.getTest().log(Status.INFO, message);
    }

    public static void pass(String message) {
        ExtentTestManager.getTest().log(Status.PASS, message);
    }

    public static void fail(String message) {
        ExtentTestManager.getTest().log(Status.FAIL, message);
    }
}
