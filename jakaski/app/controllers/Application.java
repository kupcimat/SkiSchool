package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import controllers.deadbolt.Restrictions;

import models.*;

@With(Deadbolt.class)
public class Application extends Controller {

	@Restrictions({@Restrict("admin"), @Restrict("editor"), @Restrict("instructor")})
    public static void index() {
        render();
    }

}