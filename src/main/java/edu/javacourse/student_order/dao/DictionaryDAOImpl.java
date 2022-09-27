package edu.javacourse.student_order.dao;

import edu.javacourse.student_order.domain.entities.CountryArea;
import edu.javacourse.student_order.domain.entities.PassportOffice;
import edu.javacourse.student_order.domain.entities.RegisterOffice;
import edu.javacourse.student_order.domain.entities.Street;
import edu.javacourse.student_order.mail.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DictionaryDAOImpl implements DictionaryDAO {
    private static final Logger logger = Logger.getLogger(DictionaryDAOImpl.class);
    private static final String GET_STREET =
            "SELECT * FROM jc_street WHERE UPPER(street_name) LIKE UPPER(?)";
    private static final String GET_REGISTER_OFFICE =
            "SELECT * FROM jc_register_office WHERE UPPER(r_office_area_id) LIKE UPPER(?)";
    private static final String GET_PASSPORT_OFFICE =
            "SELECT * FROM jc_passport_office WHERE UPPER(p_office_area_id) LIKE UPPER(?)";

    private static final String GET_AREA =
            "SELECT * FROM jc_country_struct WHERE area_id LIKE ? AND area_id <> ?";

    private Connection getConnection() throws SQLException {
        return ConnectionBuilder.getConnection();
    }

    @Override
    public List<Street> findStreet(String pattern) throws DAOException {
        List<Street> streets = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_STREET);) {
            statement.setString(1, "%" + pattern + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                streets.add(new Street(
                        resultSet.getLong("street_code"),
                        resultSet.getString("street_name")));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DAOException(e);
        }
        return streets;
    }

    @Override
    public List<PassportOffice> findPassportOffices(String areaId) throws DAOException {
        List<PassportOffice> passportOffices = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_PASSPORT_OFFICE);) {
            statement.setString(1, "%" + areaId + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                passportOffices.add(new PassportOffice(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3)));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DAOException(e);
        }
        return passportOffices;
    }

    @Override
    public List<RegisterOffice> findRegisterOffices(String areaId) throws DAOException {
        List<RegisterOffice> registerOffices = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_REGISTER_OFFICE);) {
            statement.setString(1, "%" + areaId + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                registerOffices.add(new RegisterOffice(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3)));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DAOException(e);
        }
        return registerOffices;
    }

    @Override
    public List<CountryArea> findAreas(String areaId) throws DAOException {
        List<CountryArea> countryAreas = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_AREA);) {
            String param1 = buildParam(areaId);
            String param2 = areaId;
            statement.setString(1, param1);
            statement.setString(2, param2);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                countryAreas.add(new CountryArea(
                        resultSet.getString(1),
                        resultSet.getString(2)));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DAOException(e);
        }
        return countryAreas;
    }

    private String buildParam(String areaId) throws SQLException {
        if (areaId == null || areaId.trim().isEmpty()) return "__0000000000";
        if (areaId.endsWith("0000000000")) return areaId.substring(0, 2) + "___0000000";
        if (areaId.endsWith("0000000")) return areaId.substring(0, 5) + "___0000";
        if (areaId.endsWith("0000")) return areaId.substring(0, 8) + "____";
        throw new SQLException("Invalid parametr areaId : " + areaId);
    }
}
