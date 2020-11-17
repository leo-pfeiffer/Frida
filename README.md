# CS5001-P4-VectorDrawing

## JUnit tests
### Compile tests

`javac -cp ./tests/junit.jar:./tests/hamcrest.jar:./tests/:. src/model/*.java tests/modelTests/*.java`


### Run tests

#### Run a single test
`java -cp ./tests/junit.jar:./tests/hamcrest.jar:./tests/:./src/:. org.junit.runner.JUnitCore modelTests.EllipseModelTest`

#### Run the ModelTestSuite
`java -cp ./tests/junit.jar:./tests/hamcrest.jar:./tests/:./src/:. org.junit.runner.JUnitCore modelTests.ModelTestSuite`

### Remove compiled class files:
Run from project root:
`find . -name "*.class" -type f -delete`
