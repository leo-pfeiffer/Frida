package modelTests;

import model.StarModel;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses(
        {EllipseModelTest.class, LineModelTest.class,
        RectangleModelTest.class, ParallelogramModelTest.class,
        HexagonModelTest.class, TriangleModelTest.class, StarModelTest.class})
public class ModelTestSuite {
    // nothing
}
