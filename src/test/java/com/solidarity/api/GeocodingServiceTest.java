package com.solidarity.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solidarity.api.domain.service.GeocodingService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class GeocodingServiceTest {

    @Test
    void getCoordinatesSuccess() throws IOException, InterruptedException {
        GeocodingService geocodingService = new GeocodingService();

        String json = geocodingService.getCoordinates(
                "57800000",
                "Loteamento Santa Maria Madalena",
                "Rua Santa Clara de Assis",
                "União dos Palmares",
                "Alagoas"
        );

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(json);

        System.out.println("resultado: " +json);

        assertTrue(jsonNode.isArray(), "Precisa ser um array");
        assertFalse(jsonNode.isEmpty(), "A resposta não deve ser vazia");

        JsonNode result = jsonNode.get(0);

        String lat = result.get("lat").asText();
        String lon = result.get("lon").asText();

        System.out.println("latitude:" + lat + "longitude:" + lon);

        assertNotNull(lat);
        assertNotNull(lon);
    }
}
