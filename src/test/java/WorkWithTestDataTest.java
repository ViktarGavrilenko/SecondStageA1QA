import com.example.utils.Const;
import com.example.modelsdatabase.TestTable;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.util.ArrayList;

import static com.example.utils.MySqlUtils.closeConnection;
import static org.testng.Assert.*;

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
            TestTable testBefore = new TestTable();
            TestTable testAfter = new TestTable();

            testBefore = testBefore.getTestById(idTest);
            TEST.simulateTest(idTest);
            testAfter = testAfter.getTestById(idTest);
            assertNotEquals(testBefore, testAfter, "Information in the test has not been updated");
        }
    }

    @AfterMethod
    public void afterTest(ITestResult result) {
        for (Integer idTest : ID_TESTS) {
            TEST.deleteTest(idTest);
            assertTrue(TEST.isDataDelete(idTest), "Data has not been removed from the table");
        }
        closeConnection();
    }
}
