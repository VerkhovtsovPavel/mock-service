package by.pavel.mock.api;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.Optional;

class HTTPActions {

    private static OkHttpClient client = new OkHttpClient();
    public static Gson gson = new Gson();

    public static Response get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + url)
                .build();

        Call call = client.newCall(request);
        return call.execute();
    }

    public static Response post(String url, MediaType contentType, String body) throws IOException {
        RequestBody requestBody = RequestBody.create(body, contentType);

        Request request = new Request.Builder()
                .url(Constant.BASE_URL + url)
                .addHeader("Content-Type", contentType.type())
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        return call.execute();
    }

    public static Response put(String url, MediaType contentType, String body) throws IOException {
        RequestBody requestBody = RequestBody.create(body, contentType);

        Request request = new Request.Builder()
                .url(Constant.BASE_URL + url)
                .addHeader("Content-Type", contentType.type())
                .put(requestBody)
                .build();

        Call call = client.newCall(request);
        return call.execute();
    }

    public static Response delete(String url) throws IOException {
        Request request = new Request.Builder()
                .url(Constant.BASE_URL + url)
                .delete()
                .build();

        Call call = client.newCall(request);
        return call.execute();
    }

    public static String bodyAsString(Response response) throws IOException {
        ResponseBody body = response.body();
        if (body!=null) {
            return body.string();
        }
        return "";
    }
}
