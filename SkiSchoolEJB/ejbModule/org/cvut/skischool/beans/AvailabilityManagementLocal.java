package org.cvut.skischool.beans;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import org.cvut.skischool.model.Availability;
import org.cvut.skischool.model.Instructor;
import org.cvut.skischool.model.Lesson;

/**
 *
 * @author sabolmi1
 */
@Local
public interface AvailabilityManagementLocal {

    void createAvailability(Availability availability);

    void updateAvailability(Availability availability);

    void deleteAvailability(Availability availability);

    List<Instructor> getAvailableInstructorsByDate(Date date);

    List<Instructor> getAvailableInstructorsByTime(Date date, int startHour, int startMinutes, int endHour, int endMinutes);

    List<Instructor> getAvailableInstructorsDuringLesson(Lesson lesson);

    List<Availability> getAvailabilityByDate(Instructor instructor, Date date);

    List<Availability> getConflictAvailabilities(Instructor instructor, Date startTime, Date endTime);
}
