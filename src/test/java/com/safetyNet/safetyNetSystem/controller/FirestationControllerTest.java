package com.safetyNet.safetyNetSystem.controller;

import com.safetyNet.safetyNetSystem.dto.FirestationResponse;
import com.safetyNet.safetyNetSystem.model.Firestation;
import com.safetyNet.safetyNetSystem.service.FirestationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FirestationControllerTest {

    @Mock
    private FirestationService firestationService;

    @InjectMocks
    private FirestationController firestationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddFirestation() {
        // Mock d'entrée
        Firestation firestation = new Firestation("123 Main St", "1");

        // Appeler le contrôleur
        ResponseEntity<String> response = firestationController.addFirestation(firestation);

        // Vérifier le service
        verify(firestationService, times(1)).addFirestation(firestation);

        // Vérifier la réponse
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Firestation mapping added successfully.", response.getBody());
    }

    @Test
    void testUpdateFirestation_Success() {
        // Mock d'entrée
        String address = "123 Main St";
        Firestation firestation = new Firestation("123 Main St", "2");
        when(firestationService.updateFirestation(eq(address), any(Firestation.class)))
                .thenReturn(Optional.of(firestation));

        // Appeler le contrôleur
        ResponseEntity<String> response = firestationController.updateFirestation(address, firestation);

        // Vérifier le service
        verify(firestationService, times(1)).updateFirestation(address, firestation);

        // Vérifier la réponse
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Firestation mapping updated successfully.", response.getBody());
    }

    @Test
    void testUpdateFirestation_NotFound() {
        // Mock d'entrée
        String address = "123 Main St";
        Firestation firestation = new Firestation("123 Main St", "2");
        when(firestationService.updateFirestation(eq(address), any(Firestation.class)))
                .thenReturn(Optional.empty());

        // Appeler le contrôleur
        ResponseEntity<String> response = firestationController.updateFirestation(address, firestation);

        // Vérifier le service
        verify(firestationService, times(1)).updateFirestation(address, firestation);

        // Vérifier la réponse
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteFirestation_Success() {
        // Mock d'entrée
        String address = "123 Main St";
        when(firestationService.deleteFirestation(address)).thenReturn(true);

        // Appeler le contrôleur
        ResponseEntity<String> response = firestationController.deleteFirestation(address);

        // Vérifier le service
        verify(firestationService, times(1)).deleteFirestation(address);

        // Vérifier la réponse
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Firestation mapping deleted successfully.", response.getBody());
    }

    @Test
    void testDeleteFirestation_NotFound() {
        // Mock d'entrée
        String address = "123 Main St";
        when(firestationService.deleteFirestation(address)).thenReturn(false);

        // Appeler le contrôleur
        ResponseEntity<String> response = firestationController.deleteFirestation(address);

        // Vérifier le service
        verify(firestationService, times(1)).deleteFirestation(address);

        // Vérifier la réponse
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testGetPersonsCoveredByStation() {
        // Mock d'entrée
        String stationNumber = "1";
        FirestationResponse mockResponse = new FirestationResponse();
        mockResponse.setNumberOfAdults(5);
        mockResponse.setNumberOfChildren(3);
        when(firestationService.getPersonsCoveredByStation(stationNumber)).thenReturn(mockResponse);

        // Appeler le contrôleur
        FirestationResponse response = firestationController.getPersonsCoveredByStation(stationNumber);

        // Vérifier le service
        verify(firestationService, times(1)).getPersonsCoveredByStation(stationNumber);

        // Vérifier la réponse
        assertNotNull(response);
        assertEquals(5, response.getNumberOfAdults());
        assertEquals(3, response.getNumberOfChildren());
    }
}
