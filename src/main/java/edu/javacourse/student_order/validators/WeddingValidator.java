package edu.javacourse.student_order.validators;

import edu.javacourse.student_order.domain.wedding.AnswerWedding;
import edu.javacourse.student_order.domain.StudentOrder;

public class WeddingValidator {
    String hostName;
    String login;
    String password;

    public WeddingValidator(String hostName, String login, String password) {
        this.hostName = hostName;
        this.login = login;
        this.password = password;
    }

    public AnswerWedding checkWedding(StudentOrder studentOrder) {
        System.out.println("CheckWedding " + this.hostName + ", login: " + this.login +
                ", password: " + this.password);
        // проверяем свидетельство о регистрации брака по номеру и фио, через реестр (AnswerWedding принимает поля и идет проверять в реестр - возвращает ответ
        AnswerWedding wedding = new AnswerWedding();
        return wedding;
    }
}
