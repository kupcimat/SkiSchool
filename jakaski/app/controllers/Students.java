package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Student;
import models.User;
import play.mvc.Before;
import play.mvc.With;
import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;
import controllers.deadbolt.Restrictions;

@With(Deadbolt.class)
@Restrictions({ @Restrict("editor"), @Restrict("admin") })
public class Students extends CRUD {

    @Before
    static void setConnectedUser() {
        if (Security.isConnected()) {
            User user = User.find("byEmail", Security.connected()).first();
            renderArgs.put("user", user);
        }
    }

    public static void students() {
        List<Student> students = Student.findAll();
        List data = new ArrayList();

        for (Student student : students) {
            List innerList = new ArrayList(2);
            innerList.add(student);
            innerList.add(student.paidLessons - student.lessons.size());
            data.add(innerList);
        }

        render(data);
    }

}
