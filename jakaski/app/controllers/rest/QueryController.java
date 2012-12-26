package controllers.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Instructor;
import models.Lesson;
import models.rest.InstructorWrapper;
import models.rest.LessonWrapper;
import models.rest.QueryResponse;

import org.apache.commons.lang.time.DateUtils;

import play.data.binding.As;
import play.mvc.Controller;
import controllers.rest.binders.DateBinder;
import controllers.rest.binders.DateSerializer;

public class QueryController extends Controller {

    public static void getLessons(@As(binder = DateBinder.class) Date date) {
        if (date == null) {
            badRequest();
        }

        Date nextDay = DateUtils.addDays(date, 1);
        List<Lesson> lessons = Lesson.find("select l from Lesson l where l.startTime >= ? and l.startTime < ?", date, nextDay).fetch();

        List<LessonWrapper> wrappers = new ArrayList<>();
        for (Lesson lesson : lessons) {
            wrappers.add(new LessonWrapper(lesson));
        }

        renderJSON(new QueryResponse<LessonWrapper>(wrappers), new DateSerializer());
    }

    public static void getInstructors(@As(binder = DateBinder.class) Date date) {
        if (date == null) {
            badRequest();
        }

        // TODO add available instructors
        Date nextDay = DateUtils.addDays(date, 1);
        List<Instructor> instructors = Instructor.find(
                "select distinct i from Instructor i join i.lessons l where l.startTime >= ? and l.startTime < ?", date, nextDay).fetch();

        List<InstructorWrapper> wrappers = new ArrayList<>();
        for (Instructor instructor : instructors) {
            wrappers.add(new InstructorWrapper(instructor));
        }

        renderJSON(new QueryResponse<InstructorWrapper>(wrappers));
    }

}
