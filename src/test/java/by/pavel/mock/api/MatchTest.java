package by.pavel.mock.api;

import by.pavel.mock.unit.entity.Mapping;
import okhttp3.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

class MatchTest extends HTTPActions {

    private static final Mapping getTestMapping = new Mapping(200, "{\"item\": 1}", "/item/1", "GET");
    private static final Mapping postTestMapping = new Mapping(201, "Item created", "/item/1", "POST");
    private static String getMappingId;
    private static String postMappingId;

    @BeforeAll
    static void createMapping() throws IOException {
        Response getMappingResponse = post(Constant.MAPPINGS, Constant.JSON, gson.toJson(getTestMapping));
        getMappingId = bodyAsString(getMappingResponse);
        Response postMappingResponse = post(Constant.MAPPINGS, Constant.JSON, gson.toJson(postTestMapping));
        postMappingId = bodyAsString(postMappingResponse);
    }

    @Test
    void checkMismatch() throws IOException {
        Response response = get(Constant.MATCH + "/mismatch");
        String body = bodyAsString(response);
        assertThat(body, equalTo("Not found"));
        assertThat(response.code(), equalTo(404));
    }

    @Test
    void checkGetMatch() throws IOException {
        Response response = get(Constant.MATCH + "/" + getTestMapping.getUrl());
        String body = bodyAsString(response);
        assertThat(response.code(), equalTo(getTestMapping.getResponseCode()));
        assertThat(body, equalTo(getTestMapping.getBody()));
    }

    @Test
    void checkPostMatch() throws IOException {
        Response response = post(Constant.MATCH + "/" + postTestMapping.getUrl(), Constant.JSON, "");
        String body = bodyAsString(response);
        assertThat(response.code(), equalTo(postTestMapping.getResponseCode()));
        assertThat(body, equalTo(postTestMapping.getBody()));
    }

    @AfterAll
    static void removeMapping() throws IOException {
        delete(Constant.MAPPINGS + "/" + getMappingId);
        delete(Constant.MAPPINGS + "/" + postMappingId);
    }
}