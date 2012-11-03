package models;

import play.*;
import play.data.validation.Required;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Lesson extends Model {

	@Required
	public Date startTime;
	@Required
	public Date endTime;
	public Location location;
	public LessonType lessonType;
	public boolean snowboard;
	public boolean paid;
	public Lang language;
	public String note;

	@ManyToMany
	public List<Instructor> instructors;

	@ManyToMany
	public List<Student> students;

	public Lesson(Date startTime, Date endTime, Location location, LessonType lessonType, boolean snowboard, boolean paid, Lang language, String note) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.location = location;
		this.lessonType = lessonType;
		this.snowboard = snowboard;
		this.paid = paid;
		this.note = note;
	}

}
