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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test pour récupérer les numéros de téléphone par numéro de caserne
    @Test
    void testGetPhoneNumbersByStation() {
        String firestationNumber = "1";
        List<String> mockPhoneNumbers = Arrays.asList("123-456-7890", "987-654-3210");

        when(firestationService.getPhoneNumbersByStation(firestationNumber)).thenReturn(mockPhoneNumbers);

        ResponseEntity<List<String>> response = otherController.getPhoneNumbersByStation(firestationNumber);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockPhoneNumbers, response.getBody());
    }

    // Test pour récupérer les informations d'une caserne à partir d'une adresse
    @Test
    void testGetFirestationInfoByAddress() {
        String address = "1509 Culver St";
        FirestationResponseNoCount mockResponse = new FirestationResponseNoCount();
        mockResponse.setStationNumber("1");
        mockResponse.setPersons(new ArrayList<>()); // Ajoutez une liste vide pour simplifier

        when(firestationService.getFirestationInfoByAddress(address)).thenReturn(mockResponse);

        FirestationResponseNoCount response = otherController.getFirestationInfoByAddress(address);

        assertEquals(mockResponse.getStationNumber(), response.getStationNumber());
        assertEquals(mockResponse.getPersons(), response.getPersons());
    }

    // Test pour récupérer les foyers desservis par plusieurs casernes
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

        when(firestationService.getFloodedStations(stations)).thenReturn(mockResponses);

        List<FirestationResponseNoCount> response = otherController.getFloodedStations(stations);

        assertEquals(mockResponses.size(), response.size());
        assertEquals(mockResponses.get(0).getStationNumber(), response.get(0).getStationNumber());
        assertEquals(mockResponses.get(1).getStationNumber(), response.get(1).getStationNumber());
    }
}
