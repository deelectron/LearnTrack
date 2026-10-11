package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.entity.Course;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * EnrollmentService is to handle all the enrollment related functionalities
 */
public class EnrollmentService {

    private final List<Enrollment> enrollments = new ArrayList<>();
    
    private final StudentService studentService;
    
    private final CourseService courseService;
    
    private long enrollmentIdCounter = 0;

    
    /**
     * EnrollmentService - constructor
     * @param studentService
     * @param courseService
     */
    public EnrollmentService(StudentService studentService,
                             CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    /**
     * enrollStudent is to enroll a student for the preferred course ID
     * @param studentId
     * @param courseId
     * @return
     */
    public Enrollment enrollStudent(Long studentId, Long courseId) {

        Student student = studentService.findStudentById(studentId);
        Course course = courseService.findCourseById(courseId);

        if (student == null || course == null) {
            return null;
        }

        if (!student.isActive() || !course.isActive()) {
            return null;
        }

        Enrollment enrollment = new Enrollment(++enrollmentIdCounter, studentId,
            courseId, LocalDate.now(), EnrollmentStatus.ACTIVE);

        enrollments.add(enrollment);
        return enrollment;
    }

    /**
     * getEnrollmentsByStudent is to fetch the Enrollment details with the student Id
     * @param studentId
     * @return
     */
    public List<Enrollment> getEnrollmentsByStudent(Long studentId) {
        List<Enrollment> result = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId().equals(studentId)) {
                result.add(enrollment);
            }
        }

        return result;
    }

    public boolean updateEnrollmentStatus(Long enrollmentId, EnrollmentStatus status) {

        if (!EnrollmentStatus.COMPLETED.equals(status)
                && !EnrollmentStatus.CANCELLED.equals(status) ) {
            return false;
        }

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId().equals(enrollmentId)) {
                enrollment.setStatus(status);
                return true;
            }
        }

        return false;
    }
}
