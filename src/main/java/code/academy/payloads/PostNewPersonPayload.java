package code.academy.payloads;

import code.academy.model.requests.PostNewPersonRequest;
import org.json.JSONObject;

public class PostNewPersonPayload {

    public PostNewPersonRequest createNewPersonPayload() {
        return PostNewPersonRequest.builder()
                .name("Derric")
                .surname("Rose")
                .age(32)
                .isEmployed(true)
                .location("Chicago")
                .build();
    }

    public PostNewPersonRequest createUpdateLocationPayload() {
        return PostNewPersonRequest.builder()
                .location("Zadar")
                .build();
    }

    public PostNewPersonRequest createUpdateEmptyLocationPayload() {
        return PostNewPersonRequest.builder()
                .location("")
                .build();
    }

    public JSONObject createNewPersonPayloadEmployeAsString(){
        JSONObject personObject = new JSONObject();
        personObject.put("name","Horhe");
        personObject.put("surname","papito");
        personObject.put("age",35);
        personObject.put("isEmployed","kako string");
        personObject.put("location","Ohrid");

        return personObject;
    }
}
