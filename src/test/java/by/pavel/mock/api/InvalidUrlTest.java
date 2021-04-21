package by.pavel.mock.api;

import by.pavel.mock.unit.entity.Mapping;
import okhttp3.Response;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class InvalidUrlTest extends HTTPActions {

    @Test
    public void invalidUrlTest() throws IOException {
        Response response = get("/invalid");
        String body = bodyAsString(response);
        assertThat(response.code(), equalTo(404));
        assertThat(body, equalTo("Unmapped request"));
    }
}
