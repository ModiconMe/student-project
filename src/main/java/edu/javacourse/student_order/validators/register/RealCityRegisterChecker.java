package edu.javacourse.student_order.validators.register;

import edu.javacourse.student_order.config.Config;
import edu.javacourse.student_order.domain.register.CityRegisterRequest;
import edu.javacourse.student_order.domain.register.CityRegisterResponse;
import edu.javacourse.student_order.domain.entities.Person;
import edu.javacourse.student_order.exception.CityRegisterException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

public class RealCityRegisterChecker implements CityRegisterChecker {
    @Override
    public CityRegisterResponse checkPerson(Person person) throws CityRegisterException {

        try {
            CityRegisterRequest request = new CityRegisterRequest(person);

            Client client = ClientBuilder.newClient();
            CityRegisterResponse response = client.target(Config.getProperty(Config.CR_URL))
                    .request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(request, MediaType.APPLICATION_JSON))
                    .readEntity(CityRegisterResponse.class);
            System.out.println(response);
            return response;
        } catch (Exception e) {
            throw new CityRegisterException("1", e.getMessage(), e);
        }
    }
}
