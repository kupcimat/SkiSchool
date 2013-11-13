package controllers;

import models.User;
import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import play.mvc.*;

@With(Deadbolt.class)
@Restrict("admin")
public class Prices extends CRUD {

	@Before
	static void setConnectedUser() {
		if (Security.isConnected()) {
			User user = User.find("byEmail", Security.connected()).first();
			renderArgs.put("user", user);
		}
	}
	
}
