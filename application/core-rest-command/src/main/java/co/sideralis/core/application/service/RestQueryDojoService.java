package co.sideralis.core.application.service;

import co.sideralis.core.usecases.dojo.QueryDojoService;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class RestQueryDojoService implements QueryDojoService {

    private static final HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .build();

    private final String urlBase;

    public RestQueryDojoService() {
        urlBase = System.getProperties().get("spring.queries.url").toString();
    }

    @Override
    public boolean existDojoName(String name) {
        Objects.requireNonNull(name, "The option name is required");

        var param = URLEncoder.encode(name.toLowerCase(), StandardCharsets.UTF_8);

        HttpRequest dojoByNameReq = HttpRequest
                .newBuilder(URI.create(urlBase + "/get/dojo-by-name?name=" + param))
                .GET().build();

        String response = Optional.of(queryFor(dojoByNameReq))
                .filter(b -> !b.isBlank())
                .map(b -> new Gson().fromJson(b, Map.class))
                .map(m -> m.get("name").toString())
                .orElseGet(() -> null);
        return !Objects.isNull(response);
    }

    private String queryFor(HttpRequest req) {
        try {
            return client.send(req, HttpResponse.BodyHandlers.ofString()).body();
        } catch (IOException | InterruptedException e) {
            throw new IntegrationServiceException(e);
        }
    }
}
