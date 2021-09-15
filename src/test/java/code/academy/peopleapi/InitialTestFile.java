package code.academy.peopleapi;


import code.academy.client.PeopleApiClient;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;



public class InitialTestFile {

    PeopleApiClient peopleApiClient = new PeopleApiClient();
    HttpResponse response;


    @Test
    public void welcomeMessagePeopleApiTest() throws Exception {
        String expectedMessage = "Welcome to People API";
        // nekakov REQUEST do PEOPLE API
        response = peopleApiClient.httpGet("https://people-api1.herokuapp.com/");

        String body = EntityUtils.toString(response.getEntity());
        JSONObject bodyAsObject = new JSONObject(body);

        String messageAsString = bodyAsObject.get("message").toString();

        Assert.assertEquals(messageAsString, expectedMessage);
    }

    @Test
    public void getSinglePersonTest() throws Exception {
        String expectedMessage = "Person succesfully fetched";
        // nekakov REQUEST do PEOPLE API
        response = peopleApiClient.httpGet("https://people-api1.herokuapp.com/api/person/612ba20357744c30dc7e6fe7");

        // cel response sme go pretvorile vo String
        String body = EntityUtils.toString(response.getEntity());
        // cel toj JSON string sme go pretvorile vo objekt
        JSONObject bodyAsObject = new JSONObject(body);

        // person sum go zemal kako string
        String personDataAsString = bodyAsObject.get("person1").toString();
        // person stringot go pretvoram vo objekt
        JSONObject personData = new JSONObject(personDataAsString);
        // get name
        String name = personData.get("name").toString();

        Assert.assertEquals(name, "Deni");
    }

    @Test
    public void getAllPeopleTest() throws Exception {
        response = peopleApiClient.httpGet("https://people-api1.herokuapp.com/api/people");

        String body = EntityUtils.toString(response.getEntity());
        JSONObject bodyAsObject = new JSONObject(body);
        String messageAsString = bodyAsObject.get("message").toString();
    }

    @Test
    public void postPersonTest() throws Exception {
        JSONObject payloadAsObject = new JSONObject();
        payloadAsObject.put("name", "Pero");
        payloadAsObject.put("surname", "Blazevski");
        payloadAsObject.put("age", 56);
        payloadAsObject.put("isEmployed", true);
        payloadAsObject.put("location", "Skopje");

        response = peopleApiClient.httpPost("https://people-api1.herokuapp.com/api/person", payloadAsObject);
        String body = EntityUtils.toString(response.getEntity());
    }

    @Test
    public void postPersonTestWithoutName() throws Exception {
        JSONObject payloadAsObject = new JSONObject();
        payloadAsObject.put("surname", "Blazevski");
        payloadAsObject.put("age", 56);
        payloadAsObject.put("isEmployed", true);
        payloadAsObject.put("location", "Skopje");

        response = peopleApiClient.httpPost("https://people-api1.herokuapp.com/api/person", payloadAsObject);
        String body = EntityUtils.toString(response.getEntity());
    }

    @Test public void updatePersonLocationTest() throws Exception {
        JSONObject payloadAsObject = new JSONObject();
        payloadAsObject.put("location", "Bitola");

        response = peopleApiClient.httpPut("https://people-api1.herokuapp.com/api/person/612ba20357744c30dc7e6fe7",
                payloadAsObject);

        String body = EntityUtils.toString(response.getEntity());
    }
}
