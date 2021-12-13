package helpers;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Post;
import specifications.RequestSpecs;

import static io.restassured.RestAssured.given;

public class PostHelper {

    public static String getPostId() {

        Post newPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        // Preparación Headers/Body
        Response response = given()
                .spec(RequestSpecs.generateToken())
                .body(newPost)
                // Ejecucion
                .when()
                .post("/v1/post");

        JsonPath jsonPathEvaluator = response.jsonPath();
        String postId = jsonPathEvaluator.get("id").toString();

        return postId;
    }

}
