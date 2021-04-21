package by.pavel.mock.api;

import by.pavel.mock.unit.entity.Mapping;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static by.pavel.mock.api.Constant.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

class MappingTest extends HTTPActions {

    private final Mapping testMapping = new Mapping(200, "{item:1}", "/item/1", "GET").normalize();
    private final Mapping updatedTestMapping = new Mapping(200, "{item:2}", "/item/1", "GET").normalize();

    @Test
    void getMappings() throws IOException {
        Response response = get(Constant.MAPPINGS);
        String body = bodyAsString(response);
        Mapping[] mappings = gson.fromJson(body, Mapping[].class);
        assertThat(response.code(), equalTo(200));
        assertTrue(mappings.length > 0);
    }

    @Test
    void addGetUpdateAndDeleteMapping() throws IOException {
        int initialMappingsCount = getMappingsCount();

        Response addResponse = post(Constant.MAPPINGS, JSON, gson.toJson(testMapping));
        String id = bodyAsString(addResponse);
        int mappingsCountAfterAdd = getMappingsCount();
        assertThat(addResponse.code(), equalTo(200));
        assertThat(mappingsCountAfterAdd, equalTo(initialMappingsCount+1));

        Response getResponse = get(Constant.MAPPINGS +"/"+id);
        String mockJson = bodyAsString(getResponse);
        assertThat(getResponse.code(), equalTo(200));
        assertThat(gson.fromJson(mockJson, Mapping.class), equalTo(testMapping));

        Response updateResponse = put(Constant.MAPPINGS +"/"+id, JSON, gson.toJson(updatedTestMapping));
        String updatedMockJson = bodyAsString(updateResponse);
        assertThat(updateResponse.code(), equalTo(200));
        assertThat(gson.fromJson(updatedMockJson, Mapping.class), equalTo(updatedTestMapping));

        Response deleteResponse = delete(Constant.MAPPINGS+"/"+id);
        assertThat(deleteResponse.code(), equalTo(200));
        int mappingsCountAfterDelete = getMappingsCount();
        assertThat(mappingsCountAfterDelete, equalTo(initialMappingsCount));
    }

    private int getMappingsCount() throws IOException {
        return gson.fromJson(bodyAsString(get(Constant.MAPPINGS)), Mapping[].class).length;
    }
}
