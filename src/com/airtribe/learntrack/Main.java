package com.airtribe.learntrack;

import com.airtribe.learntrack.entity.*;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.service.*;

import java.util.List;
import java.util.Scanner;

public class Main {

	private static final Scanner scanner = new Scanner(System.in);

	private static final StudentService studentService = new StudentService();

	private static final CourseService courseService = new CourseService();

	private static final EnrollmentService enrollmentService = new EnrollmentService(studentService, courseService);

	public static void main(String[] args) {
		boolean running = true;

		while (running) {
			System.out.println("=== LearnTrack ===");
			System.out.println("1. Student Management");
			System.out.println("2. Course Management");
			System.out.println("3. Enrollment Management");
			System.out.println("0. Exit");

			try {
				switch (readInt("Choose option: ")) {
				case 1 -> studentMenu();
				case 2 -> courseMenu();
				case 3 -> enrollmentMenu();
				case 0 -> running = false;
				default -> System.out.println("Invalid menu option.");
				}
			} catch (EntityNotFoundException e) {
				System.out.println("Error: " + e.getMessage());
			} catch (Exception e) {
				System.out.println("An unexpected error occurred. Please try again.");
			}
		}

		scanner.close();
		System.out.println("Thank you for using LearnTrack LMS.");
	}

	private static void studentMenu() {
		boolean back = false;

		while (!back) {
			System.out.println("\n--- Student Management ---");
			System.out.println("1. Add student");
			System.out.println("2. View students");
			System.out.println("3. Search student by ID");
			System.out.println("4. Deactivate student");
			System.out.println("0. Back");

			switch (readInt("Choose option: ")) {
			case 1 -> addStudent();
			case 2 -> viewStudents();
			case 3 -> searchStudent();
			case 4 -> deactivateStudent();
			case 0 -> back = true;
			default -> System.out.println("Invalid menu option.");
			}
		}
	}

	private static void addStudent() {
		String firstName = readText("First name: ");
		String lastName = readText("Last name: ");
		String email = readText("Email (optional): ");
		String batch = readText("Batch: ");

		Student student = email.isBlank()
				? studentService.addStudent(firstName, lastName, batch)
						: studentService.addStudent(
								firstName, lastName, email, batch);

		System.out.println("Student added. ID: " + student.getId());
	}

	private static void viewStudents() {
		List<Student> students = studentService.listStudents();

		if (students.isEmpty()) {
			System.out.println("No students found.");
			return;
		}

		for (Student s : students) {
			System.out.println(
					s.getId() + " | " + s.getDisplayName()
					+ " | " + s.getEmail()
					+ " | Batch: " + s.getBatch()
					+ " | Active: " + s.isActive()
					);
		}
	}

	private static void searchStudent() {
		long id = readLong("Student ID: ");
		Student s = studentService.findStudentById(id);

		System.out.println("Name: " + s.getDisplayName());
		System.out.println("Email: " + s.getEmail());
		System.out.println("Batch: " + s.getBatch());
		System.out.println("Active: " + s.isActive());
	}

	private static void deactivateStudent() {
		long id = readLong("Student ID: ");
		studentService.deactivateStudent(id);
		System.out.println("Student deactivated.");
	}

	private static void courseMenu() {
		boolean back = false;

		while (!back) {
			System.out.println("\n--- Course Management ---");
			System.out.println("1. Add course");
			System.out.println("2. View courses");
			System.out.println("3. Activate course");
			System.out.println("4. Deactivate course");
			System.out.println("0. Back");

			switch (readInt("Choose option: ")) {
			case 1 -> addCourse();
			case 2 -> viewCourses();
			case 3 -> changeCourseStatus(true);
			case 4 -> changeCourseStatus(false);
			case 0 -> back = true;
			default -> System.out.println("Invalid menu option.");
			}
		}
	}

	private static void addCourse() {
		String name = readText("Course name: ");
		String description = readText("Description: ");
		int duration = readInt("Duration in weeks: ");

		if (duration <= 0) {
			System.out.println("Duration must be positive.");
			return;
		}

		Course course = courseService.addCourse(
				name, description, duration);

		System.out.println("Course added. ID: " + course.getId());
	}

	private static void viewCourses() {
		List<Course> courses = courseService.listCourses();

		if (courses.isEmpty()) {
			System.out.println("No courses found.");
			return;
		}

		for (Course c : courses) {
			System.out.println(
					c.getId() + " | " + c.getCourseName()
					+ " | " + c.getDurationInWeeks() + " weeks"
					+ " | Active: " + c.isActive()
					);
		}
	}

	private static void changeCourseStatus(boolean active) {
		long id = readLong("Course ID: ");
		courseService.setCourseActive(id, active);

		System.out.println("Course status updated.");
	}

	private static void enrollmentMenu() {
		boolean back = false;

		while (!back) {
			System.out.println("\n--- Enrollment Management ---");
			System.out.println("1. Enroll student");
			System.out.println("2. View student enrollments");
			System.out.println("3. Complete enrollment");
			System.out.println("4. Cancel enrollment");
			System.out.println("0. Back");

			switch (readInt("Choose option: ")) {
			case 1 -> enrollStudent();
			case 2 -> viewEnrollments();
			case 3 -> updateEnrollment(EnrollmentStatus.COMPLETED);
			case 4 -> updateEnrollment(EnrollmentStatus.CANCELLED);
			case 0 -> back = true;
			default -> System.out.println("Invalid menu option.");
			}
		}
	}

	private static void enrollStudent() {
		long studentId = readLong("Student ID: ");
		long courseId = readLong("Course ID: ");

		Enrollment enrollment =
				enrollmentService.enrollStudent(studentId, courseId);

		System.out.println(
				"Enrollment created. ID: " + enrollment.getId()
				);
	}

	private static void viewEnrollments() {
		long studentId = readLong("Student ID: ");

		// Validate that the student exists.
		studentService.findStudentById(studentId);

		List<Enrollment> enrollments =
				enrollmentService.getEnrollmentsByStudent(studentId);

		if (enrollments.isEmpty()) {
			System.out.println("No enrollments found.");
			return;
		}

		for (Enrollment e : enrollments) {
			System.out.println(
					"Enrollment: " + e.getId()
					+ " | Course: " + e.getCourseId()
					+ " | Date: " + e.getEnrollmentDate()
					+ " | Status: " + e.getStatus()
					);
		}
	}

	private static void updateEnrollment(EnrollmentStatus status) {
		long id = readLong("Enrollment ID: ");

		if (enrollmentService.updateEnrollmentStatus(id, status)) {
			System.out.println("Enrollment status: " + status);
		} else {
			System.out.println("Enrollment not found.");
		}
	}

	private static String readText(String prompt) {
		System.out.print(prompt);
		return scanner.nextLine().trim();
	}

	private static int readInt(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				return Integer.parseInt(scanner.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("Enter a valid whole number.");
			}
		}
	}

	private static long readLong(String prompt) {
		while (true) {
			try {
				System.out.print(prompt);
				return Long.parseLong(scanner.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("Enter a valid numeric ID.");
			}
		}
	}
}
