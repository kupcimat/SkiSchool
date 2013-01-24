package controllers.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import models.Availability;
import models.Instructor;
import models.Lesson;
import models.rest.AvailabilityWrapper;
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

    public static void getAvailabilities(@As(binder = DateBinder.class) Date date) {
        if (date == null) {
            badRequest();
        }

        Date nextDay = DateUtils.addDays(date, 1);
        List<Availability> availabilities = Availability.find("select a from Availability a where a.startTime >= ? and a.startTime < ?",
                date, nextDay).fetch();

        List<AvailabilityWrapper> wrappers = new ArrayList<>();
        for (Availability availability : availabilities) {
            wrappers.add(new AvailabilityWrapper(availability));
        }

        renderJSON(new QueryResponse<AvailabilityWrapper>(wrappers), new DateSerializer());
    }

    public static void getInstructors(@As(binder = DateBinder.class) Date date) {
        if (date == null) {
            badRequest();
        }

        Date nextDay = DateUtils.addDays(date, 1);
        // Instructors with lessons
        List<Instructor> withLesson = Instructor.find(
                "select distinct i from Instructor i join i.lessons l where l.startTime >= ? and l.startTime < ?", date, nextDay).fetch();
        // Available instructors
        List<Instructor> available = Instructor.find(
                "select distinct i from Instructor i join i.availabilities a where a.startTime >= ? and a.startTime < ?", date, nextDay)
                .fetch();

        Set<Instructor> instructors = new HashSet<>();
        instructors.addAll(withLesson);
        instructors.addAll(available);

        List<InstructorWrapper> wrappers = new ArrayList<>();
        for (Instructor instructor : instructors) {
            wrappers.add(new InstructorWrapper(instructor));
        }

        renderJSON(new QueryResponse<InstructorWrapper>(wrappers));
    }

}
