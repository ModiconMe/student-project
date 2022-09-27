package edu.javacourse.student_order.domain;

import edu.javacourse.student_order.domain.entities.Adult;
import edu.javacourse.student_order.domain.entities.Child;
import edu.javacourse.student_order.domain.entities.RegisterOffice;
import edu.javacourse.student_order.domain.entities.StudentOrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class StudentOrder {
    private long studentOrderID;
    private StudentOrderStatus studentOrderStatus;
    private LocalDateTime studentOrderDate;
    private Adult husband;
    private Adult wife;
    private List<Child> children = new LinkedList<>();
    private String marriageCertificateId;
    private LocalDate marriageDate;
    private RegisterOffice marriageOffice;

    public long getStudentOrderID() {
        return studentOrderID;
    }

    public void setStudentOrderID(long studentOrderID) {
        this.studentOrderID = studentOrderID;
    }

    public StudentOrderStatus getStudentOrderStatus() {
        return studentOrderStatus;
    }

    public void setStudentOrderStatus(StudentOrderStatus studentOrderStatus) {
        this.studentOrderStatus = studentOrderStatus;
    }

    public LocalDateTime getStudentOrderDate() {
        return studentOrderDate;
    }

    public void setStudentOrderDate(LocalDateTime studentOrderDate) {
        this.studentOrderDate = studentOrderDate;
    }

    public Adult getHusband() {
        return husband;
    }

    public void setHusband(Adult husband) {
        this.husband = husband;
    }

    public Adult getWife() {
        return wife;
    }

    public void setWife(Adult wife) {
        this.wife = wife;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public void addChild(Child child) {
        this.children.add(child);
    }

    public String getMarriageCertificateId() {
        return marriageCertificateId;
    }

    public void setMarriageCertificateId(String marriageCertificateId) {
        this.marriageCertificateId = marriageCertificateId;
    }

    public LocalDate getMarriageDate() {
        return marriageDate;
    }

    public void setMarriageDate(LocalDate marriageDate) {
        this.marriageDate = marriageDate;
    }

    public RegisterOffice getMarriageOffice() {
        return marriageOffice;
    }

    public void setMarriageOffice(RegisterOffice marriageOffice) {
        this.marriageOffice = marriageOffice;
    }

    @Override
    public String toString() {
        return "StudentOrder{" +
                "studentOrderID=" + studentOrderID +
                ", husband=" + husband +
                ", wife=" + wife +
                ", children=" + children +
                ", marriageCertificate='" + marriageCertificateId + '\'' +
                ", marriageDate=" + marriageDate +
                ", marriageOffice='" + marriageOffice + '\'' +
                '}';
    }
}
