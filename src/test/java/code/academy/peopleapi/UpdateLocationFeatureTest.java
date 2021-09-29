package code.academy.peopleapi;

import code.academy.client.PeopleApiClient;
import code.academy.model.requests.PostNewPersonRequest;
import code.academy.model.responses.PostNewPersonResponse;
import code.academy.model.responses.PutUpdateLocationResponse;
import code.academy.payloads.PostNewPersonPayload;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static code.academy.utils.ConversionUtils.jsonStringToObject;
import static code.academy.utils.ConversionUtils.objectToJsonString;
import static org.apache.http.HttpStatus.*;

public class UpdateLocationFeatureTest {

    PeopleApiClient peopleApiClient = new PeopleApiClient();
    HttpResponse response;
    PostNewPersonPayload updateLocationPayload = new PostNewPersonPayload();
    PostNewPersonRequest updateLocationRequest = updateLocationPayload.createUpdateLocationPayload();
    PutUpdateLocationResponse putUpdateLocationResponse = new PutUpdateLocationResponse();
    String invalidId = "uoghguihui4589u7349";

    String createPersonID;

    public UpdateLocationFeatureTest() throws Exception {
    }

    @BeforeClass
    public void beforeClass() throws Exception {
        PostNewPersonRequest createNewPersonRequest = updateLocationPayload.createNewPersonPayload();
        String requestBody = objectToJsonString(createNewPersonRequest);

        HttpResponse createPersonResponse = peopleApiClient.httpPost("https://people-api1.herokuapp.com/api/person", requestBody);
        Assert.assertEquals(createPersonResponse.getStatusLine().getStatusCode(), SC_CREATED);

        String responseBody = EntityUtils.toString(createPersonResponse.getEntity());
        PostNewPersonResponse postNewPersonResponse = jsonStringToObject(responseBody, PostNewPersonResponse.class);

        createPersonID = postNewPersonResponse.getPersonData().getId();
    }

    @Test
    public void updatePersonsLocation() throws Exception {
        String requestBody = objectToJsonString(updateLocationRequest);

        response = peopleApiClient.httpPut("https://people-api1.herokuapp.com/api/person/" + createPersonID, requestBody);

        String resposneBody = EntityUtils.toString(response.getEntity());
        putUpdateLocationResponse = jsonStringToObject(resposneBody, PutUpdateLocationResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_OK);
        Assert.assertEquals(putUpdateLocationResponse.getCode(), "P200");
        Assert.assertEquals(putUpdateLocationResponse.getMessage(), "Person's location succesfully updated !");
        Assert.assertEquals(putUpdateLocationResponse.getPerson().getLocation(), updateLocationRequest.getLocation());
    }

    @Test
    public void updateNonExistingPersonsLocation() throws Exception {
        String requestBody = objectToJsonString(updateLocationRequest);

        response = peopleApiClient.httpPut("https://people-api1.herokuapp.com/api/person/" + invalidId, requestBody);

        String resposneBody = EntityUtils.toString(response.getEntity());
        putUpdateLocationResponse = jsonStringToObject(resposneBody, PutUpdateLocationResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_NOT_FOUND);
//        Assert.assertEquals(putUpdateLocationResponse.getCode(), "P404");
        Assert.assertEquals(putUpdateLocationResponse.getMessage(), "Person with id="+ invalidId
                +" not found");
    }

    @Test
    public void updatePersonsLocationSetAsNull() throws Exception {
        PostNewPersonRequest updateEmptyLocationRequest = updateLocationPayload.createUpdateEmptyLocationPayload();
        String requestBody = objectToJsonString(updateEmptyLocationRequest);

        response = peopleApiClient.httpPut("https://people-api1.herokuapp.com/api/person/" + invalidId, requestBody);

        String resposneBody = EntityUtils.toString(response.getEntity());
        putUpdateLocationResponse = jsonStringToObject(resposneBody, PutUpdateLocationResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_BAD_REQUEST);
        Assert.assertEquals(putUpdateLocationResponse.getCode(), "P400");
        Assert.assertEquals(putUpdateLocationResponse.getMessage(), "Person's location must be provided to be updated !");
    }

    @Test
    public void updatePersonsLocationEmptyRequest() throws Exception {
        PostNewPersonRequest updateEmptyLocationRequest = updateLocationPayload.createUpdateEmptyLocationPayload();
        updateEmptyLocationRequest.setLocation(null);
        String requestBody = objectToJsonString(updateEmptyLocationRequest);

        response = peopleApiClient.httpPut("https://people-api1.herokuapp.com/api/person/" + invalidId, requestBody);

        String resposneBody = EntityUtils.toString(response.getEntity());
        putUpdateLocationResponse = jsonStringToObject(resposneBody, PutUpdateLocationResponse.class);

        Assert.assertEquals(response.getStatusLine().getStatusCode(), SC_BAD_REQUEST);
        Assert.assertEquals(putUpdateLocationResponse.getCode(), "P400");
        Assert.assertEquals(putUpdateLocationResponse.getMessage(), "Request body cannot be empty");
    }

    @AfterClass
    public void afterClass() throws Exception {
        HttpResponse deleteResponse = peopleApiClient.httpDelete("https://people-api1.herokuapp.com/api/person/" + createPersonID);
        Assert.assertEquals(deleteResponse.getStatusLine().getStatusCode(), SC_OK);
    }
}
