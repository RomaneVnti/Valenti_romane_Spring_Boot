package com.safetyNet.safetyNetSystem.controller;

import com.safetyNet.safetyNetSystem.model.MedicalRecord;
import com.safetyNet.safetyNetSystem.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Classe de test unitaire pour le contrôleur MedicalRecordController.
 * Teste les différentes méthodes de ce contrôleur liées à la gestion des dossiers médicaux.
 */
class MedicalRecordControllerTest {

    @Mock
    private MedicalRecordService medicalRecordService;

    @InjectMocks
    private MedicalRecordController medicalRecordController;

    /**
     * Initialisation avant chaque test.
     * Ouvre les mocks nécessaires pour les tests.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Teste la méthode d'ajout d'un dossier médical.
     * Vérifie que le service est appelé et que la réponse est correcte.
     */
    @Test
    void testAddMedicalRecord() {
        // Mock d'entrée
        MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", "01/01/1980", List.of("aspirin"), List.of("pollen"));

        // Appeler le contrôleur
        ResponseEntity<String> response = medicalRecordController.addMedicalRecord(medicalRecord);

        // Vérifier le service
        verify(medicalRecordService, times(1)).addMedicalRecord(medicalRecord);

        // Vérifier la réponse
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Medical record added successfully.", response.getBody());
    }

    /**
     * Teste la mise à jour réussie d'un dossier médical.
     * Vérifie que le service est appelé et que la réponse est correcte.
     */
    @Test
    void testUpdateMedicalRecord_Success() {
        // Mock d'entrée
        String firstName = "John";
        String lastName = "Doe";
        MedicalRecord updatedRecord = new MedicalRecord("John", "Doe", "01/01/1980", List.of("ibuprofen"), List.of("dust"));
        when(medicalRecordService.updateMedicalRecord(eq(firstName), eq(lastName), any(MedicalRecord.class)))
                .thenReturn(Optional.of(updatedRecord));

        // Appeler le contrôleur
        ResponseEntity<String> response = medicalRecordController.updateMedicalRecord(firstName, lastName, updatedRecord);

        // Vérifier le service
        verify(medicalRecordService, times(1)).updateMedicalRecord(firstName, lastName, updatedRecord);

        // Vérifier la réponse
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Medical record updated successfully.", response.getBody());
    }

    /**
     * Teste la mise à jour d'un dossier médical qui n'existe pas.
     * Vérifie que la réponse est correcte pour un dossier introuvable.
     */
    @Test
    void testUpdateMedicalRecord_NotFound() {
        // Mock d'entrée
        String firstName = "John";
        String lastName = "Doe";
        MedicalRecord updatedRecord = new MedicalRecord("John", "Doe", "01/01/1980", List.of("ibuprofen"), List.of("dust"));
        when(medicalRecordService.updateMedicalRecord(eq(firstName), eq(lastName), any(MedicalRecord.class)))
                .thenReturn(Optional.empty());

        // Appeler le contrôleur
        ResponseEntity<String> response = medicalRecordController.updateMedicalRecord(firstName, lastName, updatedRecord);

        // Vérifier le service
        verify(medicalRecordService, times(1)).updateMedicalRecord(firstName, lastName, updatedRecord);

        // Vérifier la réponse
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    /**
     * Teste la suppression réussie d'un dossier médical.
     * Vérifie que le service est appelé et que la réponse est correcte.
     */
    @Test
    void testDeleteMedicalRecord_Success() {
        // Mock d'entrée
        String firstName = "John";
        String lastName = "Doe";
        when(medicalRecordService.deleteMedicalRecord(firstName, lastName)).thenReturn(true);

        // Appeler le contrôleur
        ResponseEntity<String> response = medicalRecordController.deleteMedicalRecord(firstName, lastName);

        // Vérifier le service
        verify(medicalRecordService, times(1)).deleteMedicalRecord(firstName, lastName);

        // Vérifier la réponse
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Medical record deleted successfully.", response.getBody());
    }

    /**
     * Teste la suppression d'un dossier médical qui n'existe pas.
     * Vérifie que la réponse est correcte pour un dossier introuvable.
     */
    @Test
    void testDeleteMedicalRecord_NotFound() {
        // Mock d'entrée
        String firstName = "John";
        String lastName = "Doe";
        when(medicalRecordService.deleteMedicalRecord(firstName, lastName)).thenReturn(false);

        // Appeler le contrôleur
        ResponseEntity<String> response = medicalRecordController.deleteMedicalRecord(firstName, lastName);

        // Vérifier le service
        verify(medicalRecordService, times(1)).deleteMedicalRecord(firstName, lastName);

        // Vérifier la réponse
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}
