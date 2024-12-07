package com.safetyNet.safetyNetSystem.controller;

import com.safetyNet.safetyNetSystem.dto.FirestationResponse;
import com.safetyNet.safetyNetSystem.model.Firestation;
import com.safetyNet.safetyNetSystem.service.FirestationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test du contrôleur FirestationController.
 */
class FirestationControllerTest {

    @Mock
    private FirestationService firestationService;

    @InjectMocks
    private FirestationController firestationController;

    /**
     * Initialisation avant chaque test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test de l'ajout d'une caserne de pompiers.
     */
    @Test
    void testAddFirestation() {
        Firestation firestation = new Firestation("123 Main St", "1");

        ResponseEntity<String> response = firestationController.addFirestation(firestation);

        verify(firestationService, times(1)).addFirestation(firestation);
        assertEquals(HttpStatus.OK, response.getStatusCode()); // Remplacer getStatusCodeValue() par getStatusCode()
        assertEquals("Firestation mapping added successfully.", response.getBody());
    }

    /**
     * Test de la mise à jour d'une caserne de pompiers avec succès.
     */
    @Test
    void testUpdateFirestation_Success() {
        String address = "123 Main St";
        Firestation firestation = new Firestation("123 Main St", "2");
        when(firestationService.updateFirestation(eq(address), any(Firestation.class)))
                .thenReturn(Optional.of(firestation));

        ResponseEntity<String> response = firestationController.updateFirestation(address, firestation);

        verify(firestationService, times(1)).updateFirestation(address, firestation);
        assertEquals(HttpStatus.OK, response.getStatusCode()); // Remplacer getStatusCodeValue() par getStatusCode()
        assertEquals("Firestation mapping updated successfully.", response.getBody());
    }

    /**
     * Test de la mise à jour d'une caserne de pompiers lorsque la caserne n'est pas trouvée.
     */
    @Test
    void testUpdateFirestation_NotFound() {
        String address = "123 Main St";
        Firestation firestation = new Firestation("123 Main St", "2");
        when(firestationService.updateFirestation(eq(address), any(Firestation.class)))
                .thenReturn(Optional.empty());

        ResponseEntity<String> response = firestationController.updateFirestation(address, firestation);

        verify(firestationService, times(1)).updateFirestation(address, firestation);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // Remplacer getStatusCodeValue() par getStatusCode()
        assertNull(response.getBody());
    }

    /**
     * Test de la suppression d'une caserne de pompiers avec succès.
     */
    @Test
    void testDeleteFirestation_Success() {
        String address = "123 Main St";
        when(firestationService.deleteFirestation(address)).thenReturn(true);

        ResponseEntity<String> response = firestationController.deleteFirestation(address);

        verify(firestationService, times(1)).deleteFirestation(address);
        assertEquals(HttpStatus.OK, response.getStatusCode()); // Remplacer getStatusCodeValue() par getStatusCode()
        assertEquals("Firestation mapping deleted successfully.", response.getBody());
    }

    /**
     * Test de la suppression d'une caserne de pompiers lorsque la caserne n'est pas trouvée.
     */
    @Test
    void testDeleteFirestation_NotFound() {
        String address = "123 Main St";
        when(firestationService.deleteFirestation(address)).thenReturn(false);

        ResponseEntity<String> response = firestationController.deleteFirestation(address);

        verify(firestationService, times(1)).deleteFirestation(address);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); // Remplacer getStatusCodeValue() par getStatusCode()
        assertNull(response.getBody());
    }

    /**
     * Test de la récupération des personnes couvertes par une caserne de pompiers.
     */
    @Test
    void testGetPersonsCoveredByStation() {
        String stationNumber = "1";
        FirestationResponse mockResponse = new FirestationResponse();
        mockResponse.setNumberOfAdults(5);
        mockResponse.setNumberOfChildren(3);
        when(firestationService.getPersonsCoveredByStation(stationNumber)).thenReturn(mockResponse);

        FirestationResponse response = firestationController.getPersonsCoveredByStation(stationNumber);

        verify(firestationService, times(1)).getPersonsCoveredByStation(stationNumber);
        assertNotNull(response);
        assertEquals(5, response.getNumberOfAdults());
        assertEquals(3, response.getNumberOfChildren());
    }
}
