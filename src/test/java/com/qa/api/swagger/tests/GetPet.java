package com.qa.api.swagger.tests;

import com.qa.api.swagger.restclient.RestClient;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;



public class GetPet {

    String baseURI = "https://petstore.swagger.io";
    String basePath = "/v2/pet/12";
    public static Map<String, String> tokenMap;
    String token = "";

    @Epic("Get Pet Using Id API Feature Implementation ......")
    @Feature("Get Pet API Feature ......")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 1)
    public void getAllUserListAPI_Test() {
        tokenMap = new HashMap<>();
        tokenMap.put("Authorization", "Bearer " + token);


        Response response = RestClient.doGET("JSON", baseURI, basePath, tokenMap, null, true);
        System.out.println(response.statusCode());
        System.out.println(response.prettyPrint());
    }
}
