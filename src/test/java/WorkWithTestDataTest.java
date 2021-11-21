import com.example.modelsdatabase.Const;
import com.example.modelsdatabase.TestTable;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.util.ArrayList;

public class WorkWithTestDataTest extends Const {
    private static ArrayList<Integer> ID_TESTS = new ArrayList<>();
    private static final TestTable TEST = new TestTable();

    @BeforeMethod
    protected void beforeMethod() {
        ResultSet resultSet = TEST.getListWithTwoNumbersRepeating();
        ID_TESTS = TEST.copyDataWithNewProjectAndAuthor(resultSet);
    }

    @Test(description = "Working with test data")
    public void testWorkWithTestData() {
        for (Integer idTest : ID_TESTS) {
            TEST.simulateTest(idTest);
        }
    }

    @AfterMethod
    public void afterTest(ITestResult result) {
        for (Integer idTest : ID_TESTS) {
            TEST.deleteTest(idTest);
        }
    }
}
