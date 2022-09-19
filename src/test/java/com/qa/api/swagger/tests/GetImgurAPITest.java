package com.qa.api.swagger.tests;

import com.qa.api.swagger.restclient.RestClient;
import com.qa.api.swagger.token.Token;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class GetImgurAPITest {

    String accessToken;
    Map<String, String> accessTokenMap;
    String username;
    String refreshToken;

    Map<Object, Object> tokenMapObj;

    @BeforeMethod
    public void setUp() {
        tokenMapObj = Token.getAccessToken();
        accessToken = tokenMapObj.get("access_token").toString();
        username = tokenMapObj.get("account_username").toString();
        refreshToken = tokenMapObj.get("refresh_token").toString();
    }

    @Test
    public void getAccountBlockStatusTest() {

        accessTokenMap = Token.getAuthToken();
        Response response = RestClient.doGET(null, "https://api.imgur.com", "account/v1/" + username + "/block",
                accessTokenMap, null, true);
        System.out.println(response.statusCode());
        System.out.println(response.prettyPrint());
    }

    @Epic("get favorites images api feature implementation ......")
    @Feature("get favorites images ......")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void getAccountImagesTest() {
        Response response = RestClient.doGET(null, "https://api.imgur.com", "/3/account/me/images",
                accessTokenMap, null, true);
        System.out.println(response.statusCode());
        System.out.println(response.prettyPrint());

    }

    @Epic("get favorites images api feature implementation ......")
    @Feature("get favorites images ......")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void getAccountFavouritesTest() {

        Integer page = ThreadLocalRandom.current().nextInt(1,5);
        String sort = "newest";

        Response response = RestClient.doGET(null, "https://api.imgur.com",
                "https://api.imgur.com/3/account/"+username+"/favorites/"+page+"/"+sort,
                accessTokenMap, null, true);

        System.out.println(response.statusCode());
        System.out.println(response.prettyPrint());
    }

    @Epic("upload images rest api feature implementation ......")
    @Feature("upload images ......")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void uploadImagesTest() {

        Map<String, String> clientMap = Token.getClientId();

        Map<String, String> formMap = new HashMap<>();
        formMap.put("title", "test title API");
        formMap.put("description", "test description API");

        Response response = RestClient.doPOST("multipart", "https://api.imgur.com", "/3/upload",
                clientMap, null, true, formMap);
        System.out.println(response.statusCode());
        System.out.println(response.prettyPrint());
    }

}
