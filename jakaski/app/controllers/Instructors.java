package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import models.Instructor;
import models.Lesson;
import models.LessonType;
import models.Student;
import models.User;
import play.mvc.Before;
import play.mvc.With;
import sun.security.action.GetLongAction;
import controllers.deadbolt.Deadbolt;
import controllers.deadbolt.Restrict;

@With(Deadbolt.class)
@Restrict("admin")
public class Instructors extends CRUD {

	@Before
	static void setConnectedUser() {
		if (Security.isConnected()) {
			User user = User.find("byEmail", Security.connected()).first();
			renderArgs.put("user", user);
		}
	}

	public static void overview() {
		List<Instructor> instructors = Instructor.findAll();
		List data = new ArrayList();

		for (Instructor instructor : instructors) {
			List innerList = new ArrayList(6);
			innerList.add(instructor);
			innerList.add(getLessonCountByType(instructor.id, LessonType.INDIVIDUAL));
			innerList.add(getLessonCountByType(instructor.id, LessonType.GROUP));
			innerList.add(getLessonCountByType(instructor.id, LessonType.KINDERGARTEN));
			innerList.add(getLessonCount(instructor.id));
			innerList.add(Lesson
			        .find("select l from Lesson l join l.instructors i join l.language g where i.id= ? and g.name <> ?", instructor.id, "sk").fetch()
			        .size());
			data.add(innerList);
		}

		render(data);
	}

	private static int getLessonCountByType(Long id, LessonType lessonType) {
		int lessonCnt = 0;
		List<Lesson> lessons = Lesson.find("select l from Lesson l join l.instructors i where i.id= ? and l.lessonType = ?", id, lessonType).fetch();
		for (Lesson lesson : lessons) {
			lessonCnt += hoursDifference(lesson.startTime, lesson.endTime);
		}
		return lessonCnt;
	}

	private static int getLessonCount(Long id) {
		int lessonCnt = 0;
		List<Lesson> lessons = Lesson.find("select l from Lesson l join l.instructors i where i.id= ?", id).fetch();
		for (Lesson lesson : lessons) {
			lessonCnt += hoursDifference(lesson.startTime, lesson.endTime);
		}
		return lessonCnt;
	}

	private static long hoursDifference(Date startTime, Date endTime) {
		long hours = 0;
		Calendar cStartTime = Calendar.getInstance();
		Calendar cEndTime = Calendar.getInstance();
		cStartTime.setTime(startTime);
		cEndTime.setTime(endTime);
		long startHour = cStartTime.get(Calendar.HOUR_OF_DAY);
		long endHour = cEndTime.get(Calendar.HOUR_OF_DAY);
		hours = endHour - startHour;
		return hours;
	}
}
