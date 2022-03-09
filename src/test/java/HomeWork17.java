import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;


public class HomeWork17 {

    @Test
    public void chekListUsers() {

        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("total", is(12));
    }

    @Test
    public void chekSingleUser() {

        given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.first_name", is("Janet"))
                .body("support.text", is("To keep ReqRes free, contributions towards server costs are appreciated!"));
    }

    @Test
    public void notFoundSingleUser() {

        given()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .statusCode(404);
    }

    @Test
    public void chekListResource() {

        given()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .statusCode(200)
                .body("total", is(12))
                .body("total_pages", is(2))
                .body("data.name[1]", is("fuchsia rose"))
                .body("data.year[1]", is(2001));
    }

    @Test
    public void chekSingleResource() {

        given()
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .statusCode(200)
                .body("data.id", is(2))
                .body("data.color", is("#C74375"));
    }

    @Test
    public void singleResourceNotFound() {

        given()
                .when()
                .get("https://reqres.in/api/unknown/23")
                .then()
                .statusCode(404);
    }

    @Test
    public void createUser() {

        Map<String, String> user = new HashMap<>();
        user.put("name", "morpheus");
        user.put("job", "leader");

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }

    @Test
    public void updateUser() {

        Map<String, String> user = new HashMap<>();
        user.put("name", "morpheus");
        user.put("job", "zion resident");

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    public void updateUser2() {

        Map<String, String> user = new HashMap<>();
        user.put("name", "morpheus");
        user.put("job", "zion resident");

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    public void deleteUser() {

        given()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    public void registerSuccessful() {

        Map<String, String> user = new HashMap<>();
        user.put("email", "eve.holt@reqres.in");
        user.put("password", "pistol");

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }


    @Test
    public void registerUnsuccessful() {

        Map<String, String> user = new HashMap<>();
        user.put("email", "sydney@fife");
        user.put("password", "");

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    public void loginSuccessful() {

        Map<String, String> user = new HashMap<>();
        user.put("email", "eve.holt@reqres.in");
        user.put("password", "cityslicka");

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    public void loginUnsuccessful() {

        Map<String, String> user = new HashMap<>();
        user.put("email", "peter@klaven");
        user.put("password", "");

        given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    public void delayedResponse() {


        given()
                .when()
                .get("https://reqres.in/api/users?delay=3")
                .then()
                .statusCode(200)
                .body("per_page", is(6))
                .body("data.first_name[2]", is("Emma"))
                .body("data.email[2]", is("emma.wong@reqres.in"));
    }

}
