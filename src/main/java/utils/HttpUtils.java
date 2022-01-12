package utils;

import com.google.gson.Gson;
import dtos.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class HttpUtils {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static Gson gson = new Gson();




      public static String fetchDatacopy(String _url) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(_url))
                .header("content-type", "application/json")
                .header("User-Agent", "server")
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }




}
