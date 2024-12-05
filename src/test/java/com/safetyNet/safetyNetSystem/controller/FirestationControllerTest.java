package com.safetyNet.safetyNetSystem.controller;

import com.safetyNet.safetyNetSystem.dto.FirestationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FirestationControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testGetPersonsCoveredByStation() {
        // URL de l'endpoint à tester
        String url = "http://localhost:" + port + "/firestation?stationNumber=3";

        // Effectuer la requête GET
        ResponseEntity<FirestationResponse> response = restTemplate.getForEntity(url, FirestationResponse.class);

        // Vérifier le statut HTTP de la réponse
        assertThat(response.getStatusCodeValue()).isEqualTo(200);

        // Vérifier que le corps de la réponse n'est pas nul
        FirestationResponse body = response.getBody();
        assertThat(body).isNotNull();

        // Vérifier les données spécifiques
        assertThat(body.getNumberOfAdults()).isGreaterThanOrEqualTo(0);
        assertThat(body.getNumberOfChildren()).isGreaterThanOrEqualTo(0);
    }
}
