package by.pavel.mock.unit.entity;

import java.util.Objects;

public class Mapping {

    private String url;
    private String method;
    private int responseCode;
    private String body;
    private String contentType;

    public Mapping(int responseCode, String body, String url, String method, String contentType) {
        this(responseCode, body, url, method);
        this.contentType = contentType;
    }

    public Mapping(int responseCode, String body, String url, String method) {
        this(responseCode, body);
        this.url = url;
        this.method = method;
    }

    public Mapping(int responseCode, String body) {
        this.responseCode = responseCode;
        this.body = body;
    }

    public Mapping() {}

    public String getBody() {
        return body;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public String getContentType() {
        return contentType;
    }

    public Mapping normalize() {
        return new Mapping(responseCode, body, url.toLowerCase(), method.toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var mapping = (Mapping) o;
        return responseCode == mapping.responseCode &&
                Objects.equals(url, mapping.url) &&
                Objects.equals(method, mapping.method) &&
                Objects.equals(body, mapping.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, method, responseCode, body);
    }
}