package code.academy.peopleapi;

import code.academy.base.TestBase;
import code.academy.model.responses.PostNewPersonResponse;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import static code.academy.utils.ConversionUtils.jsonStringToObject;
import static code.academy.utils.ConversionUtils.objectToJsonString;
import static org.apache.http.HttpStatus.*;
import static code.academy.config.HostNameConfig.*;
import static code.academy.config.EndPointConfig.*;
import static code.academy.utils.TestDataUtils.ResponseCode.*;

public class PostNewPersonTest extends TestBase {

    public PostNewPersonTest() throws Exception {
    }


    PostNewPersonResponse postNewPersonResponse;
    String personOneId;
    String personTwoId;
    String personThreeID;

    @Test
    public void postPersonTest() throws Exception {
        postNewPersonRequest = postNewPersonPayload.createNewPersonPayload();

        String newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);

        response = peopleApiClient.httpPost(HOSTNAME + POST_ENDPOINT, newPersonPayloadAsString);

        String body = EntityUtils.toString(response.getEntity());

        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_CREATED);
        Assert.assertEquals(postNewPersonResponse.getCode(), P201);
        Assert.assertEquals(postNewPersonResponse.getMessage(), "Person succesfully inserted");

        Assert.assertEquals(postNewPersonResponse.getPersonData().getName(), postNewPersonRequest.getName());
        Assert.assertEquals(postNewPersonResponse.getPersonData().getSurname(), postNewPersonRequest.getSurname());
        Assert.assertEquals(postNewPersonResponse.getPersonData().getAge(), postNewPersonRequest.getAge());
        Assert.assertEquals(postNewPersonResponse.getPersonData().getIsEmployed(), postNewPersonRequest.getIsEmployed());
        Assert.assertEquals(postNewPersonResponse.getPersonData().getLocation(), postNewPersonRequest.getLocation());
        Assert.assertNotNull(postNewPersonResponse.getPersonData().getCreatedAt());
        Assert.assertNotNull(postNewPersonResponse.getPersonData().getUpdatedAt());
        Assert.assertNotNull(postNewPersonResponse.getPersonData().getId());

        personOneId = postNewPersonResponse.getPersonData().getId();
    }

    @Test
    public void PostPersonWithoutAge() throws Exception {
        postNewPersonRequest = postNewPersonPayload.createNewPersonPayload();
        postNewPersonRequest.setAge(null);

        String newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);

        response = peopleApiClient.httpPost("https://people-api1.herokuapp.com/api/person", newPersonPayloadAsString);

        String body = EntityUtils.toString(response.getEntity());

        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_CREATED);
        Assert.assertEquals(postNewPersonResponse.getCode(), P201);
        Assert.assertEquals(postNewPersonResponse.getMessage(), "Person succesfully inserted");

        Assert.assertNull(postNewPersonResponse.getPersonData().getAge());

        personTwoId = postNewPersonResponse.getPersonData().getId();

    }

    @Test
    public void PostPersonWithoutLocation() throws Exception{
        postNewPersonRequest = postNewPersonPayload.createNewPersonPayload();
        postNewPersonRequest.setLocation(null);

        String newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);

        response = peopleApiClient.httpPost("https://people-api1.herokuapp.com/api/person", newPersonPayloadAsString);

        String body = EntityUtils.toString(response.getEntity());

        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_CREATED);
        Assert.assertEquals(postNewPersonResponse.getCode(), "P201");
        Assert.assertEquals(postNewPersonResponse.getMessage(), "Person succesfully inserted");

        Assert.assertNull(postNewPersonResponse.getPersonData().getLocation());

        personThreeID = postNewPersonResponse.getPersonData().getId();

    }

    @Test
    public void PostPersonWithoutSurname() throws Exception {
        postNewPersonRequest = postNewPersonPayload.createNewPersonPayload();
        postNewPersonRequest.setSurname(null);

        String newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);

        response = peopleApiClient.httpPost("https://people-api1.herokuapp.com/api/person", newPersonPayloadAsString);

        String body = EntityUtils.toString(response.getEntity());

        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_BAD_REQUEST);
        Assert.assertEquals(postNewPersonResponse.getCode(),"P400");
        Assert.assertEquals(postNewPersonResponse.getMessage(),"Person's surname cannot be empty");
    }

    @Test
    public void PostPersonWithoutName() throws Exception{
        postNewPersonRequest = postNewPersonPayload.createNewPersonPayload();
        postNewPersonRequest.setName(null);

        String newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);

        response = peopleApiClient.httpPost("https://people-api1.herokuapp.com/api/person", newPersonPayloadAsString);

        String body = EntityUtils.toString(response.getEntity());

        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_BAD_REQUEST);
        Assert.assertEquals(postNewPersonResponse.getCode(),"P400");
        Assert.assertEquals(postNewPersonResponse.getMessage(),"Person's name cannot be empty");
    }

    @Test
    public void PostPersonWithoutIsEmploye() throws Exception{
        postNewPersonRequest = postNewPersonPayload.createNewPersonPayload();
        postNewPersonRequest.setIsEmployed(null);
        String newPersonPayloadAsString = objectToJsonString(postNewPersonRequest);

        response = peopleApiClient.httpPost("https://people-api1.herokuapp.com/api/person", newPersonPayloadAsString);

        String body = EntityUtils.toString(response.getEntity());

        postNewPersonResponse = jsonStringToObject(body, PostNewPersonResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(),SC_BAD_REQUEST);
        Assert.assertEquals(postNewPersonResponse.getCode(),"P400");
        Assert.assertEquals(postNewPersonResponse.getMessage(),"Person must provide if he is employed or not");
    }

    @Test // this is commented out, bug reported COD-24
    public void PostPersonEmployeAsString() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject = postNewPersonPayload.createNewPersonPayloadEmployeAsString();

        response = peopleApiClient.httpPost("https://people-api1.herokuapp.com/api/person", jsonObject.toString());

        String body = EntityUtils.toString(response.getEntity());

        JSONObject bodyAsObject = new JSONObject(body);
        String messageAsString = bodyAsObject.get("message").toString();

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_BAD_REQUEST);

        String expectedMessage = "Person validation failed:";
        Boolean passes = messageAsString.contains(expectedMessage);
        Assert.assertTrue(passes);
    }

    @AfterClass
    public void afterClass() throws Exception{
        HttpResponse response1 = peopleApiClient.httpDelete("https://people-api1.herokuapp.com/api/person/" + personOneId);
        HttpResponse response2 = peopleApiClient.httpDelete("https://people-api1.herokuapp.com/api/person/" + personTwoId) ;
        HttpResponse response3 = peopleApiClient.httpDelete("https://people-api1.herokuapp.com/api/person/" + personThreeID);

        Assert.assertEquals(response1.getStatusLine().getStatusCode(), SC_OK);
        Assert.assertEquals(response2.getStatusLine().getStatusCode(), SC_OK);
        Assert.assertEquals(response3.getStatusLine().getStatusCode(), SC_OK);
    }
}
