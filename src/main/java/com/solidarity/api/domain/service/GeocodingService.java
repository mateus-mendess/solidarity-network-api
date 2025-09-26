package com.solidarity.api.domain.service;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class GeocodingService {

    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/search";

    public String getCoordinates(String postalCode, String neighborhood, String street, String city, String state) throws IOException, InterruptedException {
        String url = String.format("%s?street=%s&neighborhood=%s&city=%s&state=%s&postalcode=%s&format=jsonv2",
                NOMINATIM_URL,
                encode(street),
                encode(neighborhood),
                encode(city),
                encode(state),
                encode(postalCode)
        );

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "SolidarityNetwork/1.0 (mendes.profissional12@gmail.com)")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}