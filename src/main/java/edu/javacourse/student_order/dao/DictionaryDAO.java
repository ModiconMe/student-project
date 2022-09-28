package edu.javacourse.student_order.dao;

import edu.javacourse.student_order.domain.entities.CountryArea;
import edu.javacourse.student_order.domain.entities.PassportOffice;
import edu.javacourse.student_order.domain.entities.RegisterOffice;
import edu.javacourse.student_order.domain.entities.Street;
import edu.javacourse.student_order.exception.DAOException;

import java.util.List;

public interface DictionaryDAO {

    List<Street> findStreet(String pattern) throws DAOException;
    List<PassportOffice> findPassportOffices(String areaId) throws DAOException;
    List<RegisterOffice> findRegisterOffices(String areaId) throws DAOException;
    List<CountryArea> findAreas(String areaId) throws DAOException;
}
