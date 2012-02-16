package org.cvut.skischool.back;

import org.cvut.skischool.TimeBar;
import org.cvut.skischool.core.DateTools;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.cvut.skischool.beans.AvailabilityManagementLocal;
import org.cvut.skischool.beans.LessonManagementLocal;
import org.cvut.skischool.beans.UserManagementLocal;
import org.cvut.skischool.core.NamingConstants;
import org.cvut.skischool.core.NumberConstants;
import org.cvut.skischool.model.Availability;
import org.cvut.skischool.model.Instructor;
import org.cvut.skischool.model.Lesson;
import org.cvut.skischool.model.Student;

/**
 *
 * @author matej
 */
@ManagedBean(name = "timetableBean")
@SessionScoped
public class TimetableBean implements Serializable {

    @EJB
    private AvailabilityManagementLocal availabilityManagement;
    @EJB
    private LessonManagementLocal lessonManagement;
    @EJB
    private UserManagementLocal userManagement;
    private DataModel<Instructor> instructors;
    private Student newStudent;
    private Student selectedStudent;
    private Instructor selectedInstructor;
    private Lesson lesson;
    private Date date;
    private boolean freeLesson;
    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private int groupSize;

    public TimetableBean() {
        date = new Date();
        lesson = new Lesson();
        selectedInstructor = null;
        selectedStudent = null;
        newStudent = null;
    }

    public boolean getFreeLesson() {
        return freeLesson;
    }

