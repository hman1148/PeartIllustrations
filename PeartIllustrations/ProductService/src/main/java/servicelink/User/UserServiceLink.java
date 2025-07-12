package servicelink.User;

import models.GenericResponse.Response;
import models.User.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import servicelinks.ServiceLinkBase;

public class UserServiceLink extends ServiceLinkBase<User> {

    private final RestTemplate restTemplate;

    public UserServiceLink(String baseUrl) {
        super(baseUrl);
        this.restTemplate = new RestTemplate();
    }

    @Override
    public <R extends Response<User>> R get(String endpoint, Class<R> responseType) {
        String url = baseUrl + endpoint;
        ResponseEntity<R> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                ParameterizedTypeReference.forType(responseType)
        );
        return responseEntity.getBody();
    }

    @Override
    public <TRequest, R extends Response<User>> R post(String endpoint, TRequest requestBody, Class<R> responseType) {
        String url = baseUrl + endpoint;
        HttpEntity<TRequest> entity = new HttpEntity<>(requestBody);
        ResponseEntity<R> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                ParameterizedTypeReference.forType(responseType)
        );
        return responseEntity.getBody();
    }

    @Override
    public <TRequest, R extends Response<User>> R put(String endpoint, TRequest requestBody, Class<R> responseType) {
        String url = baseUrl + endpoint;
        HttpEntity<TRequest> entity = new HttpEntity<>(requestBody);
        ResponseEntity<R> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                ParameterizedTypeReference.forType(responseType)
        );
        return responseEntity.getBody();
    }

    @Override
    public <R extends Response<User>> R delete(String endpoint, Class<R> responseType) {
        String url = baseUrl + endpoint;
        ResponseEntity<R> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                null,
                ParameterizedTypeReference.forType(responseType)
        );
        return responseEntity.getBody();
    }
}