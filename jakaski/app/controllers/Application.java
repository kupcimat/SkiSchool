package controllers;

import play.mvc.Controller;
import play.mvc.With;
import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import controllers.deadbolt.Restrictions;

@With(Deadbolt.class)
public class Application extends Controller {

    @Restrictions({ @Restrict("admin"), @Restrict("editor"), @Restrict("instructor") })
    public static void index() {
        render();
    }

}