    public void setFreeLesson(boolean isFreeLesson) {
        this.freeLesson = isFreeLesson;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Student getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(Student selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

    public Instructor getSelectedInstructor() {
        return selectedInstructor;
    }

    public void setSelectedInstructor(Instructor selectedInstructor) {
        this.selectedInstructor = selectedInstructor;
    }

    public void goYesterday(ActionEvent event) {
        date = DateTools.subtractDay(date);
    }

    public void goTomorrow(ActionEvent event) {
        date = DateTools.addDay(date);
    }

    public void goToday(ActionEvent event) {
        date = new Date();
    }

    public DataModel<Instructor> getAvailableInstructors() {
        instructors = new ListDataModel<Instructor>(availabilityManagement.getAvailableInstructorsByDate(date));
        return instructors;
    }

    public List<TimeBar> createTimeBars(Instructor instructor) {
        List<TimeBar> bars = new ArrayList<TimeBar>();
        List<Availability> availabilities = availabilityManagement.getAvailabilityByDate(instructor, date);
        List<Lesson> lessons = lessonManagement.getLessonsByDate(instructor, date);
        double previousEnd = NumberConstants.DAY_START_HOUR;

        for (Availability a : availabilities) {
            bars.addAll(processAvailability(a, lessons, previousEnd));
            previousEnd = DateTools.timeToHours(a.getEndTime());
        }

        return bars;
    }

    private List<TimeBar> processAvailability(Availability a, List<Lesson> lessons, double previousEnd) {
        List<TimeBar> bars = new ArrayList<TimeBar>();
        int margin = NumberConstants.BAR_MARGIN;
        boolean noLesson = true;

        for (Lesson l : lessons) {
            Date lessonStart = DateTools.addMinute(l.getStartTime());
            if (lessonStart.after(a.getStartTime()) && lessonStart.before(a.getEndTime())) {
                // time bar for avaiability before lesson
                int left = (int) Math.round((DateTools.timeToHours(a.getStartTime()) - previousEnd) * NumberConstants.HOUR_WIDTH) + margin;
                int width = (int) Math.round((DateTools.timeToHours(l.getStartTime()) - DateTools.timeToHours(a.getStartTime())) * NumberConstants.HOUR_WIDTH) - 2 * margin;
                if (width > 0) {
                    Availability aBefore = new Availability(a.getStartTime(), l.getStartTime());
                    aBefore.setNote(a.getNote());
                    bars.add(new TimeBar(left, width, aBefore));
                    previousEnd = DateTools.timeToHours(aBefore.getEndTime());
                }

                // time bar for reserved lesson
                left = (int) Math.round((DateTools.timeToHours(l.getStartTime()) - previousEnd) * NumberConstants.HOUR_WIDTH) + margin;
                width = (int) Math.round((DateTools.timeToHours(l.getEndTime()) - DateTools.timeToHours(l.getStartTime())) * NumberConstants.HOUR_WIDTH) - 2 * margin;
                bars.add(new TimeBar(left, width, l));

                // time bar for the rest of availability
                if (!DateTools.equalsTime(l.getEndTime(), a.getEndTime())) {
                    double end = DateTools.timeToHours(l.getEndTime());
                    Availability aRest = new Availability(l.getEndTime(), a.getEndTime());
                    aRest.setNote(a.getNote());
                    bars.addAll(processAvailability(aRest, lessons, end));
                }

                noLesson = false;
                break;
            }
        }
        if (noLesson) {
            int left = (int) Math.round((DateTools.timeToHours(a.getStartTime()) - previousEnd) * NumberConstants.HOUR_WIDTH) + margin;
            int width = (int) Math.round((DateTools.timeToHours(a.getEndTime()) - DateTools.timeToHours(a.getStartTime())) * NumberConstants.HOUR_WIDTH) - 2 * margin;
            bars.add(new TimeBar(left, width, a));
        }

        return bars;
    }

    public boolean displayedTime(int width) {
        return (width >= (NumberConstants.HOUR_WIDTH - 2 * NumberConstants.BAR_MARGIN));
    }

    public int getAvailabilityStartHour() {
        if (lesson.getStartTime() == null) {
            return NumberConstants.DAY_START_HOUR;
        }
        return DateTools.getHours(lesson.getStartTime());
    }

    public int getAvailabilityEndHour() {
        if (lesson.getEndTime() == null) {
            return NumberConstants.DAY_END_HOUR;
        }
        return DateTools.getHours(lesson.getEndTime());
    }

    public List<Student> completeStudent(String query) {
        List<Student> results = userManagement.getStudentsByName(query);
        if (results.isEmpty()) {
            newStudent = new Student();
            String[] names = query.split(" +");

            if (names.length > 1) {
                newStudent.setFirstName(names[0]);
                newStudent.setLastName(names[1]);
            } else {
                newStudent.setFirstName(null);
                newStudent.setLastName(names[0]);
            }
        }
        return results;
    }

    public List<Instructor> completeInstructor(String query) {
        return userManagement.getInstructorsByName(query);
    }

    public void newLesson(Availability availability) {
        lesson = new Lesson();
        selectedStudent = null;
        selectedInstructor = instructors.getRowData();
        freeLesson = false;

        Date lessonStart = availability.getStartTime();
        Date lessonEnd = availability.getEndTime();

        startHour = DateTools.getHours(lessonStart);
        startMinute = DateTools.getMinutes(lessonStart);
        endHour = DateTools.getHours(lessonEnd);
        endMinute = DateTools.getMinutes(lessonEnd);

        lesson.setStartTime(lessonStart);
        lesson.setEndTime(lessonEnd);
    }

    public void newFreeLesson(int hour) {
        lesson = new Lesson();
        selectedStudent = null;
        selectedInstructor = null;
        freeLesson = true;

        Date lessonStart = DateTools.makeDateTime(date, hour, 0);
        Date lessonEnd = DateTools.makeDateTime(date, hour + 1, 0);

        startHour = DateTools.getHours(lessonStart);
        startMinute = DateTools.getMinutes(lessonStart);
        endHour = DateTools.getHours(lessonEnd);
        endMinute = DateTools.getMinutes(lessonEnd);
    }

    public void createNewStudent(ActionEvent event) {
        if (newStudent == null) {
//            showErrorMessage("Please enter name of the student");
            showErrorMessage("Prosím zadajte meno žiaka");
            return;
        }
//        if (newStudent.getFirstName() == null) {
////            showErrorMessage("Please enter first name and last name");
//            showErrorMessage("Prosím zadajte meno a prezvisko žiaka");
//            return;
//        }

        newStudent.setGroupSize(1);
        newStudent.setDisabled(false);

        userManagement.createStudent(newStudent);
        selectedStudent = newStudent;
//        showInfoMessage("New student created successfully");
        showInfoMessage("Nový žiak bol úspešne vytvorený");
    }

    public void createLesson(ActionEvent event) {
        List<Instructor> instructorsTmp = new ArrayList<Instructor>();
        instructorsTmp.add(selectedInstructor);
        Date startTime = DateTools.makeDateTime(date, startHour, startMinute, 0);
        Date endTime = DateTools.makeDateTime(date, endHour, endMinute, 0);

        // check whether time is mismatched
        if (endTime.before(startTime)) {
//            showErrorMessage("End time is before start time");
            showErrorMessage("Koniec je pred začiatkom");
            return;
        }
        // check whether time equals
        if (startTime.equals(endTime)) {
//            showErrorMessage("Start time equals end time");
            showErrorMessage("Začiatok a koniec sú rovnaké");
            return;
        }
        // check whether is selected instructor
        if (selectedInstructor == null) {
//            showErrorMessage("You must add instructor");
            showErrorMessage("Pridajte inštruktora");
            return;
        }
        // check whether is selected student
        if (selectedStudent == null) {
//            showErrorMessage("You must add student");
            showErrorMessage("Pridajte žiaka");
            return;
        }
        // check whether other lessons are not reserved
        List<Lesson> conflictLessons = lessonManagement.checkLessonReservations(startTime, endTime, instructorsTmp);
        if (conflictLessons.size() > 0) {
            Lesson l = conflictLessons.get(0);
            DateFormat df = new SimpleDateFormat("HH:mm");
            String start = df.format(l.getStartTime());
            String end = df.format(l.getEndTime());
            String student = l.getStudents().get(0).getFirstName() + " " + l.getStudents().get(0).getLastName();

//            showErrorMessage("Reserved lesson (" + student + ", " + start + " - " + end + ")");
            showErrorMessage("Iná hodina v tomto čase (" + student + ", " + start + " - " + end + ")");
            return;
        }
        // create availability for free lesson
        if (freeLesson) {
            Availability a = new Availability();
            a.setStartTime(startTime);
            a.setEndTime(endTime);
            a.setInstructor(selectedInstructor);
            availabilityManagement.createAvailability(a);
        }
        // check whether instructor is available
        List<Instructor> conflictInstructors = lessonManagement.checkLessonAvailability(startTime, endTime, instructorsTmp);
        if (conflictInstructors.size() > 0) {
            DateFormat df = new SimpleDateFormat("HH:mm");
            String start = df.format(startTime);
            String end = df.format(endTime);
            String instructor = conflictInstructors.get(0).getFirstName() + " " + conflictInstructors.get(0).getLastName();

//            showErrorMessage(instructor + " is not available during " + start + " - " + end);
            showErrorMessage(instructor + " nie je dostupný " + start + " - " + end);
            return;
        }

        lesson.setInstructors(instructorsTmp);
        lesson.setStudents(new ArrayList<Student>());
        lesson.getStudents().add(selectedStudent);
        selectedStudent.setGroupSize(groupSize);
        lesson.setStartTime(startTime);
        lesson.setEndTime(endTime);
        lesson.setExecuted(false);
        lesson.setPaid(false);

        if (groupSize < NumberConstants.GROUP_SIZE) {
            lesson.setLessonType(NamingConstants.LESSON_INDIVIDUAL);
        } else {
            lesson.setLessonType(NamingConstants.LESSON_GROUP);
        }

        lessonManagement.createLesson(lesson);
//        showInfoMessage("Lesson created successfully");
        showInfoMessage("Hodina úspešne vytvorená");
    }

    public void updateLesson(ActionEvent event) {
        lessonManagement.updateLesson(lesson);
//        showInfoMessage("Lesson updated successfully");
        showInfoMessage("Hodina úspešne upravená");
    }

    public void deleteLesson(ActionEvent event) {
        if (lesson.isExecuted()) {
//            showErrorMessage("Lesson is already executed");
            showErrorMessage("Hodina už prebehla");
            return;
        }

        lessonManagement.deleteLesson(lesson);
//        showInfoMessage("Lesson removed successfully");
        showInfoMessage("Hodina úspešne zmazaná");
    }

    private void showErrorMessage(String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private void showInfoMessage(String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
