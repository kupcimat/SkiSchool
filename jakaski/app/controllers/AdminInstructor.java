package controllers;

import java.util.List;

import models.Availability;
import models.Instructor;
import models.User;
import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import controllers.deadbolt.Restrictions;
import play.data.validation.Valid;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Deadbolt.class)
@Restrictions({@Restrict("instructor"),@Restrict("admin")})
public class AdminInstructor extends Controller {

	@Before
	static void setConnectedUser() {
		if (Security.isConnected()) {
			User user = User.find("byEmail", Security.connected()).first();
			renderArgs.put("instructor", user);
		}
	}

	public static void instructor() {
		render();
	}

	public static void availabilities() {
		List<Availability> availabilities = Availability.find("instructor.email", Security.connected()).fetch();
		render(availabilities);
	}

	public static void timetable() {
		render();
	}

	public static void update(Instructor instructor, String oldPassword, String newPassword, String passwordConfirm) {
		Instructor newInstructor = Instructor.find("byEmail", Security.connected()).first();
		validation.equals(oldPassword, newInstructor.password).message("oldPasswordDiff");
		validation.equals(passwordConfirm, newPassword).message("newPasswordDiff");
		newInstructor.firstname = instructor.firstname;
		newInstructor.surname = instructor.surname;
		newInstructor.phone = instructor.phone;
		newInstructor.email = instructor.email;
		newInstructor.password = newPassword;
		validation.valid(newInstructor);
		if (validation.hasErrors()) {
			render("@instructor", newInstructor, newPassword, passwordConfirm, oldPassword);
		}
		newInstructor.save();
		timetable();
	}
}
