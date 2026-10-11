package com.airtribe.learntrack.entity;

public class Student extends Person{

	private String batch;
	private boolean active;

	public Student() {
		super();
	}

	public Student(Long id, String firstName, String lastName,
			String email, String batch, boolean active) {
		super(id, firstName, lastName, email);
		this.batch = batch;
		this.active = active;
	}

	
	/**
	 * Constructor overloading with - without email
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param batch
	 * @param active
	 */
	public Student(Long id, String firstName, String lastName,
			String batch, boolean active) {
		super(id, firstName, lastName, null);
		this.batch = batch;
			this.active = active;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public String getDisplayName() {
		return "Student's name : " + super.getDisplayName()
		+ " Batch : " + batch;
	}

}
