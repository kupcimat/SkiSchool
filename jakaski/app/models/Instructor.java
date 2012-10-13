package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Instructor extends User {

	@OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
	public List<Availability> availabilities;

	public Instructor(String email, String password, String fullname) {
		super(email, password, fullname);
		this.availabilities = new ArrayList<Availability>();
	}

	public Instructor addAvailability(Date startTime, Date endTime, String note) {
		Availability availability = new Availability(startTime, endTime, note, this).save();
		this.availabilities.add(availability);
		this.save();
		return this;
	}
}
