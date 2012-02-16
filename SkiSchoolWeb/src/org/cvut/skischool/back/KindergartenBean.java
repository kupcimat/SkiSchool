package org.cvut.skischool.back;

import java.io.Serializable;
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
import org.cvut.skischool.core.DateTools;
import org.cvut.skischool.core.NamingConstants;
import org.cvut.skischool.model.Instructor;
import org.cvut.skischool.model.Lesson;
import org.cvut.skischool.model.Student;
import org.primefaces.model.DualListModel;

/**
 *
 * @author sabolmi1
 */
@ManagedBean(name = "kindergartenBean")
@SessionScoped
public class KindergartenBean implements Serializable {

    @EJB
    private LessonManagementLocal lessonManagement;
    @EJB
    private UserManagementLocal userManagement;
    @EJB
    private AvailabilityManagementLocal availabilityManagement;
    private DualListModel<Instructor> instructors;
    private List<Student> students;
    private Student selectedStudent;
    private Student newStudent;
    private DataModel<Lesson> kindergartens;
    private Lesson lesson;
    private List<Instructor> previousTargetInstructors;
    private int startHour;
    private int startMinutes;
    private int endHour;
    private int endMinutes;
    private Date date;

    public KindergartenBean() {
        date = new Date();
        instructors = new DualListModel<Instructor>(new ArrayList(), new ArrayList());
        students = new ArrayList();
        lesson = new Lesson();
        selectedStudent = null;
        newStudent = null;
    }

    public Student getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(Student selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinutes() {
        return endMinutes;
    }

    public void setEndMinutes(int endMinutes) {
        this.endMinutes = endMinutes;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinutes() {
        return startMinutes;
    }

    public void setStartMinutes(int startMinutes) {
        this.startMinutes = startMinutes;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

//    public DataModel<Instructor> getAvailableInstructors() {
//        instructors = new DualListModel(availabilityManagement.getAvailableInstructorsByDate(date));
//        return instructors;
//    }
//     public List<Student> completeStudent(String query) {
//        return userManagement.getStudentsByName(query);
//    }
//
    public DualListModel<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(DualListModel<Instructor> instructors) {
        this.instructors = instructors;
    }

    public int getStudentsCount() {
        return students.size();
    }

    public DataModel<Lesson> getAllKindergartens() {
        kindergartens = new ListDataModel<Lesson>(lessonManagement.getAllLessonsByType(NamingConstants.LESSON_KINDERGARTEN));
        return kindergartens;
    }

    public void setAvailableInstructors(ActionEvent event) {
        lesson = kindergartens.getRowData();
        List<Instructor> teachingInstructors = lesson.getInstructors();
        List<Instructor> availableInstructors = new ArrayList<Instructor>(availabilityManagement.getAvailableInstructorsDuringLesson(lesson));
        availableInstructors.removeAll(teachingInstructors);
        instructors = new DualListModel<Instructor>(availableInstructors, teachingInstructors);
        previousTargetInstructors = teachingInstructors;
    }

    public void setLessonStudents(ActionEvent event) {
        selectedStudent = null;
        lesson = kindergartens.getRowData();
        students = lesson.getStudents();
    }

    public void newKindergarten() {
        lesson = new Lesson();
    }

    public void createKindergarten(ActionEvent event) {
        Date startTime = DateTools.makeDateTime(date, startHour, startMinutes, 0);
        Date endTime = DateTools.makeDateTime(date, endHour, endMinutes, 0);

        if (endTime.before(startTime)) {
//            showErrorMessage("End time is before start time");
            showErrorMessage("Koniec je pred začiatkom");
            return;
        }
        if (startTime.equals(endTime)) {
//            showErrorMessage("Start time and end time are equal");
            showErrorMessage("Začiatok a koniec sú rovnaké");
            return;
        }
        lesson.setStartTime(startTime);
        lesson.setEndTime(endTime);

        lesson.setExecuted(false);
        lesson.setLessonType(NamingConstants.LESSON_KINDERGARTEN);
        lesson.setPaid(false);
        lesson.setSnowboard(false);

        lessonManagement.createLesson(lesson);
//        showInfoMessage("Kindergarten created");
        showInfoMessage("Škôlka vytvorená");
    }

    private void writeOut(String id, List<Instructor> instructors) {
        System.out.print(id + ": ");
        for (Instructor instructor : instructors) {
            System.out.print(instructor.getFirstName() + " " + instructor.getLastName() + " ");
        }
        System.out.println("");
    }

    public void updateKindergarten(ActionEvent event) {
        List<Instructor> currentInstructors = new ArrayList<Instructor>(instructors.getTarget());
        currentInstructors.removeAll(previousTargetInstructors);
        List<Lesson> conflictLessons = lessonManagement.checkLessonReservations(lesson.getStartTime(), lesson.getEndTime(), currentInstructors);
        String conflictInstructorsNames = "";
        if (conflictLessons.size() > 0) {
            for (Lesson conflictLesson : conflictLessons) {
                for (Instructor conflictInstructor : conflictLesson.getInstructors()) {
                    conflictInstructorsNames += conflictInstructor.getFirstName() + " " + conflictInstructor.getLastName() + "<br/>";
                }
            }
//            showErrorMessage("Conflict instructors", conflictInstructorsNames);
            showErrorMessage("Inštruktori majú v tomto čase inú hodinu", conflictInstructorsNames);
//             setAvailableInstructors(null);
            return;
        }

        lesson.setInstructors(instructors.getTarget());

        lessonManagement.updateLesson(lesson);
//        showInfoMessage("Instructors updated");
        showInfoMessage("Inštruktori upravení");
        previousTargetInstructors = instructors.getTarget();
    }

    public void updateStudents(ActionEvent event) {
        lesson.setStudents(students);
        lessonManagement.updateLesson(lesson);
//        showInfoMessage("Students updated");
        showInfoMessage("Žiaci upravení");
    }

    public String reinitStudent() {
        selectedStudent = new Student();
        return null;
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

    private void showInfoMessage(String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private void showErrorMessage(String message, String detail) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, detail);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    private void showErrorMessage(String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
