package edu.javacourse.student_order.dao;

import edu.javacourse.student_order.domain.StudentOrder;
import edu.javacourse.student_order.domain.entities.*;
import edu.javacourse.student_order.exception.DAOException;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class StudentOrderDAOImplTest {
    private static final Logger logger = Logger.getLogger(StudentOrderDAOImplTest.class);

    @BeforeClass
    public static void startUp() throws Exception {
        DBInit.init();
        logger.debug("DB is start");
    }

    @Test
    public void saveStudentOrder() throws DAOException {
        StudentOrder studentOrder = buildStudentOrder(10);
        new StudentOrderDAOImpl().saveStudentOrder(studentOrder);
    }

    @Test(expected = DAOException.class)
    public void saveStudentOrderError() throws DAOException {
        StudentOrder studentOrder = buildStudentOrder(10);
        studentOrder.getHusband().setSurname(null);
        new StudentOrderDAOImpl().saveStudentOrder(studentOrder);
    }

    @Test
    public void getStudentOrders() throws DAOException {
        List<StudentOrder> studentOrders = new StudentOrderDAOImpl().getStudentOrders();

    }

    public StudentOrder buildStudentOrder(long id) {
        StudentOrder studentOrder = new StudentOrder();
        studentOrder.setStudentOrderID(id);
        studentOrder.setStudentOrderDate(LocalDateTime.of(2000, 8, 14, 14, 12, 55));
        studentOrder.setMarriageCertificateId("" + (123456000 + id));
        studentOrder.setMarriageDate(LocalDate.of(2016, 7, 4));

        RegisterOffice registerOffice = new RegisterOffice(1L, "", "");
        studentOrder.setMarriageOffice(registerOffice);

        Street street = new Street(1L, "First street");
        Address address = new Address("195000", street, "10", "2", "121");

        Adult husband = new Adult("Popov", "Dmitry", "Olegovich",
                LocalDate.of(1995, 3, 18));
        husband.setPassportSeries("" + (1000 + id));
        husband.setPassportNumber("" + (100000 + id));
        husband.setIssueDate(LocalDate.of(2017, 9, 5));
        PassportOffice passportOffice1 = new PassportOffice(1L, "", "");
        husband.setIssueDepartment(passportOffice1);
        husband.setStudentID("" + (100000 + id));
        husband.setAddress(address);
        husband.setUniversity(new University(2L, ""));
        husband.setStudentID("HH12345");

        Adult wife = new Adult("Popova", "Polina", "Igorevna",
                LocalDate.of(1997, 8, 21));
        wife.setPassportSeries("" + (2000 + id));
        wife.setPassportNumber("" + (200000 + id));
        wife.setIssueDate(LocalDate.of(2018, 4, 5));
        PassportOffice passportOffice2 = new PassportOffice(2L, "", "");
        wife.setIssueDepartment(passportOffice2);
        wife.setStudentID("" + (200000 + id));
        wife.setAddress(address);
        wife.setUniversity(new University(1L, ""));
        wife.setStudentID("WW12345");

        Child child1 = new Child("Popova", "Yulia", "Dmitryevna",
                LocalDate.of(2016, 1, 11));
        child1.setCertificateNumber("" + (300000 + id));
        child1.setIssueDate(LocalDate.of(2018, 6, 11));
        RegisterOffice registerOffice1 = new RegisterOffice(2L, "", "");
        child1.setIssueDepartment(registerOffice1);
        child1.setAddress(address);

        Child child2 = new Child("Popov", "Yuri", "Dmitryev",
                LocalDate.of(2018, 10, 11));
        child2.setCertificateNumber("" + (400000 + id));
        child2.setIssueDate(LocalDate.of(2018, 7, 19));
        RegisterOffice registerOffice2 = new RegisterOffice(3L, "", "");
        child2.setIssueDepartment(registerOffice2);
        child2.setAddress(address);
        List<Child> children = List.of(child1, child2);
        studentOrder.setHusband(husband);
        studentOrder.setWife(wife);
        studentOrder.setChildren(children);
        System.out.println(studentOrder);
        return studentOrder;
    }
}