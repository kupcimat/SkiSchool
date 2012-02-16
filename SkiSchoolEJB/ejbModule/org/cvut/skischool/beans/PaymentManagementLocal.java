package org.cvut.skischool.beans;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import org.cvut.skischool.model.Payment;
import org.cvut.skischool.model.Student;

/**
 *
 * @author matej
 */
@Local
public interface PaymentManagementLocal {

    void createPayment(Payment payment);

    void updatePayment(Payment payment);

    void deletePayment(Payment payment);

    List<Payment> getStudentPayments(Student student);

    List<Payment> getAllPaymentsPerTime(Student student, Date startTime, Date endTime);

    List<Payment> getAllUnpaidPayments();
    
    List<Payment> getUnpaidPaymentsByStudent(Student student);
}
