package org.cvut.skischool.beans;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import org.cvut.skischool.model.Payment;
import org.cvut.skischool.model.Student;

/**
 *
 * @author matej
 */
@Stateless
public class PaymentManagement implements PaymentManagementLocal {

    @Override
    public void createPayment(Payment payment) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updatePayment(Payment payment) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deletePayment(Payment payment) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Payment> getStudentPayments(Student student) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Payment> getAllPaymentsPerTime(Student student, Date startTime, Date endTime) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Payment> getAllUnpaidPayments() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Payment> getUnpaidPaymentsByStudent(Student student) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
