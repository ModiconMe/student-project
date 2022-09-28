package edu.javacourse.student_order.dao;

import edu.javacourse.student_order.config.Config;
import edu.javacourse.student_order.domain.StudentOrder;
import edu.javacourse.student_order.domain.entities.*;
import edu.javacourse.student_order.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentOrderDAOImpl implements StudentOrderDAO {
    private static final Logger logger = Logger.getLogger(StudentOrderDAOImpl.class);
    private static final String INSERT_ORDER = """
            INSERT INTO jc_student_order(
            \tstudent_order_status, student_order_date, h_sur_name, h_given_name, h_patronymic, h_date_of_birth, h_passport_series, h_passport_number, h_passport_issue_date, h_passport_office_id, h_postcode, h_street_code, h_building, h_extension, h_apartment, h_university_id, h_student_number, w_sur_name, w_given_name, w_patronymic, w_date_of_birth, w_passport_series, w_passport_number, w_passport_issue_date, w_passport_office_id, w_postcode, w_street_code, w_building, w_extension, w_apartment, w_university_id, w_student_number, certificate_id, marriage_date, register_office_id)
            \tVALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);""";

    public static final String INSERT_CHILD = """
            INSERT INTO jc_student_child(
            \tstudent_order_id, c_sur_name, c_given_name, c_patronymic, c_date_of_birth, c_certificate_number, c_certificate_date, c_register_office_id, c_postcode, c_street_code, c_building, c_extension, c_apartment)
            \tVALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);""";

    private static final String SELECT_ORDERS = """
            SELECT so.*,
            ro.r_office_area_id, ro.r_office_name,
            po_h.p_office_area_id as h_office_area_id, po_h.p_office_name as h_office_name,
            po_w.p_office_area_id as w_office_area_id, po_w.p_office_name as w_office_name
            FROM jc_student_order so
            INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id
            INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_office_id
            INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.h_passport_office_id
            WHERE student_order_status = ? ORDER BY student_order_date LIMIT ?""";

    private static final String SELECT_CHILD = """
            SELECT soc.*, ro.r_office_area_id, ro.r_office_name
            FROM jc_student_child soc
            INNER JOIN jc_register_office ro ON ro.r_office_id = soc.c_register_office_id
            WHERE soc.student_order_id IN""";
    private static final String SELECT_ORDERS_FULL = """
            SELECT so.*,
            ro.r_office_area_id, ro.r_office_name,
            po_h.p_office_area_id as h_office_area_id, po_h.p_office_name as h_office_name,
            po_w.p_office_area_id as w_office_area_id, po_w.p_office_name as w_office_name,
            soc.*, ro_c.r_office_area_id, ro_c.r_office_name
            FROM jc_student_order so
            INNER JOIN jc_register_office ro ON ro.r_office_id = so.register_office_id
            INNER JOIN jc_passport_office po_h ON po_h.p_office_id = so.h_passport_office_id
            INNER JOIN jc_passport_office po_w ON po_w.p_office_id = so.w_passport_office_id
            INNER JOIN jc_student_child soc ON soc.student_order_id = so.student_order_id
            INNER JOIN jc_register_office ro_c ON ro_c.r_office_id = soc.c_register_office_id
            WHERE student_order_status = ? ORDER BY student_order_date LIMIT ?""";

    private Connection getConnection() throws SQLException {
        return ConnectionBuilder.getConnection();
    }

    @Override
    public Long saveStudentOrder(StudentOrder studentOrder) throws DAOException {
        long result = -1L;
        logger.debug("student order: " + studentOrder);
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ORDER, new String[]{"student_order_id"})) {

            connection.setAutoCommit(false);
            try {
                // Header
                statement.setInt(1, StudentOrderStatus.START.ordinal());
                statement.setTimestamp(2, java.sql.Timestamp.valueOf(studentOrder.getStudentOrderDate()));

                // set params for husband and wife
                setParamsForAdult(statement, 3, studentOrder.getHusband());
                setParamsForAdult(statement, 18, studentOrder.getWife());

                // Marriage
                statement.setString(33, studentOrder.getMarriageCertificateId());
                statement.setDate(34, java.sql.Date.valueOf(studentOrder.getMarriageDate()));
                statement.setLong(35, studentOrder.getMarriageOffice().getOfficeId());

                statement.executeUpdate();

                ResultSet gkRs = statement.getGeneratedKeys();
                if (gkRs.next()) {
                    result = gkRs.getLong(1);
                }
                saveChildren(connection, studentOrder, result);

                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DAOException(e);
        }
        return result;
    }

    private void saveChildren(Connection connection, StudentOrder studentOrder, Long soId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CHILD)) {
            for (Child c : studentOrder.getChildren()) {
                statement.setLong(1, soId);
                setParamsForChild(statement, c);
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void setParamsForPerson(PreparedStatement statement, int start, Person person) throws SQLException {
        statement.setString(start, person.getSurname());
        statement.setString(start + 1, person.getGivenName());
        statement.setString(start + 2, person.getPatronymic());
        statement.setDate(start + 3, Date.valueOf(person.getDateOfBirth()));
    }

    private void setParamsForAddress(PreparedStatement statement, int start, Person person) throws SQLException {
        Address personAddress = person.getAddress();
        statement.setString(start, personAddress.getPostcode());
        statement.setLong(start + 1, personAddress.getStreet().getStreetCode());
        statement.setString(start + 2, personAddress.getBuilding());
        statement.setString(start + 3, personAddress.getExtension());
        statement.setString(start + 4, personAddress.getApartment());
    }

    private void setParamsForAdult(PreparedStatement statement, int start, Adult adult) throws SQLException {

        setParamsForPerson(statement, start, adult);

        statement.setString(start + 4, adult.getPassportSeries());
        statement.setString(start + 5, adult.getPassportNumber());
        statement.setDate(start + 6, Date.valueOf(adult.getIssueDate()));
        statement.setLong(start + 7, adult.getIssueDepartment().getOfficeId());

        setParamsForAddress(statement, start + 8, adult);

        statement.setLong(start + 13, adult.getUniversity().getUniversityId());
        statement.setString(start + 14, adult.getStudentID());
    }

    private void setParamsForChild(PreparedStatement statement, Child c) throws SQLException {
        setParamsForPerson(statement, 2, c);

        statement.setString(6, c.getCertificateNumber());
        statement.setDate(7, Date.valueOf(c.getIssueDate()));
        statement.setLong(8, c.getIssueDepartment().getOfficeId());

        setParamsForAddress(statement, 9, c);
    }

    @Override
    public List<StudentOrder> getStudentOrders() throws DAOException {
//        return getStudentOrdersOneSelect();
        return getStudentOrdersTwoSelect();
    }

    private List<StudentOrder> getStudentOrdersOneSelect() throws DAOException {
        List<StudentOrder> result = new LinkedList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ORDERS_FULL)) {

            Map<Long, StudentOrder> map = new HashMap<>();

            statement.setInt(1, StudentOrderStatus.START.ordinal());

            int limit = Integer.parseInt(Config.getProperty(Config.DB_LIMIT));
            statement.setInt(2, limit);

            ResultSet resultSet = statement.executeQuery();
            int counter = 0;
            while (resultSet.next()) {
                Long soId = resultSet.getLong("student_order_id");
                if (!map.containsKey(soId)) {
                    StudentOrder studentOrder = getStudentOrder(resultSet);

                    result.add(studentOrder);
                    map.put(soId, studentOrder);
                }
                StudentOrder studentOrder = map.get(soId);
                studentOrder.addChild(fillChild(resultSet));
                counter++;
            }
            if (counter >= limit) {
                result.remove(result.size() - 1);
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DAOException(e);
        }
        return result;
    }

    private List<StudentOrder> getStudentOrdersTwoSelect() throws DAOException {
        List<StudentOrder> result = new LinkedList<>();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ORDERS)) {

            statement.setInt(1, StudentOrderStatus.START.ordinal());
            statement.setInt(2, Integer.parseInt(Config.getProperty(Config.DB_LIMIT)));
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                StudentOrder studentOrder = getStudentOrder(resultSet);

                result.add(studentOrder);
            }
            findChildren(connection, result);
            resultSet.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DAOException(e);
        }
        return result;
    }

    private StudentOrder getStudentOrder(ResultSet resultSet) throws SQLException {
        StudentOrder studentOrder = new StudentOrder();

        fillStudentOrder(resultSet, studentOrder);
        fillMarriage(resultSet, studentOrder);

        Adult husband = fillAdult(resultSet, "h_");
        Adult wife = fillAdult(resultSet, "w_");
        studentOrder.setHusband(husband);
        studentOrder.setWife(wife);
        return studentOrder;
    }

    private void fillStudentOrder(ResultSet resultSet, StudentOrder studentOrder) throws SQLException {
        studentOrder.setStudentOrderID(resultSet.getLong("student_order_id"));
        studentOrder.setStudentOrderDate((resultSet.getTimestamp("student_order_date")).toLocalDateTime());
        studentOrder.setStudentOrderStatus(StudentOrderStatus.fromValue(resultSet.getInt("student_order_status")));
    }

    private Adult fillAdult(ResultSet resultSet, String prefix) throws SQLException {
        Adult adult = new Adult(
                resultSet.getString(prefix + "sur_name"),
                resultSet.getString(prefix + "given_name"),
                resultSet.getString(prefix + "patronymic"),
                resultSet.getDate(prefix + "date_of_birth").toLocalDate());
        adult.setPassportSeries(resultSet.getString(prefix + "passport_series"));
        adult.setPassportNumber(resultSet.getString(prefix + "passport_number"));
        adult.setIssueDate(resultSet.getDate(prefix + "passport_issue_date").toLocalDate());
        adult.setIssueDepartment(new PassportOffice(
                resultSet.getLong(prefix + "passport_office_id"),
                resultSet.getString(prefix + "office_area_id"),
                resultSet.getString(prefix + "office_name")));
        Address address = new Address(
                resultSet.getString(prefix + "postcode"),
                new Street(
                        resultSet.getLong(prefix + "street_code"),
                        ""),
                resultSet.getString(prefix + "building"),
                resultSet.getString(prefix + "extension"),
                resultSet.getString(prefix + "apartment")
        );

        adult.setAddress(address);
        University university = new University(
                resultSet.getLong(prefix + "university_id"),
                "");

        adult.setUniversity(university);
        adult.setStudentID(resultSet.getString(prefix + "student_number"));
        return adult;
    }

    private void fillMarriage(ResultSet resultSet, StudentOrder studentOrder) throws SQLException {
        studentOrder.setMarriageCertificateId(resultSet.getString("certificate_id"));
        studentOrder.setMarriageDate((resultSet.getDate("marriage_date")).toLocalDate());

        RegisterOffice registerOffice = new RegisterOffice(
                resultSet.getLong("register_office_id"),
                resultSet.getString("r_office_area_id"),
                resultSet.getString("r_office_name"));
        studentOrder.setMarriageOffice(registerOffice);

    }

    private void findChildren(Connection connection, List<StudentOrder> result) throws DAOException {
        String s = "(" + result.stream().map(so -> String.valueOf(so.getStudentOrderID())).
                collect(Collectors.joining(", ")) + ")";

        Map<Long, StudentOrder> maps = result.stream().collect(Collectors
                .toMap(StudentOrder::getStudentOrderID, so -> so));

        try (PreparedStatement statement = connection.prepareStatement(SELECT_CHILD + s)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Child child = fillChild(resultSet);
                StudentOrder studentOrder = maps.get(resultSet.getLong("student_order_id"));
                studentOrder.addChild(child);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    private Child fillChild(ResultSet resultSet) throws SQLException {
        Child child = new Child(
                resultSet.getString("c_sur_name"),
                resultSet.getString("c_given_name"),
                resultSet.getString("c_patronymic"),
                resultSet.getDate("c_date_of_birth").toLocalDate());
        child.setIssueDate(resultSet.getDate("c_certificate_date").toLocalDate());
        child.setCertificateNumber(resultSet.getString("c_certificate_number"));
        child.setIssueDepartment(new RegisterOffice(
                resultSet.getLong("c_register_office_id"),
                resultSet.getString("r_office_area_id"),
                resultSet.getString("r_office_name")));
        Address address = new Address(
                resultSet.getString("c_postcode"),
                new Street(
                        resultSet.getLong("c_street_code"),
                        ""),
                resultSet.getString("c_building"),
                resultSet.getString("c_extension"),
                resultSet.getString("c_apartment")
        );
        child.setAddress(address);
        return child;
    }
}
