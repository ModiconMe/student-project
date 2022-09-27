package edu.javacourse.student_order.domain.entities;

import java.time.LocalDate;

public class Adult extends Person {
    private String passportSeries;
    private String passportNumber;
    private LocalDate issueDate;
    private PassportOffice issueDepartment;
    private University university;
    private String studentID;

    public Adult(String surname, String givenName, String patronymic, LocalDate dateOfBirth) {
        super(surname, givenName, patronymic, dateOfBirth);
    }

    public String getPassportSeries() {
        return passportSeries;
    }

    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public PassportOffice getIssueDepartment() {
        return issueDepartment;
    }

    public void setIssueDepartment(PassportOffice issueDepartment) {
        this.issueDepartment = issueDepartment;
    }

    public University getUniversity() {
        return university;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    @Override
    public String toString() {
        return "Adult{" +
                "passportSeries='" + passportSeries + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", issueDate=" + issueDate +
                ", issueDepartment='" + issueDepartment + '\'' +
                ", universityName='" + university + '\'' +
                ", studentID='" + studentID + '\'' +
                "} " + super.toString();
    }
}
