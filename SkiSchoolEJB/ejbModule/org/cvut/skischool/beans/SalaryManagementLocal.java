package org.cvut.skischool.beans;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import org.cvut.skischool.model.Instructor;
import org.cvut.skischool.model.Salary;

/**
 *
 * @author sabolmi1
 */
@Local
public interface SalaryManagementLocal {

    void createSalary(Salary salary);

    //no neviem
    void updateSalary(Salary salary);

    void deleteSalary(Salary salary);

    List<Salary> getInstructorSalaries(Instructor instructor);

    List<Salary> getInstructorSalariesPerTime(Instructor instructor, Date startDay, Date endDay);

    List<Salary> getAllSalariesPerTime(Date startDay, Date endDay);
}
