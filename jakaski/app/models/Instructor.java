package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Instructor extends User {

	public String positionSki;
	public String positionSnowboard;
	public Set<Lang> languages;
	
	@ManyToMany(mappedBy = "instructors")
	public Set<Lesson> lessons;
	
	@OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
	public List<Availability> availabilities;

	public Instructor(String email, String password, String firstname, String surname, String phone) {
		super(email, password, firstname, surname, phone);
		this.availabilities = new ArrayList<Availability>();
	}

	public Instructor addAvailability(Date startTime, Date endTime, Location location, String note) {
		Availability availability = new Availability(startTime, endTime, location, note, this).save();
		this.availabilities.add(availability);
		this.save();
		return this;
	}
}
