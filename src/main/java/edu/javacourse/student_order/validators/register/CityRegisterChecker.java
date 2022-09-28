package edu.javacourse.student_order.validators.register;

import edu.javacourse.student_order.domain.register.CityRegisterResponse;
import edu.javacourse.student_order.domain.entities.Person;
import edu.javacourse.student_order.exception.CityRegisterException;

public interface CityRegisterChecker {
    CityRegisterResponse checkPerson(Person person) throws CityRegisterException;
}
