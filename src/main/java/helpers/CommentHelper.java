package helpers;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Comment;
import specifications.RequestSpecs;

import static io.restassured.RestAssured.given;

public class CommentHelper {

    public static String getCommentId(String postId) {

        Comment newComment = new Comment(DataHelper.generateRandomCommentName(), DataHelper.generateRandomComment());

        // Preparación Headers/Body
        Response response = given()
                .spec(RequestSpecs.generateBasicAuth())
                .body(newComment)
                // Ejecucion
                .when()
                .post("/v1/comment/" + postId);

        JsonPath jsonPathEvaluator = response.jsonPath();
        String commentId = jsonPathEvaluator.get("id").toString();

        return commentId;
    }

    public static void setPostIdWithComment(String postId) {

        Comment newComment = new Comment(DataHelper.generateRandomCommentName(), DataHelper.generateRandomComment());

        // Preparación Headers/Body
        Response response = given()
                .spec(RequestSpecs.generateBasicAuth())
                .body(newComment)
                // Ejecucion
                .when()
                .post("/v1/comment/" + postId);
    }
}
