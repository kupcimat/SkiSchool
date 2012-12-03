package controllers;

import models.User;
import play.mvc.Before;
import play.mvc.With;
import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;

@With(Deadbolt.class)
@Restrict("admin")
public class Students extends CRUD{

	@Before
	static void setConnectedUser() {
		if (Security.isConnected()) {
			User user = User.find("byEmail", Security.connected()).first();
			renderArgs.put("instructor", user);
		}
	}
}
