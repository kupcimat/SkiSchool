package org.cvut.skischool.back;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.cvut.skischool.beans.LessonManagementLocal;
import org.cvut.skischool.core.DateTools;
import org.cvut.skischool.core.NamingConstants;
import org.cvut.skischool.model.Instructor;
import org.cvut.skischool.model.Lesson;
import org.cvut.skischool.model.Student;

/**
 *
 * @author matej
 */
@ManagedBean(name = "selectBean")
@SessionScoped
public class SelectBean implements Serializable {

    @EJB
    private LessonManagementLocal lessonManagement;
    private Date startDate;
    private Date endDate;
    private Instructor instructor;
    private Student student;

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public SelectBean() {
        Date actualDate = new Date();
        startDate = DateTools.makeDate(actualDate, 1);
        endDate = DateTools.makeDate(actualDate, Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void checkDates(ActionEvent event) {
        if (startDate.after(endDate)) {
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Start date is after end date", null);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Čas do je pred časom od", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public long countIndividualLessons(Instructor instructor) {
        long count = 0;
        List<Lesson> lessons = lessonManagement.countLessons(instructor, startDate, endDate, NamingConstants.LESSON_INDIVIDUAL);
        for (Lesson lesson : lessons) {
            count += DateTools.hoursDifference(lesson.getStartTime(), lesson.getEndTime());
        }
        return count;
    }

    public long countLessonsLangBonus(Instructor instructor) {
        return lessonManagement.countLessonsLangBonus(instructor, startDate, endDate);
    }

    public long countKindergartenLessons(Instructor instructor) {
        long count = 0;
        List<Lesson> lessons = lessonManagement.countLessons(instructor, startDate, endDate, NamingConstants.LESSON_KINDERGARTEN);
        for (Lesson lesson : lessons) {
            count += DateTools.hoursDifference(lesson.getStartTime(), lesson.getEndTime());
        }
        return count;
    }

    public long countGroupLessons(Instructor instructor) {
        long count = 0;
        List<Lesson> lessons = lessonManagement.countLessons(instructor, startDate, endDate, NamingConstants.LESSON_GROUP);
        for (Lesson lesson : lessons) {
            count += DateTools.hoursDifference(lesson.getStartTime(), lesson.getEndTime());
        }
        return count;
    }

    public long countLessonsTotal(Instructor instructor) {
        long totalCount = countIndividualLessons(instructor)
                + countKindergartenLessons(instructor)
                + countGroupLessons(instructor);
        return totalCount;
    }

    public DataModel<Lesson> getLessonsByInstructorAndInterval() {
        DataModel<Lesson> lessons = new ListDataModel<Lesson>(lessonManagement.getLessonsByInstructorAndInterval(instructor, startDate, endDate));
        return lessons;
    }

    public DataModel<Lesson> getLessonsByStudentAndInterval() {
        DataModel<Lesson> lessons = new ListDataModel<Lesson>(lessonManagement.getLessonsByStudentAndInterval(student, startDate, endDate));
        return lessons;
    }
}
