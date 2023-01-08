package rest_assured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.hamcrest.Matchers;


import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestAssuredAPI {

    public static Response getRequest(String endpoint){

        RestAssured.defaultParser = Parser.JSON;

        return given().headers("ContentType", ContentType.JSON, "Accept", ContentType.JSON)
                .when().get(endpoint)
                .then().contentType(ContentType.JSON).extract().response();

    }

    public static void main(String[] args) {

        Response response = getRequest("https://reqres.in/api/users");

        List<String> jsonResponse = response.jsonPath().getList("data");

        System.out.println(jsonResponse.size());

        //if we want to see all the first_name of all entries as an array
        String fn = response.jsonPath().getString("data.first_name");
        System.out.println(fn);

        //if we want to get only the first first_name
        String fnOne = response.jsonPath().getString("data.first_name[0]");
        System.out.println(fnOne);

        response.prettyPeek();

        response.then().assertThat().statusCode(HttpURLConnection.HTTP_OK).body("total", Matchers.equalTo(11));

    }
}
