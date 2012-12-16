package controllers.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Lesson;
import models.rest.QueryResponse;
import models.rest.LessonWrapper;

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

}
