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
	@Enumerated(EnumType.STRING)
	public Location location;
	public LessonType lessonType;
	public boolean snowboard;
	public boolean paid;
	@Enumerated(EnumType.STRING)
	public Lang language;
	public String note;

	@ManyToMany
	public Set<Instructor> instructors;

	@ManyToMany
	public Set<Student> students;

	public Lesson(Date startTime, Date endTime, Location location, LessonType lessonType, boolean snowboard, boolean paid, Lang language, String note) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.location = location;
		this.lessonType = lessonType;
		this.snowboard = snowboard;
		this.paid = paid;
		this.note = note;
		this.instructors = new HashSet<Instructor>();
		this.students = new HashSet<Student>();
	}

}
