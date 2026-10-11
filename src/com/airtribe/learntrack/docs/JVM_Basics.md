## JVM Basics

## JDK, JRE and JVM

The easiest way I found to remember these is that each one sits inside the next.

JVM (Java Virtual Machine) - is the part that actually runs a Java program. It reads compiled ".class" files and executes them on the machine. It also handles things like memory management and garbage collection.

JRE (Java Runtime Environment) - is the JVM plus the standard libraries that programs need at runtime (things like "java.util", "java.time", "java.io").

JDK (Java Development Kit) - is the JRE plus the developer tools: the compiler ("javac"), the debugger, "jar", "javadoc" and so on. If we want to write and compile Java, you need the JDK.

So roughly: JDK = JRE + dev tools, and JRE = JVM + libraries.

(Note: from Java 11 onwards there isn't a separate JRE download any more, just installing the JDK is enough.)

## What is bytecode?

When I run "javac Main.java", the compiler turn my code into **bytecode**, which ends up in a ".class" file.

Bytecode is not human readable format like Java source, but it's also not tied to any specific operating system or CPU. Only the JVM understands it. When the program runs, the JVM reads the bytecode and either interprets it or compiles the frequently used parts into native machine code on the fly (that's the JIT compiler), which is why Java can still be quite fast.

## What does "write once, run anywhere" mean?

Because Java compiles to bytecode instead of to native code, the same ".class" files can run on any machine that has a JVM. Oracle and others make a JVM for Windows, macOS, Linux and so on, and each one knows how to translate bytecode for its own platform. So the JVM is the piece that's platform specific, not my program.
