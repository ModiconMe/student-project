package edu.javacourse.student_order.domain.register;

import edu.javacourse.student_order.domain.entities.Person;

import java.util.ArrayList;
import java.util.List;

public class AnswerCityRegisterItem {

    public enum Status {REGISTER, NOT_REGISTER, ERROR};
    public static class Error {
        private String code;
        private String text;

        public Error(String code, String text) {
            this.code = code;
            this.text = text;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
    private Person person;
    private Status status;
    private Error error;

    public AnswerCityRegisterItem(Person person, Status status) {
        this.person = person;
        this.status = status;
    }

    public AnswerCityRegisterItem(Person person, Status status, Error error) {
        this.person = person;
        this.status = status;
        this.error = error;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "AnswerCityRegisterItem{" +
                "person=" + person +
                ", status=" + status +
                ", error=" + error +
                '}';
    }
}
