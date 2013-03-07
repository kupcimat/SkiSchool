package controllers;

import models.Student;
import models.User;
import play.mvc.Before;
import play.mvc.With;
import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import controllers.deadbolt.Restrictions;

@With(Deadbolt.class)
@Restrictions({@Restrict("editor"), @Restrict("admin") })
public class Students extends CRUD{

	@Before
	static void setConnectedUser() {
		if (Security.isConnected()) {
			User user = User.find("byEmail", Security.connected()).first();
			renderArgs.put("user", user);
		}
	}
	
	public static void getStudentBalance(Student student) {
		int lessonCount = 0;
		int paidLessons = 0;
		lessonCount = student.lessons.size();
		paidLessons = student.paidLessons;
		renderArgs.put("balance", paidLessons - lessonCount);
	}

	public static void addPaidLessons(Student student, int count) {
		student.paidLessons += count;
		student.save();
	}
}
