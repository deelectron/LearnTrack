# LearnTrack

LearnTrack is a small console app for managing students, courses and enrollments, written in Core Java.

Everything runs from a menu in the terminal and all data is kept in memory, so nothing is saved after you exit.

## What it can do

**Student Management**
- Add a student (email is optional)
- View all students
- Search for a student by ID
- Deactivate a student (they're marked inactive, not deleted)

**Course Management**
- Add a course
- View all courses
- Activate or deactivate a course

**Enrollment Management**
- Enroll a student in a course
- View all enrollments for a student
- Mark an enrollment as completed or cancelled

Wrong menu options, non-numeric input and IDs that don't exist are handled with a message instead of crashing the program.

## Project structure

```
src/com/airtribe/learntrack/
├── Main.java          -> console menu (UI)
├── entity/            -> Person, Student, Trainer, Course, Enrollment
├── enums/             -> EnrollmentStatus
├── service/           -> StudentService, CourseService, EnrollmentService
├── exception/         -> EntityNotFoundException, InvalidInputException
├── util/              -> IdGenerator
└── docs/              -> Setup_Instructions.md, JVM_Basics.md, Design_Notes.md
```

## Requirements

- JDK 21 

See `docs/Setup_Instructions.md` for details.

## How to compile and run

### Option 1: Eclipse

1. *File → Import → Existing Projects into Workspace* and select the `LearnTrack` folder.
2. Open `src/com/airtribe/learntrack/Main.java`.
3. Right click → *Run As → Java Application*.

### Option 2: Command line (Windows)

From the project root (`LearnTrack` folder):

```
dir /s /b src\*.java > sources.txt
javac -d out @sources.txt
java -cp out com.airtribe.learntrack.Main
```

The first line collects all the `.java` file paths into `sources.txt`, the second compiles them into an `out` folder, and the third runs the `Main` class.

On macOS / Linux the same thing looks like:

```
javac -d out $(find src -name "*.java")
java -cp out com.airtribe.learntrack.Main
```

## Sample run

```
=== LearnTrack ===
1. Student Management
2. Course Management
3. Enrollment Management
0. Exit
Choose option: 1

--- Student Management ---
1. Add student
2. View students
3. Search student by ID
4. Deactivate student
0. Back
Choose option: 1
First name: Rahul
Last name: Sharma
Email (optional): rahul@example.com
Batch: Java-Oct
Student added. ID: 1
```

## More docs

- `docs/Setup_Instructions.md` – JDK setup and Hello World check
- `docs/JVM_Basics.md` – JDK vs JRE vs JVM, bytecode, write once run anywhere
- `docs/Design_Notes.md` – why ArrayList, where static is used, how inheritance is used
