package edu.javacourse.student_order.validators;

import edu.javacourse.student_order.domain.children.AnswerChildren;
import edu.javacourse.student_order.domain.StudentOrder;

public class ChildrenValidator {
    String hostName;
    String login;
    String password;

    public ChildrenValidator(String hostName, String login, String password) {
        this.hostName = hostName;
        this.login = login;
        this.password = password;
    }

    public AnswerChildren checkChildren(StudentOrder studentOrder) {
        System.out.println("CheckChildren " + this.hostName + ", login: " + this.login +
                ", password: " + this.password);
        // проверяем свидетельство о рождении ребенка по номеру и фио родителей (AnswerChildren будет принимать на вход все нужные поля ?)
        AnswerChildren children = new AnswerChildren();
        return children;
    }
}
