import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ReqresTestsWithoutPojo {
    private final static String URL = "https://reqres.in/";


    @Test
    @DisplayName("Убедиться , что имена файлов-автоаров пользователей совпадают, коночание email @reqres.in")
    public void chekAvatarAndIdTest1(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        Response response = (Response) given()
                .when()
                .get("api/users?page=2")
                .then().log().all()
                .body("page", equalTo(2))
                .body("data.id", notNullValue())
                .body("data.email", notNullValue())
                .body("data.first_name", notNullValue())
                .body("data.last_name", notNullValue())
                .body("data.avatar", notNullValue())
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        List<String> emails = jsonPath.get("data.email");
        List<Integer> ids = jsonPath.get("data.id");
        List<String> avatars = jsonPath.get("data.avatar");

        for (int i=0; i < avatars.size(); i++) {
            Assertions.assertTrue(avatars.get(i).contains(ids.get(i).toString()));
        }
        Assertions.assertTrue(emails.stream().allMatch(x-> x.endsWith("@reqres.in")));

    }

    @Test
    @DisplayName("Запрос на успешную регистрацию")
    public void seccessRegTest1(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        Map<String, String> user = new HashMap<>();
        user.put("email", "eve.holt@reqres.in");
        user.put("password", "pistol");

        Response response = given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .body("id", equalTo(4))
                .body("token", equalTo("QpwL5tke4Pnpja7X4"))
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        int id = jsonPath.get("id");
        String token = jsonPath.get("token");
        assertEquals(4, id);
        assertEquals("QpwL5tke4Pnpja7X4", token);


    }
    @Test
    @DisplayName("Запрос на неуспешную регистрацию")
    public void unseccessRegTest1(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecError400());

        Map<String, String> user = new HashMap<>();
        user.put("email", "eve.holt@reqres.in");
        user.put("password", "");

        given()
                .body(user)
                .when()
                .post("api/register")
                .then().log().all()
                .body("error", equalTo("Missing password"));

    }


    @Test
    @DisplayName("Удаление пользователя, проверяем , что статус метода Делет 204")
    public void deleteUserTest1(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(204));
        given()
                .when()
                .delete("api/users/2")
                .then().log().all();


    }

    @Test
    @DisplayName("Обновить инфу о пользователе")
    public void updateUserTest1(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        Map<String, String> user = new HashMap<>();
        user.put("name", "morpheus");
        user.put("job", "zion resident");
        String response = given()
                .body(user)
                .when()
                .put("api/users/2")
                .then().log().all()
                .extract().path("name");

        assertEquals("morpheus", response);


    }



}
