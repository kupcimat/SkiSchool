package controllers;

import models.Instructor;
import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Deadbolt.class)
@Restrict("instructor")
public class AdminInstructor extends Controller {

	   @Before
	    static void setConnectedUser() {
	        if(Security.isConnected()) {
	            Instructor instructor = Instructor.find("byEmail", Security.connected()).first();
	            renderArgs.put("user", instructor);
	        }
	    }
	 
	    public static void instructor() {
	        render();
	    }

}
