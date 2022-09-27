package edu.javacourse.student_order.validators;

import edu.javacourse.student_order.domain.students.AnswerStudents;
import edu.javacourse.student_order.domain.StudentOrder;

public class StudentValidator {
    String hostName;
    String login;
    String password;

    public StudentValidator(String hostName, String login, String password) {
        this.hostName = hostName;
        this.login = login;
        this.password = password;
    }

    public AnswerStudents checkStudent(StudentOrder studentOrder) {
        System.out.println("CheckStudent " + this.hostName + ", login: " + this.login +
                ", password: " + this.password);
        // берем id мужа и идем в реестр проверять, аналогично с женой (AnswerStudent будет принимать поля и пойдет в реестр)
        AnswerStudents answerStudents = new AnswerStudents();
        return answerStudents;
    }
}
