# Design Notes

A few notes on why I built LearnTrack the way I did.

## Why ArrayList instead of an array

The main reason is that I don't know in advance how many students, courses or enrollments there will be. An array needs a fixed size when you create it, so I would either have to guess a big number (and waste space) or write my own code to create a bigger array and copy everything over when it fills up.

`ArrayList` does that resizing for me. I just call `add()` and it grows as needed. It also gives me useful methods out of the box like `isEmpty()`, `size()` and removing items through an `Iterator`, which I use in `StudentService.removeStudent()`. With a plain array I would have had to shift elements around manually after a removal.

Each service keeps its own list:

- `StudentService` has `List<Student> students`
- `CourseService` has `List<Course> courses`
- `EnrollmentService` has `List<Enrollment> enrollments`

I declared them as `List` and created them as `ArrayList`. That way the rest of the code only depends on the `List` interface, and if I ever wanted to switch to a different list type later, I'd only change one line.

Since everything is in memory, the data is gone when the program closes. That's fine for this stage of the project.

## Where I used static members and why

**`IdGenerator` (util package)**
This is the main place. It has `private static int studentIdCounter` and `courseIdCounter`, plus static methods `getNextStudentId()` and `getNextCourseId()`. The counters need to be static because there should be exactly one counter shared across the whole application. If they were instance fields, every new `IdGenerator` object would start from 0 again and I'd get duplicate IDs.

I also made the class `final` and gave it a private constructor. It's a utility class with only static methods, so there's no reason for anyone to create an object of it or extend it.

**`Main` (ui layer)**
The `Scanner` and the three service objects are `private static final` fields in `Main`. The menu methods are all static (because they are called from the static `main` method), so they need static fields to share the same scanner and the same services. This also makes sure all menus work on the same data. If each menu created its own `StudentService`, a student added in one place wouldn't show up in another.

The helper methods `readInt()`, `readLong()` and `readText()` are static for the same reason, and they keep the input parsing and the try-catch for `NumberFormatException` in one place instead of repeating it in every menu.

**Where I didn't use static**
The lists inside the services are normal instance fields. The data belongs to the service object, and keeping it non-static means I could create a fresh service (for example in a test) without old data leaking in. The enrollment ID counter is also an instance field inside `EnrollmentService` because only that service creates enrollments. Looking back, I could move it into `IdGenerator` as well so all the ID logic lives together.

## Where I used inheritance and what I gained

I created a `Person` base class with the fields every person in the system has: `id`, `firstName`, `lastName` and `email`, along with their getters and setters and a `getDisplayName()` method.

`Student` and `Trainer` both extend `Person`:

- `Student` adds `batch` and `active`.
- `Trainer` adds `specialization`. It isn't used in the menus yet, but it shows how another type of person can reuse the same base class.

Both subclasses call `super(id, firstName, lastName, email)` in their constructors, so the common fields are set in one place. `Student` also has an overloaded constructor without the email, which passes `null` to `super` for the email.

Both classes override `getDisplayName()`. `Person` just returns the first and last name, while `Student` adds the batch and `Trainer` adds the specialization. Each override still calls `super.getDisplayName()` so the name format itself is only written once.

What I got out of this:

- **Less repeated code.** The four common fields and their getters/setters are written once in `Person` instead of being copied into every class.
- **Easier changes.** If I need to add something like a phone number for everyone, I add it to `Person` and both `Student` and `Trainer` get it.
- **Polymorphism.** A `Person` reference can hold a `Student` or a `Trainer`, and calling `getDisplayName()` runs the right version depending on the actual object. That's the main thing the overriding example demonstrates.

## Other design decisions

- **Layers kept separate.** Entities only hold data, services hold the logic (adding, finding, updating), and `Main` only shows menus, reads input and calls the services. `Main` doesn't touch the lists directly.
- **Soft delete for students.** Deactivating a student sets `active = false` instead of removing them, so their enrollment history is still there. I kept a `removeStudent()` method too, since the brief asked for one.
- **Custom exceptions.** `EntityNotFoundException` is thrown when a student or course ID doesn't exist, and `InvalidInputException` when an ID can't be parsed. `Main` catches them and prints a readable message instead of crashing.
- **Enum for enrollment status.** I used `EnrollmentStatus` (`ACTIVE`, `COMPLETED`, `CANCELLED`) instead of plain strings so a typo like `"COMPLETD"` can't sneak in.
