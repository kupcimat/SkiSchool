package models;

import play.*;
import play.data.validation.Required;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
public class Availability extends Model {
	
	@Required
	public Date startTime;
	@Required
	public Date endTime;
	@Enumerated(EnumType.STRING)
	public Location location;
	public String note;
	@ManyToOne
	public Instructor instructor;

	public Availability(Date startTime, Date endTime, Location location, String note, Instructor instructor) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.location = location;
		this.note = note;
		this.instructor = instructor;
	}

}
