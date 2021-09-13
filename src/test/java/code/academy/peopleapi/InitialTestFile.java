package code.academy.peopleapi;


import code.academy.client.PeopleApiClient;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;



public class InitialTestFile {

    PeopleApiClient peopleApiClient = new PeopleApiClient();
    HttpResponse response;
    HttpResponse getPeople;
    HttpResponse getOnePerson;

    @Test
    public void initialTest() throws Exception {
        // nekakov REQUEST do PEOPLE API
        response = peopleApiClient.getWelcomeRequest();
        getPeople = peopleApiClient.getAllPeople();
        getOnePerson = peopleApiClient.getOnePerson();

        String body = EntityUtils.toString(response.getEntity());
        String bodyAllPeople = EntityUtils.toString(getPeople.getEntity());
        String bodyOfOnePerson = EntityUtils.toString(getOnePerson.getEntity());

        // People API mi vrakja nekakov response

        // nekakva proverka na bodyto shto go ima vo toj response
        // body === "message": "Welcome to People API"

    }
}
