package com.airtribe.learntrack.service;

import java.util.ArrayList;
import java.util.List;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.util.IdGenerator;

/**
 * CourseService is to handle all the functionalities regarding the course
 */
public class CourseService {

	private final List<Course> courses = new ArrayList<>();

	public Course addCourse(String name, String description, 
			int durationInWeeks) {

		Course course = new Course((long) IdGenerator.getNextCourseId(),
				name, description, durationInWeeks, true);

		courses.add(course);
		return course;
	}

	public List<Course> listCourses() {
		return new ArrayList<>(courses);
	}

	public Course findCourseById(Long id) {
		for (Course course : courses) {
			if (course.getId().equals(id)) {
				return course;
			}
		}
		throw new EntityNotFoundException(
        		"Course with ID " + id + " was not found.");
	}

	public boolean setCourseActive(Long id, boolean active) {
		Course course = findCourseById(id);

		if (course == null) {
			return false;
		}
		course.setActive(active);
		return true;
	}
}
