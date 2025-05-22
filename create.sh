#!/bin/bash

# Create root directory structure
mkdir -p src/main/java/com/wiftwift/{functions/{logarithmic,trigonometric},utils}
mkdir -p src/test/java/com/wiftwift/{functions/{logarithmic,trigonometric},utils}
mkdir -p src/main/resources
mkdir -p src/test/resources

touch build.gradle
touch settings.gradle

# Create main function classes
touch src/main/java/com/wiftwift/Main.java
touch src/main/java/com/wiftwift/SystemSolver.java

# Create trigonometric function classes
touch src/main/java/com/wiftwift/functions/trigonometric/Sin.java
touch src/main/java/com/wiftwift/functions/trigonometric/Cos.java
touch src/main/java/com/wiftwift/functions/trigonometric/Tan.java
touch src/main/java/com/wiftwift/functions/trigonometric/Csc.java
touch src/main/java/com/wiftwift/functions/trigonometric/TrigFunctions.java

# Create logarithmic function classes
touch src/main/java/com/wiftwift/functions/logarithmic/Ln.java
touch src/main/java/com/wiftwift/functions/logarithmic/Log2.java
touch src/main/java/com/wiftwift/functions/logarithmic/Log3.java
touch src/main/java/com/wiftwift/functions/logarithmic/Log5.java
touch src/main/java/com/wiftwift/functions/logarithmic/LogFunctions.java

# Create utility classes
touch src/main/java/com/wiftwift/utils/CsvWriter.java

# Create test classes
touch src/test/java/com/wiftwift/SystemSolverTest.java
touch src/test/java/com/wiftwift/functions/trigonometric/SinTest.java
touch src/test/java/com/wiftwift/functions/trigonometric/CosTest.java
touch src/test/java/com/wiftwift/functions/trigonometric/TanTest.java
touch src/test/java/com/wiftwift/functions/trigonometric/CscTest.java
touch src/test/java/com/wiftwift/functions/logarithmic/LnTest.java
touch src/test/java/com/wiftwift/functions/logarithmic/Log2Test.java
touch src/test/java/com/wiftwift/functions/logarithmic/Log3Test.java
touch src/test/java/com/wiftwift/functions/logarithmic/Log5Test.java
touch src/test/java/com/wiftwift/utils/CsvWriterTest.java

echo "Project structure created successfully."
