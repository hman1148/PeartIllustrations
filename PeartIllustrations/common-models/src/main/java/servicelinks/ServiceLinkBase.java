package servicelinks;

import models.GenericResponse.Response;

public abstract class ServiceLinkBase<T> {
    protected String baseUrl;

    public ServiceLinkBase(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public abstract <R extends Response<T>> R get(String endpoint, Class<R> responseType);

    public abstract <TRequest, R extends Response<T>> R post(String endpoint, TRequest requestBody, Class<R> responseType);

    public abstract <TRequest, R extends Response<T>> R put(String endpoint, TRequest requestBody, Class<R> responseType);

    public abstract <R extends Response<T>> R delete(String endpoint, Class<R> responseType);
}