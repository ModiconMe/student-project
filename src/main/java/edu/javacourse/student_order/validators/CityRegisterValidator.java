package edu.javacourse.student_order.validators;

import edu.javacourse.student_order.domain.entities.Person;
import edu.javacourse.student_order.domain.register.AnswerCityRegister;
import edu.javacourse.student_order.domain.register.AnswerCityRegisterItem;
import edu.javacourse.student_order.domain.register.CityRegisterResponse;
import edu.javacourse.student_order.domain.StudentOrder;
import edu.javacourse.student_order.exception.CityRegisterException;
import edu.javacourse.student_order.validators.register.CityRegisterChecker;
import edu.javacourse.student_order.validators.register.RealCityRegisterChecker;

public class CityRegisterValidator {
    private CityRegisterChecker checker;
    public static final String IN_CODE = "NO_GRN";

        public CityRegisterValidator() {
        this.checker = new RealCityRegisterChecker();
    }

    public AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
        AnswerCityRegister answer = new AnswerCityRegister();
        answer.addItem(checkPerson(studentOrder.getHusband()));
        answer.addItem(checkPerson(studentOrder.getWife()));
        studentOrder.getChildren().forEach(child -> answer.addItem(checkPerson(child)));
        return answer;
    }

    private AnswerCityRegisterItem checkPerson(Person person) {
            AnswerCityRegisterItem.Status status = null;
            AnswerCityRegisterItem.Error error = null;
        try {
            CityRegisterResponse response = checker.checkPerson(person);
            if (response == null) {
                throw new NullPointerException("There is null in " + person.toString());
            }
            status = response.isRegistered() ?
                    AnswerCityRegisterItem.Status.REGISTER :
                    AnswerCityRegisterItem.Status.NOT_REGISTER;
        } catch (CityRegisterException e) {
            e.printStackTrace(System.out);
            status = AnswerCityRegisterItem.Status.ERROR;
            error = new AnswerCityRegisterItem.Error(IN_CODE, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            status = AnswerCityRegisterItem.Status.ERROR;
            error = new AnswerCityRegisterItem.Error(IN_CODE, e.getMessage());
        }
        AnswerCityRegisterItem answer = new AnswerCityRegisterItem(person, status, error);
        return answer;
    }


}
