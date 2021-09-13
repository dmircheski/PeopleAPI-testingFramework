package code.academy.peopleapi;


import code.academy.client.PeopleApiClient;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;



public class InitialTestFile {

    PeopleApiClient peopleApiClient = new PeopleApiClient();
    HttpResponse response;


    @Test
    public void initialTest() throws Exception {
        // nekakov REQUEST do PEOPLE API
        response = peopleApiClient.postNewPerson();


        String body = EntityUtils.toString(response.getEntity());


        // People API mi vrakja nekakov response

        // nekakva proverka na bodyto shto go ima vo toj response
        // body === "message": "Welcome to People API"

    }
}
