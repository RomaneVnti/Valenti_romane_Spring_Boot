package com.safetyNet.safetyNetSystem.service;

import com.safetyNet.safetyNetSystem.dao.MedicalRecordDAO;
import com.safetyNet.safetyNetSystem.model.MedicalRecord;
import com.safetyNet.safetyNetSystem.model.Person;
import com.safetyNet.safetyNetSystem.service.MedicalRecordService;
import com.safetyNet.safetyNetSystem.util.DataLoaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicalRecordServiceTest {

    private MedicalRecordDAO medicalRecordDAO;
    private MedicalRecordService medicalRecordService;
    private DataLoaderUtil dataLoaderUtil;

    @BeforeEach
    void setUp() {
        medicalRecordDAO = mock(MedicalRecordDAO.class);
        dataLoaderUtil = mock(DataLoaderUtil.class);
        medicalRecordService = new MedicalRecordService(medicalRecordDAO, dataLoaderUtil);
    }

    @Test
    void testGetAllMedicalRecords() {
        // Création d'exemples de données simulées
        List<MedicalRecord> medicalRecords = Arrays.asList(
                new MedicalRecord("John", "Doe", "01/01/1980", Arrays.asList("Aspirin"), Arrays.asList("Peanuts")),
                new MedicalRecord("Jane", "Smith", "02/02/1990", Arrays.asList("Ibuprofen"), Arrays.asList("Dust"))
        );

        // Simuler le comportement du DAO
        when(medicalRecordDAO.getAllMedicalRecords()).thenReturn(medicalRecords);

        // Appel de la méthode à tester
        List<MedicalRecord> result = medicalRecordService.getAllMedicalRecords();

        // Vérification des résultats
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
    }

    @Test
    void testAddMedicalRecord() {
        // Création d'un enregistrement médical à ajouter
        MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", "01/01/1980", Arrays.asList("Aspirin"), Arrays.asList("Peanuts"));

        // Appel de la méthode à tester
        medicalRecordService.addMedicalRecord(medicalRecord);

        // Vérification que la méthode addMedicalRecord a bien été appelée sur le DAO
        verify(medicalRecordDAO, times(1)).addMedicalRecord(medicalRecord);
    }

    @Test
    void testUpdateMedicalRecord() {
        // Arrange: Création de l'enregistrement médical existant et de la mise à jour
        MedicalRecord updatedRecord = new MedicalRecord("John", "Doe", "01/01/1980", Arrays.asList("Aspirin", "Paracetamol"), Arrays.asList("Peanuts"));

        // Simuler le comportement du DAO pour l'update
        when(medicalRecordDAO.updateMedicalRecord("John", "Doe", updatedRecord)).thenReturn(Optional.of(updatedRecord));

        // Appel de la méthode à tester
        Optional<MedicalRecord> result = medicalRecordService.updateMedicalRecord("John", "Doe", updatedRecord);

        // Vérification que l'enregistrement a bien été mis à jour
        assertTrue(result.isPresent());
        assertEquals("Paracetamol", result.get().getMedications().get(1));  // Vérifie que la mise à jour a bien eu lieu
    }

    @Test
    void testDeleteMedicalRecord() {
        // Simuler le comportement du DAO pour la suppression
        when(medicalRecordDAO.deleteMedicalRecord("John", "Doe")).thenReturn(true);

        // Appel de la méthode à tester
        boolean result = medicalRecordService.deleteMedicalRecord("John", "Doe");

        // Vérification que la suppression a bien eu lieu
        assertTrue(result);
        verify(medicalRecordDAO, times(1)).deleteMedicalRecord("John", "Doe");
    }

    @Test
    void testGetMedicalRecordByPerson() {
        // Création d'une personne et d'un enregistrement médical
        Person person = new Person("John", "Doe", "123 Main St", "City", "12345", "123-456-7890", "john.doe@example.com");
        MedicalRecord medicalRecord = new MedicalRecord("John", "Doe", "01/01/1980", Arrays.asList("Aspirin"), Arrays.asList("Peanuts"));

        // Simuler le comportement du DAO
        when(medicalRecordDAO.getMedicalRecordByPerson("John", "Doe")).thenReturn(Optional.of(medicalRecord));

        // Appel de la méthode à tester
        Optional<MedicalRecord> result = medicalRecordService.getMedicalRecordByPerson(person);

        // Vérification que l'enregistrement médical a bien été trouvé
        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
        assertEquals("Doe", result.get().getLastName());
    }
}
