package edu.javacourse.student_order;

import edu.javacourse.student_order.dao.StudentOrderDAOImpl;
import edu.javacourse.student_order.domain.*;
import edu.javacourse.student_order.domain.children.AnswerChildren;
import edu.javacourse.student_order.domain.register.AnswerCityRegister;
import edu.javacourse.student_order.domain.students.AnswerStudents;
import edu.javacourse.student_order.domain.wedding.AnswerWedding;
import edu.javacourse.student_order.mail.exception.DAOException;
import edu.javacourse.student_order.mail.MailSender;
import edu.javacourse.student_order.validators.*;

import java.util.List;

public class StudentOrderValidator {
    private final ChildrenValidator childrenValidator;
    private final CityRegisterValidator cityRegisterValidator;
    private final StudentValidator studentValidator;
    private final WeddingValidator weddingValidator;
    private final MailSender mailSender;

    public StudentOrderValidator() {
        childrenValidator = new ChildrenValidator("Host1", "Login1", "Password1");
        cityRegisterValidator = new CityRegisterValidator();
        studentValidator = new StudentValidator("Host1", "Login1", "Password1");
        weddingValidator = new WeddingValidator("Host1", "Login1", "Password1");
        mailSender = new MailSender();
    }
    public static void main(String[] args) {
        StudentOrderValidator studentOrderValidator = new StudentOrderValidator();
        studentOrderValidator.checkAll();
    }

    public void checkAll() {
        List<StudentOrder> studentOrders = null;
        try {
            studentOrders = readStudentOrders();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        studentOrders.forEach((this::checkOneOrder));
    }

    public void checkOneOrder(StudentOrder studentOrder) {
        AnswerCityRegister cityAnswer = checkCityRegister(studentOrder);
        System.out.println(cityAnswer);
//        AnswerWedding wedding = checkWedding(studentOrder);
//        AnswerChildren children = checkChildren(studentOrder);
//        AnswerStudents student = checkStudent(studentOrder);
//        sendMail(studentOrder);
    }
    static List<StudentOrder> readStudentOrders() throws DAOException {
        return new StudentOrderDAOImpl().getStudentOrders();
    }

    public AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
        return cityRegisterValidator.checkCityRegister(studentOrder);
    }

    public AnswerWedding checkWedding(StudentOrder studentOrder) {
        return weddingValidator.checkWedding(studentOrder);
    }

    public AnswerChildren checkChildren(StudentOrder studentOrder) {
        return childrenValidator.checkChildren(studentOrder);
    }

    public AnswerStudents checkStudent(StudentOrder studentOrder) {
        return studentValidator.checkStudent(studentOrder);
    }

    public void sendMail(StudentOrder studentOrder) {
        mailSender.sendMail(studentOrder);
    }
}
