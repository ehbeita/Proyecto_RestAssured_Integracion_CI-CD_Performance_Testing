package api_test;

import helpers.DataHelper;
import helpers.PostHelper;
import model.Post;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import specifications.RequestSpecs;
import specifications.ResponseSpecs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class PostsTests extends BaseTest {

    String postResourcePath = "/v1/post";
    String postsResourcePath = "/v1/posts";

    //Positive test - Create Post
    @Test
    public void Test_Post_Create(){

        Post newPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateToken())
                .body(newPost)
                // Ejecucion
                .when()
                .post(postResourcePath )
                // Assertions / Verificaciones
                .then()
                .spec(ResponseSpecs.defaultSpec())
                .body("message", equalTo("Post created"))
                .statusCode(200);
    }

    //Negative test - Create Post - Invalid form
    @Test
    public void Test_Post_Create_Invalid_Form(){

        String postContent = "Body with only content";

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateToken())
                .body(postContent)
                // Ejecucion
                .when()
                .post(postResourcePath )
                // Assertions / Verificaciones
                .then()
                .spec(ResponseSpecs.defaultSpec())
                .body("message", equalTo("Invalid form"))
                .statusCode(406);
    }

    //Positive test - Post All
    @Test
    public void Test_Post_All(){

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateToken())
                // Ejecucion
                .when()
                .get(postsResourcePath )
                // Assertions / Verificaciones
                .then()
                .spec(ResponseSpecs.defaultSpec())
                .body("results", Matchers.notNullValue())
                .statusCode(200);
    }

    //Negative test - Post All - Invalid Token
    @Test
    public void Test_Post_All_Invalid_Token(){

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateInvalidToken())
                // Ejecucion
                .when()
                .get(postsResourcePath )
                // Assertions / Verificaciones
                .then()
                .spec(ResponseSpecs.defaultSpec())
                .body("results", Matchers.nullValue())
                .statusCode(401);
    }

    //Positive test - Post One
    @Test
    public void Test_Post_One(){

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateToken())
                // Ejecucion
                .when()
                .get(postResourcePath + "/" + PostHelper.getPostId())
                // Assertions / Verificaciones
                .then()
                .spec(ResponseSpecs.defaultSpec())
                .body("data", Matchers.notNullValue())
                .statusCode(200);
    }

    //Negative test - Post One - Post Not Found
    @Test
    public void Test_Post_One_Not_Found(){

        String postId = "0001";

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateToken())
                // Ejecucion
                .when()
                .get(postResourcePath + "/" + postId)
                // Assertions / Verificaciones
                .then()
                .spec(ResponseSpecs.defaultSpec())
                .body("Message", equalTo("Post not found"))
                .body("error", equalTo("sql: no rows in result set"))
                .statusCode(404);
    }

    //Positive test - Update Post
    @Test
    public void Test_Post_Update(){

        Post newPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateToken())
                .body(newPost)
                // Ejecucion
                .when()
                .put(postResourcePath + "/" + PostHelper.getPostId())
                // Assertions / Verificaciones
                .then()
                .spec(ResponseSpecs.defaultSpec())
                .body("message", equalTo("Post updated"))
                .statusCode(200);
    }

    //Negative test - Update Post - Post Not Found
    @Test
    public void Test_Post_Update_Not_Found(){

        Post newPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());
        String postId = "0001";

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateToken())
                .body(newPost)
                // Ejecucion
                .when()
                .put(postResourcePath + "/" + postId)
                // Assertions / Verificaciones
                .then()
                .spec(ResponseSpecs.defaultSpec())
                .body("message", equalTo("Post could not be updated"))
                .body("error", equalTo("Post not found"))
                .statusCode(406);
    }

    //Positive test - Delete Post
    @Test
    public void Test_Post_Delete(){

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateToken())
                // Ejecucion
                .when()
                .delete(postResourcePath + "/" + PostHelper.getPostId())
                // Assertions / Verificaciones
                .then()
                .spec(ResponseSpecs.defaultSpec())
                .body("message", equalTo("Post deleted"))
                .statusCode(200);
    }

    //Negative test - Delete Post - Post Not Found
    @Test
    public void Test_Post_Delete_Not_Found(){

        String postId = "0001";

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateToken())
                // Ejecucion
                .when()
                .delete(postResourcePath + "/" + postId)
                // Assertions / Verificaciones
                .then()
                .spec(ResponseSpecs.defaultSpec())
                .body("message", equalTo("Post could not be deleted"))
                .body("error", equalTo("Post not found"))
                .statusCode(406);
    }
}