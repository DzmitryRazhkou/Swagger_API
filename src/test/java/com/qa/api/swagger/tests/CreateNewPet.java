package com.qa.api.swagger.tests;

import com.qa.api.swagger.pojo.Pet;
import com.qa.api.swagger.restclient.RestClient;
import com.qa.api.swagger.util.Status;
import com.qa.api.swagger.util.TestDataGenerator;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class CreateNewPet {

    Pet pet;
    String baseURI = "https://petstore.swagger.io";
    String basePath = "/v2/pet";
    String token = "";

    public static int petId;

    public static Map<String, String> tokenMap;

    @Epic("Create A New Pet API Feature Implementation ......")
    @Feature("Create New Pet API Feature ......")
    @Severity(SeverityLevel.MINOR)
    @Test(priority = 1)
    public void createNewPetTest() {

        tokenMap = new HashMap<>();
        tokenMap.put("Authorization", "Bearer " + token);
        pet = TestDataGenerator.GeneratePet();

        Response response = RestClient.doPOST("JSON", baseURI, basePath, tokenMap, null, true, pet);
        assert response != null;
        System.out.println(response.statusCode());
        System.out.println(response.prettyPrint());
        System.out.println("====================");

        JsonPath jsonPath = response.jsonPath();
        petId = jsonPath.getInt("id");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(petId, pet.getId());
        Assert.assertEquals(jsonPath.getString("name"), pet.getName());
        Assert.assertEquals(jsonPath.getString("status"), Status.ADOPTED.name());
    }
}
