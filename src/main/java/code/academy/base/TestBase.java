package code.academy.base;

import code.academy.client.PeopleApiClient;
import code.academy.model.requests.PostNewPersonRequest;
import code.academy.payloads.PostNewPersonPayload;
import org.apache.http.HttpResponse;

public class TestBase {

    public PeopleApiClient peopleApiClient = new PeopleApiClient();
    public PostNewPersonRequest postNewPersonRequest = new PostNewPersonRequest();
    public PostNewPersonPayload postNewPersonPayload = new PostNewPersonPayload();
    public HttpResponse response;

    public TestBase() throws Exception {
    }
}
