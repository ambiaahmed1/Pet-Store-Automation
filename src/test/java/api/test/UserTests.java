package api.test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class UserTests {

    Faker faker;
    User user;

    public Logger logger;

    @BeforeClass
    public void setup() {

        faker = new Faker();
        user = new User();
        user.setId(faker.idNumber().hashCode());
        user.setUsername(faker.name().username());
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(faker.internet().password());
        user.setPhone(faker.phoneNumber().cellPhone());

        //logs
        logger = LogManager.getLogger(this.getClass());

    }

    @Test(priority = 1)
    public void testPostUser() {
        logger.info("Creating user...");

        Response response = UserEndPoints.createUser(user);

        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test(priority = 2)
    public void getUserByName() {
        logger.info("Reading user...");

        Response response = UserEndPoints.readUser(user.getUsername());

        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    public void updateUserByName() {
        logger.info("Updating user...");

        //update data using payload
        user.setFirstName(faker.name().firstName());
        user.setLastName(faker.name().lastName());
        user.setEmail(faker.internet().emailAddress());

        Response response = UserEndPoints.updateUser(user.getUsername(), user);

        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

        Response responseAfterUpdate = UserEndPoints.readUser(user.getUsername());

        Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);

        ResponseBody body = responseAfterUpdate.getBody();
        String bodyStringVal = body.asString();

        Assert.assertTrue(bodyStringVal.contains("firstName"));

        JsonPath jsonPathEval = responseAfterUpdate.jsonPath();

        String firstName = jsonPathEval.get("firstName");
        String lastName = jsonPathEval.get("lastName");
        String email = jsonPathEval.get("email");

        //Assert for updated values from responseAfterUpdate
        Assert.assertTrue(firstName.equalsIgnoreCase(user.getFirstName()));
        Assert.assertTrue(lastName.equalsIgnoreCase(user.getLastName()));
        Assert.assertTrue(email.equalsIgnoreCase(user.getEmail()));

    }

    @Test(priority = 4)
    public void deleteUserByName() {
        logger.info("Delete user...");

        Response response = UserEndPoints.deleteUser(user.getUsername());

        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);

    }
}
