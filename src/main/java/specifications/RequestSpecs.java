package specifications;

import helpers.AuthHelper;
import helpers.DataHelper;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.http.impl.client.BasicAuthCache;

public class RequestSpecs {

    public static RequestSpecification generateToken(){

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String token = AuthHelper.getUserToken();
        // Add Header
        requestSpecBuilder.addHeader("Authorization", "Bearer "+token);

        return requestSpecBuilder.build();
    }

    public static RequestSpecification generateInvalidToken(){

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String token = AuthHelper.getUserInvalidToken();
        // Add Header
        requestSpecBuilder.addHeader("Authorization", "Bearer "+token);

        return requestSpecBuilder.build();
    }

    public static RequestSpecification generateBasicAuth(){

        BasicAuthScheme basicAuth = new BasicAuthScheme();
        basicAuth.setUserName("testuser");
        basicAuth.setPassword("testpass");

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setAuth(basicAuth);
        return requestSpecBuilder.build();
    }

    public static RequestSpecification generateInvalidBasicAuth(){

        BasicAuthScheme basicAuth = new BasicAuthScheme();
        basicAuth.setUserName("testuser99");
        basicAuth.setPassword("testpass11");

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setAuth(basicAuth);
        return requestSpecBuilder.build();
    }

}
