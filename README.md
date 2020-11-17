# CS5001-P4-VectorDrawing

## JUnit tests
### Compile tests

javac -cp ./tests/junit.jar:./tests/hamcrest.jar:./tests/:. tests/modelTests/*.java

### Run tests

#### Run a single test
java -cp ./tests/junit.jar:./tests/hamcrest.jar:./tests/:. org.junit.runner.JUnitCore modelTests.EllipseModelTest

#### Run the ModelTestSuite
java -cp ./tests/junit.jar:./tests/hamcrest.jar:./tests/:. org.junit.runner.JUnitCore modelTests.ModelTestSuite
