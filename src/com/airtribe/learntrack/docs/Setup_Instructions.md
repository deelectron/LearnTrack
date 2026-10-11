Setup Instructions

JDK version used - 21 and the project is also set to compile with Java 21 compliance.

Hello World test

HelloWorld.java:

public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}

Then from the folder where the file was saved:

javac HelloWorld.java
java HelloWorld

What happens here:

- "javac HelloWorld.java" compiles the source file and creates "HelloWorld.class" in the same folder. That ".class" file is the bytecode.
- "java HelloWorld" starts the JVM, loads the class and runs its "main" method. Note that you give the class name here, not the file name, so no ".class" at the end.

Output:

Hello World
