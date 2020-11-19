package modelTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/** Test suite for all JUnit tests.
 * @author 190026921 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
        {EllipseModelTest.class, LineModelTest.class,
        RectangleModelTest.class, ParallelogramModelTest.class,
        HexagonModelTest.class, TriangleModelTest.class, StarModelTest.class,
        WriteReadFileTest.class})
public class ModelTestSuite { }
