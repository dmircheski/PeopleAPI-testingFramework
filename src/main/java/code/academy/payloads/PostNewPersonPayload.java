package code.academy.payloads;

import code.academy.model.requests.PostNewPersonRequest;

public class PostNewPersonPayload {

    public PostNewPersonRequest createNewpersonPayload() {
        return  PostNewPersonRequest.builder()
                .name("Vlado")
                .surname("Ilievski")
                .age(54)
                .isEmployed(false)
                .location("Skopje")
                .build();
    }
}
