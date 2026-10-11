package com.airtribe.learntrack.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.IdGenerator;

/**
 * StudentService to handle all the functionalities regarding the student
 */
public class StudentService {

	private final List<Student> students = new ArrayList<>();

	/**
	 * add a student
	 * @param student
	 * @return
	 */
	public Student addStudent(String firstName, String lastName,
			String email, String batch) {

		Long id = (long) IdGenerator.getNextStudentId();

		Student student = new Student(id, firstName, lastName,
				email, batch, true);

		return addStudent(student);
	}

	// method overloading - create a student without email
	public Student addStudent(String firstName,String lastName, 
			String batch) {

		Long id = (long) IdGenerator.getNextStudentId();

		Student student = new Student(
				id, firstName, lastName, batch, true
				);

		return addStudent(student);
	}
	
	private Student addStudent(Student student) {
		students.add(student);
		return student;
	}
	
	/**
	 * Hard delete as in 3. Static, Methods, and Utility Classes on requirement document
	 * @param id
	 * @return
	 */
	public boolean removeStudent(Long id) {
		Iterator<Student> iterator = students.iterator();

	    while (iterator.hasNext()) {
	        Student student = iterator.next();

	        if (student.getId().equals(id)) {
	            iterator.remove();
	            return true;
	        }
	    }
	    return false;
	}

	/**
	 * updateStudent
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param batch
	 * @param active
	 * @return
	 */
	public boolean updateStudent(Long id, String firstName, String lastName,
			String email, String batch, boolean active) {

		Student student = findStudentById(id);

		if (student == null) {
			return false;
		}

		student.setFirstName(firstName);
		student.setLastName(lastName);
		student.setEmail(email);
		student.setBatch(batch);
		student.setActive(active);

		return true;
	}
	
	/**
	 * listStudents - to list all the students
	 * @return
	 */
	public List<Student> listStudents() {
		return students;
	}
	
	public Student findStudentById(Long id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        
        throw new EntityNotFoundException(
        		"Student with ID " + id + " was not found.");
    }
	
	public Student findStudentById(String studentId) {
		if(studentId == null || studentId.equals("")) {
			throw new InvalidInputException(
	        		"Student ID given is invalid");
		}
		Long id = 0L;
		try {
			id = Long.valueOf(studentId);
		}catch(Exception ex) {
			throw new InvalidInputException(
	        		"Student ID given is invalid"); 
		}
		
		for (Student student : students) {
			if (student.getId().equals(id)) {
				return student;
			}
		}
        
        throw new EntityNotFoundException(
        		"Student with ID " + id + " was not found.");
    }

    public boolean deactivateStudent(Long id) {
        Student student = findStudentById(id);
        if (student == null) {
            return false;
        }
        student.setActive(false);
        return true;
    }

}
