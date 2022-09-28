package edu.javacourse.student_order.validator.register;

import edu.javacourse.student_order.domain.register.CityRegisterResponse;
import edu.javacourse.student_order.domain.entities.Adult;
import edu.javacourse.student_order.domain.entities.Child;
import edu.javacourse.student_order.domain.entities.Person;
import edu.javacourse.student_order.exception.CityRegisterException;
import edu.javacourse.student_order.validators.register.CityRegisterChecker;

public class FakeCityRegisterChecker implements CityRegisterChecker {
    public static final String REGISTER_SERIES = "1000";
    public static final String NOT_REGISTER_SERIES = "1001";
    public static final String ERROR_SERIES = "1002";
    @Override
    public CityRegisterResponse checkPerson(Person person) throws CityRegisterException {
        CityRegisterResponse response = new CityRegisterResponse();
        if (person instanceof Adult adult) {
            switch (adult.getPassportNumber()) {
                case REGISTER_SERIES -> {
                    response.setRegistered(true);
                    response.setTemporal(false);
                    return response;
                }
                case NOT_REGISTER_SERIES -> {
                    response.setRegistered(false);
                    return response;
                }
                case ERROR_SERIES -> {
                    throw new CityRegisterException("1", "CityRegister exception");
                }
            }
        }
        if (person instanceof Child child) {
            switch (child.getCertificateNumber()) {
                case REGISTER_SERIES -> {
                    response.setRegistered(true);
                    response.setTemporal(false);
                    return response;
                }
                case NOT_REGISTER_SERIES -> {
                    response.setRegistered(false);
                    return response;
                }
                case ERROR_SERIES -> {
                    throw new CityRegisterException("1", "CityRegister exception");
                }
            }
        }
        return null;
    }
}
