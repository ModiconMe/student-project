package edu.javacourse.student_order.dao;

import edu.javacourse.student_order.domain.StudentOrder;
import edu.javacourse.student_order.mail.exception.DAOException;

import java.util.List;

public interface StudentOrderDAO {
    Long saveStudentOrder(StudentOrder studentOrder) throws DAOException;
    List<StudentOrder> getStudentOrders() throws DAOException;
}
