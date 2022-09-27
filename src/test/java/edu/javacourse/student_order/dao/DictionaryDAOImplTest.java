package edu.javacourse.student_order.dao;

import edu.javacourse.student_order.SaveStudentOrder;
import edu.javacourse.student_order.domain.entities.CountryArea;
import edu.javacourse.student_order.domain.entities.PassportOffice;
import edu.javacourse.student_order.domain.entities.RegisterOffice;
import edu.javacourse.student_order.domain.entities.Street;
import edu.javacourse.student_order.mail.exception.DAOException;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.List;

public class DictionaryDAOImplTest {
    private static final Logger logger = Logger.getLogger(SaveStudentOrder.class);
    @BeforeClass
    public static void startUp() throws Exception {
        DBInit.init();
    }

    @Test
    public void testStreet() throws DAOException {
        logger.info("log");
        List<Street> streets = new DictionaryDAOImpl().findStreet("сад");
        Assert.assertEquals(1, streets.size());
    }

    @Test
    public void testPassportOffices() throws DAOException {
        List<PassportOffice> passportOffices = new DictionaryDAOImpl().findPassportOffices("010020000000");
        Assert.assertEquals(2, passportOffices.size());
    }

    @Test
    public void testRegisterOffices() throws DAOException {
        List<RegisterOffice> registerOffices = new DictionaryDAOImpl().findRegisterOffices("010010000000");
        Assert.assertEquals(2, registerOffices.size());
    }

    @Test
    public void testCountryArea() throws DAOException {
        List<CountryArea> ca1 = new DictionaryDAOImpl().findAreas("");
        Assert.assertEquals(2, ca1.size());
        List<CountryArea> ca2 = new DictionaryDAOImpl().findAreas("020000000000");
        Assert.assertEquals(2, ca2.size());
        List<CountryArea> ca3 = new DictionaryDAOImpl().findAreas("020010000000");
        Assert.assertEquals(2, ca3.size());
        List<CountryArea> ca4 = new DictionaryDAOImpl().findAreas("020010010000");
        Assert.assertEquals(2, ca4.size());
    }


}