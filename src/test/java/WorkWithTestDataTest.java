import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import com.example.databasemodels.TestTable;
import com.example.databasequeries.TestTableQueries;
import com.example.utils.DatabaseConst;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static com.example.utils.MySqlUtils.closeConnection;
import static com.example.utils.TestUtils.simulateTest;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

public class WorkWithTestDataTest extends DatabaseConst {
    protected static final ISettingsFile TEST_DATA_FILE = new JsonSettingsFile("testData.json");
    protected static final String NAME_AUTHOR_PROJECT = TEST_DATA_FILE.getValue("/name").toString();
    private static final String AUTHOR_LOGIN = TEST_DATA_FILE.getValue("/login").toString();
    private static final String AUTHOR_EMAIL = TEST_DATA_FILE.getValue("/email").toString();

    private static ArrayList<Integer> ID_TESTS = new ArrayList<>();
    private static final TestTableQueries TEST = new TestTableQueries();

    @BeforeMethod
    protected void beforeMethod() {
        ArrayList<TestTable> listTests =
                TEST.getListWithTwoNumbersRepeating(NAME_AUTHOR_PROJECT, AUTHOR_LOGIN, AUTHOR_EMAIL);
        ID_TESTS = TEST.copyDataWithNewProjectAndAuthor(listTests);
    }

    @Test(description = "Working with test data")
    public void testWorkWithTestData() {
        for (Integer idTest : ID_TESTS) {
            TestTableQueries testTableMethods = new TestTableQueries();
            TestTable testBefore = testTableMethods.getTestById(idTest);
            simulateTest(idTest);
            TestTable testAfter = testTableMethods.getTestById(idTest);
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