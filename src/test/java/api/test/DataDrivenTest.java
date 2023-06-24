package api.test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DataDrivenTest {


    @Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
    public void testPostuser(String userID, String userName, String fname, String lname, String useremail, String password, String phnumber) {

        User userPayload = new User();

        userPayload.setId(Integer.parseInt(userID));
        userPayload.setUsername(userName);
        userPayload.setFirstName(fname);
        userPayload.setLastName(lname);
        userPayload.setEmail(useremail);
        userPayload.setPassword(password);
        userPayload.setPhone(phnumber);

        Response response = UserEndPoints.createUser(userPayload);
        response.then().log().body();
        Assert.assertEquals(response.getStatusCode(), 200);


    }

    @Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testDeleteuser(String userName) {

        Response response = UserEndPoints.deleteUser(userName);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

}
