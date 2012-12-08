package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import controllers.deadbolt.Restrictions;
import controllers.scheduler.SchedulerEvent;

import models.*;

@With(Deadbolt.class)
public class Application extends Controller {

    @Restrictions({ @Restrict("admin"), @Restrict("editor"), @Restrict("instructor") })
    public static void index() {
        render();
    }

    /**
     * @param body MUST BE NAMED "BODY" TO FUNCTION PROPERLY (BECAUSE WE USE BODY OF HTTP REQUEST)!
     */
    public static void createAvailability(SchedulerEvent body) {

        String user = Security.connected();
        Instructor instructor = Instructor.find("byEmail", user).first();

        // TODO debug
        // System.err.println("body: " + body);
        // System.out.println("user: " + user);
        // System.out.println("instructor: " + instructor);

        Availability availability = body.createAvailability(new Location("Jahodna"), instructor);
        instructor.addAvailability(availability);

        renderJSON(new SchedulerEvent.Response(availability.getId()));
    }
}
