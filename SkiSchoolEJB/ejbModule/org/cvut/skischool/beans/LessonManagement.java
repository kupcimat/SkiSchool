package org.cvut.skischool.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.cvut.skischool.core.DateTools;
import org.cvut.skischool.model.Availability;
import org.cvut.skischool.model.Instructor;
import org.cvut.skischool.model.Lesson;
import org.cvut.skischool.model.Student;

/**
 *
 * @author matej
 */
@Stateless
public class LessonManagement implements LessonManagementLocal {

    @PersistenceContext
    EntityManager em;

    @Override
    @RolesAllowed({"administrator"})
    public void createLesson(Lesson lesson) {
        em.persist(lesson);
    }

    @Override
    public void updateLesson(Lesson lesson) {
        em.merge(lesson);
    }

    @Override
    @RolesAllowed({"administrator"})
    public void deleteLesson(Lesson lesson) {
        if (!lesson.isExecuted()) {
            em.remove(em.merge(lesson));
        }
    }

    @Override
    public Lesson getLesson(Long id) {
        return em.find(Lesson.class, id);
    }

    @Override
    public List<Instructor> checkLessonAvailability(Date startTime, Date endTime, List<Instructor> instructors) {
        List<Instructor> occupiedInstructors = new ArrayList<Instructor>();
        Query q = em.createNamedQuery("Availability.getAvailabilityByTimeAndInstructor");

        Date endTmp = DateTools.subtractMinute(endTime);

        for(Instructor instructor : instructors) {
            List<Availability> availabilities = (List<Availability>) q
                .setParameter("startTime", startTime, TemporalType.TIMESTAMP)
                .setParameter("endTime", endTmp, TemporalType.TIMESTAMP)
                .setParameter("instructor", instructor)
                .getResultList();
            if (availabilities.isEmpty()) {
                occupiedInstructors.add(instructor);
            }
        }

        return occupiedInstructors;
    }

    @Override
    public List<Lesson> checkLessonReservations(Date startTime, Date endTime, List<Instructor> instructors) {
        List<Lesson> conflictLessons = new ArrayList<Lesson>();
        Query q = em.createNamedQuery("Lesson.getLessonsByInstructorAndTime");

        Date startTmp = DateTools.addMinute(startTime);
        Date endTmp = DateTools.subtractMinute(endTime);

        for (Instructor instructor : instructors) {
            List<Lesson> lessons = (List<Lesson>) q
                .setParameter("startTime", startTmp, TemporalType.TIMESTAMP)
                .setParameter("endTime", endTmp, TemporalType.TIMESTAMP)
                .setParameter("instructor", instructor)
                .getResultList();
            conflictLessons.addAll(lessons);
        }

        return conflictLessons;
    }

    @Override
    public List<Lesson> getAllLessons() {
        List<Lesson> lessons = (List<Lesson>) em.createNamedQuery("Lesson.getAllLessons").getResultList();
        return lessons;
    }

    @Override
    public List<Lesson> getLessonsByDate(Instructor instructor, Date date) {
        Date dayBegin = DateTools.makeDateTime(date, 0, 0, 0);
        Date dayEnd = DateTools.addDay(dayBegin);

        Query q = em.createNamedQuery("Lesson.getLessonsByDate");
        List<Lesson> lessons = (List<Lesson>) q
                .setParameter("instructor", instructor)
                .setParameter("dayBegin", dayBegin, TemporalType.TIMESTAMP)
                .setParameter("dayEnd", dayEnd, TemporalType.TIMESTAMP)
                .getResultList();

        return lessons;
    }

    @Override
    public List<Lesson> getAllLessonsByType(String type) {
        Query q = em.createNamedQuery("Lesson.getAllLessonsByType");
        List<Lesson> lessons = (List<Lesson>) q
                .setParameter("type",type)
                .getResultList();

        return lessons;
    }

    @Override
    public long countLessonsLangBonus(Instructor instructor, Date startDate, Date endDate) {
        Query q = em.createNamedQuery("Lesson.countLessonsLangBonus");
        Long lessonsCount = (Long) q
                .setParameter("instructor", instructor)
                .setParameter("startDate", startDate, TemporalType.TIMESTAMP)
                .setParameter("endDate", endDate, TemporalType.TIMESTAMP)
                .getSingleResult();

        return lessonsCount;
    }

    @Override
    public List<Lesson> countLessons(Instructor instructor, Date startDate, Date endDate, String type) {
        Query q = em.createNamedQuery("Lesson.countLessons");
        List<Lesson> lessons = (List<Lesson>) q
                .setParameter("instructor", instructor)
                .setParameter("startDate", startDate, TemporalType.TIMESTAMP)
                .setParameter("endDate", endDate, TemporalType.TIMESTAMP)
                .setParameter("type", type)
                .getResultList();

        return lessons;
    }

    @Override
    public List<Lesson> getLessonsByInstructorAndInterval(Instructor instructor, Date startDate, Date endDate){
         Query q = em.createNamedQuery("Lesson.getLessonsByInstructorAndInterval");
         List<Lesson> lessons = (List<Lesson>) q
                .setParameter("instructor", instructor)
                .setParameter("startDate", startDate, TemporalType.TIMESTAMP)
                .setParameter("endDate", endDate, TemporalType.TIMESTAMP)
                .getResultList();
         
         return lessons;
    }

    @Override
    public List<Lesson> getLessonsByStudentAndInterval(Student student, Date startDate, Date endDate) {
         Query q = em.createNamedQuery("Lesson.getLessonsByStudentAndInterval");
         List<Lesson> lessons = (List<Lesson>) q
                .setParameter("student", student)
                .setParameter("startDate", startDate, TemporalType.TIMESTAMP)
                .setParameter("endDate", endDate, TemporalType.TIMESTAMP)
                .getResultList();

         return lessons;
    }
}
