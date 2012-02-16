package org.cvut.skischool.beans;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import org.cvut.skischool.model.Instructor;
import org.cvut.skischool.model.Lesson;
import org.cvut.skischool.model.Student;

/**
 *
 * @author matej
 */
@Local
public interface LessonManagementLocal {

    void createLesson(Lesson lesson);

    void updateLesson(Lesson lesson);

    void deleteLesson(Lesson lesson);

    Lesson getLesson(Long id);

    /**
     * Check lesson whether all instructors are available during the specified
     * lesson.
     *
     * @param lesson Lesson to check
     * @return instructors who are not available during specified time
     */
    List<Instructor> checkLessonAvailability(Date startTime, Date endTime, List<Instructor> instructors);

    /**
     * Check lesson whether all instructors have any other reserved lessons
     * conflicting with the specified lesson.
     * 
     * @param lesson Lesson to check
     * @return conflicting lessons
     */
    List<Lesson> checkLessonReservations(Date startTime, Date endTime, List<Instructor> instructors);

    List<Lesson> getAllLessons();

    List<Lesson> getLessonsByDate(Instructor instructor, Date date);

    List<Lesson> getAllLessonsByType(String type);

    List<Lesson> getLessonsByInstructorAndInterval(Instructor instructor, Date startDate, Date endDate);

    long countLessonsLangBonus(Instructor instructor, Date startDate, Date endDate);

    List<Lesson> countLessons(Instructor instructor, Date startDate, Date endDate, String type);

    List<Lesson> getLessonsByStudentAndInterval(Student student, Date startDate, Date endDate);
}
