package lv.nixx.poc.collection.binarysearch;

import java.util.Date;

public class Person {
	
	public int id;
	public String name;
	private Date dateOfBirth;
	
	public Person(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Person(int id, String name, Date dateOfBirth) {
		this(id, name);
		this.dateOfBirth = dateOfBirth;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", dateOfBirth=" + dateOfBirth + "]";
	}
	
}
