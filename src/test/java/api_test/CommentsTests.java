package api_test;

import helpers.CommentHelper;
import helpers.DataHelper;
import helpers.PostHelper;
import model.Comment;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import specifications.RequestSpecs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class CommentsTests extends BaseTest {

    String commentResourcePath = "/v1/comment/";
    String commentsResourcePath = "/v1/comments/";

    //Positive test - Create Comment
    @Test
    public void Test_Comment_Create() {

        String postId = PostHelper.getPostId();

        Comment newComment = new Comment(DataHelper.generateRandomCommentName(), DataHelper.generateRandomComment());

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateBasicAuth())
                .body(newComment)
                // Ejecucion
                .when()
                .post(commentResourcePath + postId)
                // Assertions / Verificaciones
                .then()
                .body("message", equalTo("Comment created"))
                .statusCode(200);
    }

    //Negative test - Create Comment - violates foreign key constraint
    @Test
    public void Test_Comment_Create_FK_Violation() {

        String postId = "0001";

        Comment newComment = new Comment(DataHelper.generateRandomCommentName(), DataHelper.generateRandomComment());

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateBasicAuth())
                .body(newComment)
                // Ejecucion
                .when()
                .post(commentResourcePath + postId)
                // Assertions / Verificaciones
                .then()
                .body("message", equalTo("Comment could not be created"))
                .body("error", equalTo("pq: insert or update on table "+'"'+"comment"+'"'+" violates foreign key constraint "+'"'+"comment_post_id_fkey"+'"'))
                .statusCode(406);
    }

    //Positive test - Get Comment All
    @Test
    public void Test_Comment_All() {

        String postId = PostHelper.getPostId();
        CommentHelper.setPostIdWithComment(postId);

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateBasicAuth())
                // Ejecucion
                .when()
                .get(commentsResourcePath + postId)
                // Assertions / Verificaciones
                .then()
                .body("results", Matchers.notNullValue())
                .statusCode(200);
    }

    //Negative test - Comment All - Invalid Authentication
    @Test
    public void Test_Comment_All_Invalid_Auth() {

        String postId = PostHelper.getPostId();
        CommentHelper.setPostIdWithComment(postId);

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateInvalidBasicAuth())
                // Ejecucion
                .when()
                .get(commentsResourcePath + postId)
                // Assertions / Verificaciones
                .then()
                .body("results", Matchers.nullValue())
                .statusCode(401);
    }

    //Positive test - Comment One
    @Test
    public void Test_Comment_One(){

        String postId = PostHelper.getPostId();
        String commentId = CommentHelper.getCommentId(postId);

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateBasicAuth())
                // Ejecucion
                .when()
                .get(commentResourcePath + postId + "/" + commentId)
                // Assertions / Verificaciones
                .then()
                .body("data", Matchers.notNullValue())
                .statusCode(200);
    }

    //Negative test - Comment One - Comment Not Found
    @Test
    public void Test_Comment_One_Not_Found(){

        String postId = PostHelper.getPostId();
        String commentId = "0001";

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateBasicAuth())
                // Ejecucion
                .when()
                .get(commentResourcePath + postId + "/" + commentId)
                // Assertions / Verificaciones
                .then()
                .body("Message", equalTo("Comment not found"))
                .body("error", equalTo("sql: no rows in result set"))
                .statusCode(404);
    }

    //Positive test - Comment Update
    @Test
    public void Test_Comment_Update(){

        String postId = PostHelper.getPostId();
        String commentId = CommentHelper.getCommentId(postId);
        Comment newComment = new Comment(DataHelper.generateRandomCommentName(), DataHelper.generateRandomComment());

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateBasicAuth())
                .body(newComment)
                // Ejecucion
                .when()
                .put(commentResourcePath + postId + "/" + commentId)
                // Assertions / Verificaciones
                .then()
                .body("message", equalTo("Comment updated"))
                .statusCode(200);
    }

    //Negative test - Comment Update - Comment Not Found
    @Test
    public void Test_Comment_Update_Not_Found(){

        String postId = PostHelper.getPostId();
        String commentId = "0001";
        Comment newComment = new Comment(DataHelper.generateRandomCommentName(), DataHelper.generateRandomComment());

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateBasicAuth())
                .body(newComment)
                // Ejecucion
                .when()
                .put(commentResourcePath + postId + "/" + commentId)
                // Assertions / Verificaciones
                .then()
                .body("message", equalTo("Comment could not be updated"))
                .body("error", equalTo("Comment not found"))
                .statusCode(406);
    }

    //Positive test - Comment Delete
    @Test
    public void Test_Comment_Delete(){

        String postId = PostHelper.getPostId();
        String commentId = CommentHelper.getCommentId(postId);

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateBasicAuth())
                // Ejecucion
                .when()
                .delete(commentResourcePath + postId + "/" + commentId)
                // Assertions / Verificaciones
                .then()
                .body("message", equalTo("Comment deleted"))
                .statusCode(200);
    }

    //Negative test - Comment Delete - Comment Not Found
    @Test
    public void Test_Comment_Delete_Not_Found(){

        String postId = PostHelper.getPostId();
        String commentId = "0001";

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateBasicAuth())
                // Ejecucion
                .when()
                .delete(commentResourcePath + postId + "/" + commentId)
                // Assertions / Verificaciones
                .then()
                .body("message", equalTo("Comment could not be deleted"))
                .body("error", equalTo("Comment not found"))
                .statusCode(406);
    }
}