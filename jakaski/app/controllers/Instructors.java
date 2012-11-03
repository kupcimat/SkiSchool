package controllers;

import play.mvc.With;
import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;

@With(Deadbolt.class)
@Restrict("admin")
public class Instructors extends CRUD{

}
