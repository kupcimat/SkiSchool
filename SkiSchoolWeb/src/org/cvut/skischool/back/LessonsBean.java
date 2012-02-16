package org.cvut.skischool.back;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.cvut.skischool.beans.LessonManagementLocal;
import org.cvut.skischool.model.Instructor;
import org.cvut.skischool.model.Lesson;
import org.cvut.skischool.model.Student;

/**
 *
 * @author matej
 */
@ManagedBean(name = "lessonsBean")
@SessionScoped
public class LessonsBean implements Serializable {

    @EJB
    private LessonManagementLocal lessonManagement;
    private DataModel<Lesson> lessons;
    private SelectItem[] executedLessonOptions;
    private SelectItem[] lessonTypeOptions;

    public LessonsBean() {
        String[] data = {"true", "false"};
        String[] names = {"Prebehli", "Neprebehli"};
        String[] lessonTypeDate = {"individual", "group", "kindergarten"};
        String[] lessonTypeNames = {"Individuálne", "Skupinové", "Škôlka"};
//        String[] names = {"Executed", "Not executed"};
        executedLessonOptions = createFilterOptions(data, names);
        lessonTypeOptions = createFilterOptions(lessonTypeDate, lessonTypeNames);
    }

    public SelectItem[] getLessonTypeOptions() {
        return lessonTypeOptions;
    }

    public SelectItem[] getExecutedLessonOptions() {
        return executedLessonOptions;
    }

    public DataModel<Lesson> getAllLessons() {
        lessons = new ListDataModel<Lesson>(lessonManagement.getAllLessons());
        return lessons;
    }

    public List<SelectItem> getInstructorsSelectList() {
        List<SelectItem> instructorsList = new ArrayList<SelectItem>();
        for (Instructor i : lessons.getRowData().getInstructors()) {
            instructorsList.add(new SelectItem(i, i.getFirstName() + " " + i.getLastName()));
        }
        return instructorsList;
    }

    public List<SelectItem> getStudentsSelectList() {
        List<SelectItem> studentsList = new ArrayList<SelectItem>();
        for (Student s : lessons.getRowData().getStudents()) {
            studentsList.add(new SelectItem(s, s.getFirstName() + " " + s.getLastName()));
        }
        return studentsList;
    }

    private SelectItem[] createFilterOptions(String[] data, String[] names) {
        SelectItem[] result = new SelectItem[data.length + 1];

//        result[0] = new SelectItem("", "All");
        result[0] = new SelectItem("", "Všetky");
        for (int i = 0; i < data.length; i++) {
            result[i + 1] = new SelectItem(data[i], names[i]);
        }

        return result;
    }
}
