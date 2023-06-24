package api.endpoints;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ResourceBundle;

import static io.restassured.RestAssured.given;

public class UserEndPoints {

    static ResourceBundle getURL() {
        ResourceBundle routes = ResourceBundle.getBundle("routes"); //load properties file
        return routes;
    }

    public static Response createUser(User payload) {

        String post_url = getURL().getString("post_url");

        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)

                .when()
                .log().all()
                .post(post_url);

        return response;
    }

    public static Response readUser(String userName) {
        Response response = given()
                .pathParam("username", userName)

                .when()
                .log().all()
                .get(Routes.get_url);

        return response;
    }

    public static Response updateUser(String userName, User payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("username", userName)
                .body(payload)

                .when()
                .log().all()
                .put(Routes.update_url);
        return response;
    }

    public static Response deleteUser(String userName) {
        Response response = given()
                .pathParam("username", userName)

                .when()
                .delete(Routes.delete_url);

        return response;
    }
}
