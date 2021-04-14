package by.pavel.mock.entity;

public class Mapping {

    private String url;
    private String method;
    private int responseCode;
    private String body;

    public Mapping(String url, String method, int responseCode, String body) {
        this.url = url;
        this.method = method;
        this.responseCode = responseCode;
        this.body = body;
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

    public Mapping normalize() {
        return new Mapping(url.toLowerCase(), method.toLowerCase(), responseCode, body);
    }
}