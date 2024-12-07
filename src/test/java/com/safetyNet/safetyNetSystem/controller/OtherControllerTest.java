package com.safetyNet.safetyNetSystem.controller;

import com.safetyNet.safetyNetSystem.dto.FirestationResponseNoCount;
import com.safetyNet.safetyNetSystem.service.FirestationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class OtherControllerTest {

    @Mock
    private FirestationService firestationService;

    @InjectMocks
    private OtherController otherController;

    /**
     * Initialise les mocks avant chaque test.
     * Cette méthode est exécutée avant chaque méthode de test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Teste la récupération des numéros de téléphone par numéro de caserne.
     * Vérifie si le contrôleur renvoie correctement la liste des numéros de téléphone
     * d'une caserne donnée lorsque la méthode est appelée.
     */
    @Test
    void testGetPhoneNumbersByStation() {
        String firestationNumber = "1";
        List<String> mockPhoneNumbers = Arrays.asList("123-456-7890", "987-654-3210");

        // Simule la réponse du service
        when(firestationService.getPhoneNumbersByStation(firestationNumber)).thenReturn(mockPhoneNumbers);

        // Appelle le contrôleur
        ResponseEntity<List<String>> response = otherController.getPhoneNumbersByStation(firestationNumber);

        // Vérifie la réponse du contrôleur
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockPhoneNumbers, response.getBody());
    }

    /**
     * Teste la récupération des informations d'une caserne à partir d'une adresse.
     * Vérifie si le contrôleur renvoie correctement les informations de la caserne
     * lorsqu'une adresse est fournie.
     */
    @Test
    void testGetFirestationInfoByAddress() {
        String address = "1509 Culver St";
        FirestationResponseNoCount mockResponse = new FirestationResponseNoCount();
        mockResponse.setStationNumber("1");
        mockResponse.setPersons(new ArrayList<>()); // Liste vide pour simplification

        // Simule la réponse du service
        when(firestationService.getFirestationInfoByAddress(address)).thenReturn(mockResponse);

        // Appelle le contrôleur
        FirestationResponseNoCount response = otherController.getFirestationInfoByAddress(address);

        // Vérifie la réponse du contrôleur
        assertEquals(mockResponse.getStationNumber(), response.getStationNumber());
        assertEquals(mockResponse.getPersons(), response.getPersons());
    }

    /**
     * Teste la récupération des foyers desservis par plusieurs casernes.
     * Vérifie si le contrôleur renvoie correctement les foyers desservis par
     * les casernes spécifiées dans la liste donnée.
     */
    @Test
    void testGetFloodedStations() {
        List<String> stations = Arrays.asList("1", "2");
        List<FirestationResponseNoCount> mockResponses = new ArrayList<>();
        FirestationResponseNoCount response1 = new FirestationResponseNoCount();
        response1.setStationNumber("1");
        FirestationResponseNoCount response2 = new FirestationResponseNoCount();
        response2.setStationNumber("2");
        mockResponses.add(response1);
        mockResponses.add(response2);

        // Simule la réponse du service
        when(firestationService.getFloodedStations(stations)).thenReturn(mockResponses);

        // Appelle le contrôleur
        List<FirestationResponseNoCount> response = otherController.getFloodedStations(stations);

        // Vérifie la réponse du contrôleur
        assertEquals(mockResponses.size(), response.size());
        assertEquals(mockResponses.get(0).getStationNumber(), response.get(0).getStationNumber());
        assertEquals(mockResponses.get(1).getStationNumber(), response.get(1).getStationNumber());
    }
}
