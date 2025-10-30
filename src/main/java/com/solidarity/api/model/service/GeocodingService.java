package com.solidarity.api.model.service;
import com.solidarity.api.dto.request.AddressRequest;
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

    public String getCoordinates(AddressRequest addressRequest) throws IOException, InterruptedException {
        String url = String.format("%s?street=%s&neighborhood=%s&city=%s&state=%s&postalcode=%s&format=jsonv2",
                NOMINATIM_URL,
                encode(addressRequest.getStreet()),
                encode(addressRequest.getNeighborhood()),
                encode(addressRequest.getCity()),
                encode(addressRequest.getState()),
                encode(addressRequest.getPostalCode())
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