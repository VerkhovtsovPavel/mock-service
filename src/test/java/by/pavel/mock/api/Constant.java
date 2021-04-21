package by.pavel.mock.api;

import okhttp3.MediaType;

public interface Constant {
    String BASE_URL = "https://mock-service-310714.appspot.com/";
    String MAPPINGS = "mappings";
    String MATCH = "match";
    MediaType JSON = MediaType.parse("application/json; charset=utf-8");

}
