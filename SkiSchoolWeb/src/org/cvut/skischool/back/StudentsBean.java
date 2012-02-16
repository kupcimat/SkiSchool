package org.cvut.skischool.back;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.cvut.skischool.Messages;
import org.cvut.skischool.beans.UserManagementLocal;
import org.cvut.skischool.model.Student;

/**
 *
 * @author matej
 */
@ManagedBean(name = "studentsBean")
@SessionScoped
public class StudentsBean implements Serializable {

    @EJB
    private UserManagementLocal userManagement;
    private Student student;

    public StudentsBean() {
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Student> getAllStudents() {
        return userManagement.getAllStudents();
    }

    public String newStudent() {
        student = new Student();
        student.setDisabled(false);

        return "createstudent?faces-redirect=true";
    }

    public String editStudent(Student student) {
        this.student = student;

        return "createstudent?faces-redirect=true";
    }

    public String saveStudent() {
        userManagement.updateStudent(student);

        return "users?faces-redirect=true";
    }

    public void deleteStudent(Student student) {
        if (student.getLessons() == null || student.getLessons().isEmpty()) {
            userManagement.deleteStudent(student);
            Messages.showInfoMessage("Žiak bol úspešne zmazaný");
        }else{
            showWarnMessage("Žiak nemôže byť zmazaný pretože má hodiny");
//            showWarnMessage("Student has lessons and can't be deleted");
        }
    }

     private void showWarnMessage(String message) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, message, "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
