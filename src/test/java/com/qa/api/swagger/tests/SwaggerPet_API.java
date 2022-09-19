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

public class SwaggerPet_API {

    Pet pet;
    String baseURI = "https://petstore.swagger.io";
    String token = "";
    int petId;

    public static Map<String, String> tokenMap;

    @Epic("Create A New Pet API Feature Implementation ......")
    @Feature("Create New Pet API Feature ......")
    @Severity(SeverityLevel.MINOR)
    @Test(priority = 1)
    public void createNewPetAPI_Test() {

        tokenMap = new HashMap<>();
        tokenMap.put("Authorization", "Bearer " + token);
        pet = TestDataGenerator.GeneratePet();
        String basePath = "/v2/pet";

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

    @Epic("Get Pet Using Id API Feature Implementation ......")
    @Feature("Get Pet API Feature ......")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 2)
    public void getPetAPI_Test() {
        tokenMap = new HashMap<>();
        tokenMap.put("Authorization", "Bearer " + token);
        String basePath = "/v2/pet/" +petId;

        Response response = RestClient.doGET("JSON", baseURI, basePath, tokenMap, null, true);
        assert response != null;
        System.out.println(response.statusCode());
        System.out.println(response.prettyPrint());

        JsonPath jsonPath = response.jsonPath();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(petId, pet.getId());
        Assert.assertEquals(jsonPath.getString("name"), pet.getName());
        Assert.assertEquals(jsonPath.getString("status"), Status.ADOPTED.name());
    }

    @Epic("Update An Existing Pet API Feature Implementation ......")
    @Feature("Update An Existing Pet API Feature ......")
    @Severity(SeverityLevel.MINOR)
    @Test(priority = 3)
    public void updatePetAPI_Test() {

        tokenMap = new HashMap<>();
        tokenMap.put("Authorization", "Bearer " + token);
        Pet petUpdated = TestDataGenerator.GenerateUpdatedPet();
        String basePath = "/v2/pet";

        Response response = RestClient.doPUT("JSON", baseURI, basePath, tokenMap, null, true, petUpdated);
        assert response != null;
        System.out.println(response.statusCode());
        System.out.println(response.prettyPrint());
        System.out.println("====================");

        JsonPath jsonPath = response.jsonPath();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(petId, petUpdated.getId());
        Assert.assertEquals(jsonPath.getString("name"), petUpdated.getName());
        Assert.assertEquals(jsonPath.getString("status"), Status.SOLD.name());
    }

    @Epic("Delete Pet Using Id API Feature Implementation ......")
    @Feature("Delete Pet API Feature ......")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 4)
    public void deletePetAPI_Test() {
        tokenMap = new HashMap<>();
        tokenMap.put("Authorization", "Bearer " + token);
        String basePath = "/v2/pet/" +petId;

        Response response = RestClient.doDELETE("JSON", baseURI, basePath, tokenMap, null, true);
        assert response != null;
        System.out.println(response.statusCode());
        System.out.println(response.prettyPrint());

        JsonPath jsonPath = response.jsonPath();

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(jsonPath.getString("code"), "200");
        Assert.assertEquals(jsonPath.getString("message"), String.valueOf(pet.getId()));
    }
}
