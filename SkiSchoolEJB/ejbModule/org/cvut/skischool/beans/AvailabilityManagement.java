package org.cvut.skischool.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.cvut.skischool.core.DateTools;
import org.cvut.skischool.model.Availability;
import org.cvut.skischool.model.Instructor;
import org.cvut.skischool.model.Lesson;

/**
 *
 * @author sabolmi1
 */
@Stateless
public class AvailabilityManagement implements AvailabilityManagementLocal, Serializable {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Instructor> getAvailableInstructorsByTime(Date date, int startHour, int startMinutes, int endHour, int endMinutes) {
        Date timeBegin = DateTools.makeDateTime(date, startHour, startMinutes, 0);
//        Date timeEnd = DateTools.makeDateTime(date, endHour, endMinutes, 0);

        Query q = em.createNamedQuery("Availability.getAvailableInstructorsByTime");
        List<Instructor> instructors = (List<Instructor>) 
                q.setParameter("timeBegin", timeBegin, TemporalType.TIMESTAMP)
//                .setParameter("timeEnd", timeEnd, TemporalType.TIMESTAMP)
                .getResultList();

        return instructors;
    }

    @Override
    public List<Instructor> getAvailableInstructorsDuringLesson(Lesson lesson) {
        Date startTime = lesson.getStartTime();
        Date endTime = lesson.getEndTime();
        int startHours = DateTools.getHours(startTime);
        int startMinutes = DateTools.getMinutes(startTime);
        int endHours = DateTools.getHours(endTime);
        int endMinutes = DateTools.getMinutes(endTime);
        List<Instructor> instructors = getAvailableInstructorsByTime(startTime, startHours, startMinutes, endHours, endMinutes);
        return instructors;
    }

    @Override
    public List<Instructor> getAvailableInstructorsByDate(Date date) {
        Date dayBegin = DateTools.makeDateTime(date, 0, 0, 0);
        Date dayEnd = DateTools.addDay(dayBegin);

        Query q = em.createNamedQuery("Availability.getAvailableInstructorsByDate");
        List<Instructor> instructors = (List<Instructor>)
                q.setParameter("dayBegin", dayBegin, TemporalType.TIMESTAMP)
                .setParameter("dayEnd", dayEnd, TemporalType.TIMESTAMP)
                .getResultList();

        return instructors;
    }

    @Override
    public List<Availability> getAvailabilityByDate(Instructor instructor, Date date) {
        Date dayBegin = DateTools.makeDateTime(date, 0, 0, 0);
        Date dayEnd = DateTools.addDay(dayBegin);

        Query q = em.createNamedQuery("Availability.getAvailabilityByDate");
        List<Availability> availabilities = (List<Availability>) 
                q.setParameter("instructor", instructor)
                .setParameter("dayBegin", dayBegin, TemporalType.TIMESTAMP)
                .setParameter("dayEnd", dayEnd, TemporalType.TIMESTAMP)
                .getResultList();

        return availabilities;
    }

    @Override
    public List<Availability> getConflictAvailabilities(Instructor instructor, Date startTime, Date endTime) {
        Query q = em.createNamedQuery("Availability.getConflictAvailabilities");
        List<Availability> availabilities = (List<Availability>)
                q.setParameter("instructor",instructor)
                .setParameter("startTime",startTime,TemporalType.TIMESTAMP)
                .setParameter("endTime", endTime, TemporalType.TIMESTAMP)
                .getResultList();

        return availabilities;
    }

    @Override
    public void createAvailability(Availability availability) {
        em.persist(availability);
    }

    @Override
    public void updateAvailability(Availability availability) {
        em.merge(availability);
    }

    @Override
    public void deleteAvailability(Availability availability) {
        em.remove(em.merge(availability));
    }
}
