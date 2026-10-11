package com.airtribe.learntrack.util;

public final class IdGenerator {
	
	private static int studentIdCounter = 0;
	private static int courseIdCounter = 0;
	
	/**
	 * IdGenerator constructor to prevent the new instance creation
	 */
	private IdGenerator() {
    
	}
	
	public static int getNextStudentId() {
		return ++studentIdCounter;
	}
	
	public static int getNextCourseId() {
		return ++courseIdCounter;
	}

}
