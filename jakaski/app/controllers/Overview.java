package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import models.Availability;
import models.Instructor;
import models.Lesson;
import models.Student;
import models.User;
import play.mvc.Before;
import play.mvc.Controller;

public class Overview extends Controller {

	@Before
	static void setConnectedUser() {
		if (Security.isConnected()) {
			User user = User.find("byEmail", Security.connected()).first();
			renderArgs.put("user", user);
		}
	}

	public static void studentLessons(Long id) {
		List<Lesson> lessons = null;
		Student student = Student.findById(id);
		if (student.lessons != null) {
			lessons = new ArrayList<Lesson>(student.lessons);
		}
		renderArgs.put("lessons", lessons);
		render("@lessons",lessons);
	}
	
	public static void instructorLessons(Long id) {
		List<Lesson> lessons = null;
		Instructor instructor = Instructor.findById(id);
		if (instructor.lessons != null) {
			lessons = new ArrayList<Lesson>(instructor.lessons);
		}
		renderArgs.put("lessons", lessons);
		render("@lessons",lessons);
	}
}
