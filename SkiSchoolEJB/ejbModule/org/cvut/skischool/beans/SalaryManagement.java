package org.cvut.skischool.beans;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.cvut.skischool.model.Instructor;
import org.cvut.skischool.model.Salary;

/**
 *
 * @author sabolmi1
 */
@Stateless
public class SalaryManagement implements SalaryManagementLocal {

    @PersistenceContext
    EntityManager em;

    @Override
    public void createSalary(Salary salary) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateSalary(Salary salary) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteSalary(Salary salary) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Salary> getInstructorSalariesPerTime(Instructor instructor, Date startDay, Date endDay) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Salary> getInstructorSalaries(Instructor instructor) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Salary> getAllSalariesPerTime(Date startDay, Date endDay) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
