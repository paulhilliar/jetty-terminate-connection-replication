package examples;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;

import java.util.List;


public class OkHttpHarness {

    public static void main(String[] args) throws Exception {
        OkHttpClient client = new OkHttpClient.Builder()
            .protocols(List.of(Protocol.HTTP_1_1))
            .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
            .url("http://localhost:8090/terminate")
            .build();

        try (okhttp3.Response response = client.newCall(request).execute()) {

            System.out.println("Response status: " + response.code());
            System.out.println("Response headers: " + response.headers());

            System.out.println("About to read response body.....");
            System.out.println("Response body: " + response.body().string());

            throw new RuntimeException("This should never be reached");
        }
    }
}
