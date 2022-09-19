package com.qa.api.swagger.restclient;


import com.qa.api.swagger.util.TestUtil;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.util.Map;

/**
 * This class is having all HTTPs methods, which will call the APIs and having generic methods
 * for getting the response and fetch the values from response.
 *
 * @author dzmitryrazhkou
 */

public class RestClient {

//    HTTP Methods: GET, POST, PUT, DELETE

    @Step("get call with {0}, {1}, {2}, {3}, {4}, {5}")
    public static Response doGET(String contentType, String baseURI, String basePath,
                                 Map<String, String> token, Map<String, String> paramsMap, boolean log) {
        if (setBaseURL(baseURI)) {
            RequestSpecification request = createRequest(contentType, token, paramsMap, log);
            return getResponse("GET", request, basePath);
        }
        return null;
    }

    /**
     * This method is used to call POST API
     *
     * @param contentType
     * @param baseURI
     * @param basePath
     * @param token
     * @param paramsMap
     * @param log
     * @param obj
     * @return this method is returning response from the POST call
     */
    @Step("post call with {0}, {1}, {2}, {3}, {4}, {5}, {6}")
    public static Response doPOST(String contentType, String baseURI, String basePath,
                                  Map<String, String> token, Map<String, String> paramsMap, boolean log, Object obj) {
        if (setBaseURL(baseURI)) {
            RequestSpecification request = createRequest(contentType, token, paramsMap, log);
            addRequestPayload(request, obj);
            return getResponse("POST", request, basePath);
        }
        return null;
    }

    @Step("put call with {0}, {1}, {2}, {3}, {4}, {5}, {6}")
    public static Response doPUT(String contentType, String baseURI, String basePath,
                                  Map<String, String> token, Map<String, String> paramsMap, boolean log, Object obj) {
        if (setBaseURL(baseURI)) {
            RequestSpecification request = createRequest(contentType, token, paramsMap, log);
            addRequestPayload(request, obj);
            return getResponse("PUT", request, basePath);
        }
        return null;
    }

    @Step("delete call with {0}, {1}, {2}, {3}, {4}, {5}")
    public static Response doDELETE(String contentType, String baseURI, String basePath,
                                 Map<String, String> token, Map<String, String> paramsMap, boolean log) {
        if (setBaseURL(baseURI)) {
            RequestSpecification request = createRequest(contentType, token, paramsMap, log);
            return getResponse("DELETE", request, basePath);
        }
        return null;
    }

    public static void addRequestPayload(RequestSpecification request, Object obj) {

        if (obj instanceof Map) {
            request.formParams((Map<String, String>) obj);
        } else {
            String jsonPayload = TestUtil.getSerializedJSON(obj);
            request.body(jsonPayload);
        }
    }

//    Set a URL:

    private static boolean setBaseURL(String baseURI) {
        if (baseURI == null || baseURI.isEmpty()) {
            System.out.println("Please Pass The Correct BaseURL ... Either It Is Null Or Blank/ Empty ... ");
            return false;
        }
        try {
            RestAssured.baseURI = baseURI;
            return true;
        } catch (Exception e) {
            System.out.println("Some Exception Got Occurred While Assigning The BaseURI With RestAssured ... ");
            return false;
        }
    }

//    Pass a request parameters:

    private static RequestSpecification createRequest(String contentType, Map<String, String> token,
                                                      Map<String, String> paramsMap, boolean log) {

        RequestSpecification request;
        if (log) {
            request = RestAssured.given().log().all();
        } else {
            request = RestAssured.given();
        }

        if (token.size() > 0) {
//            request.header("Authorization", "Bearer " +token);
            request.headers(token);
        }

        if (!(paramsMap == null)) {
            request.queryParams(paramsMap);
        }

        if (contentType != null) {
            if (contentType.equalsIgnoreCase("JSON")) {
                request.contentType(ContentType.JSON);
            } else if (contentType.equalsIgnoreCase("XML")) {
                request.contentType(ContentType.XML);
            } else if (contentType.equalsIgnoreCase("TEXT")) {
                request.contentType(ContentType.TEXT);
            } else if (contentType.equalsIgnoreCase("multipart")) {
                request.multiPart("image", new File("/Users/dzmitryrazhkou/Downloads/Kremlin.PNG"));
            }
        }

        return request;
    }

//    Get a response

    private static Response getResponse(String HTTPMethod, RequestSpecification request, String basePath) {
        return executeAPI(HTTPMethod, request, basePath);
    }

//    Execute

    private static Response executeAPI(String HTTPMethod, RequestSpecification request, String basePath) {
        Response response = null;
        switch (HTTPMethod) {
            case "GET":
                response = request.get(basePath);
                break;

            case "POST":
                response = request.post(basePath);
                break;

            case "PUT":
                response = request.put(basePath);
                break;

            case "DELETE":
                response = request.delete(basePath);
                break;

            default:
                System.out.println("Please Pass the Correct HTTP Method ... ");
                break;
        }
        return response;
    }


}
