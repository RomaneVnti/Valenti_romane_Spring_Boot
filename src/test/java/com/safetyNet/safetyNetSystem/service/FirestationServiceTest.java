package com.safetyNet.safetyNetSystem.service;

import com.safetyNet.safetyNetSystem.dao.FirestationDAO;
import com.safetyNet.safetyNetSystem.dto.FirestationResponse;
import com.safetyNet.safetyNetSystem.dto.FirestationResponseNoCount;
import com.safetyNet.safetyNetSystem.dto.PersonInfo;
import com.safetyNet.safetyNetSystem.model.Firestation;
import com.safetyNet.safetyNetSystem.model.Person;
import com.safetyNet.safetyNetSystem.model.MedicalRecord;
import com.safetyNet.safetyNetSystem.util.DateUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class FirestationServiceTest {

    @Mock
    private FirestationDAO firestationDAO;

    @Mock
    private PersonService personService;

    @Mock
    private MedicalRecordService medicalRecordService;

    @InjectMocks
    private FirestationService firestationService;

    private List<Firestation> firestations;
    private List<Person> persons;
    private List<MedicalRecord> medicalRecords;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup mock firestations
        firestations = new ArrayList<>();
        Firestation firestation1 = new Firestation("123 Main St", "1");
        firestations.add(firestation1);

        // Setup mock persons
        persons = new ArrayList<>();
        Person person1 = new Person("John", "Doe", "123 Main St", "City", "12345", "123-456-7890", "john.doe@example.com");
        persons.add(person1);

        // Setup mock medical records
        medicalRecords = new ArrayList<>();  // Initialisation de la liste medicalRecords
        List<String> medications = new ArrayList<>();
        medications.add("Aspirin");
        List<String> allergies = new ArrayList<>();
        allergies.add("Peanuts");
        MedicalRecord medicalRecord1 = new MedicalRecord("John", "Doe", "01/01/1980", medications, allergies);
        medicalRecords.add(medicalRecord1);
    }


    @Test
    public void testGetAllFirestations() {
        when(firestationDAO.getAllFirestations()).thenReturn(firestations);

        List<Firestation> result = firestationService.getAllFirestations();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("123 Main St", result.get(0).getAddress());
    }

    @Test
    public void testAddFirestation() {
        Firestation firestation = new Firestation("2", "456 Elm St");

        firestationService.addFirestation(firestation);

        verify(firestationDAO, times(1)).addFirestation(firestation);
    }

    @Test
    public void testUpdateFirestation() {
        Firestation updatedFirestation = new Firestation("789 Oak St", "1");

        when(firestationDAO.updateFirestation("1", updatedFirestation)).thenReturn(Optional.of(updatedFirestation));

        Optional<Firestation> result = firestationService.updateFirestation("1", updatedFirestation);

        assertTrue(result.isPresent());
        assertEquals("789 Oak St", result.get().getAddress());
    }

    @Test
    public void testDeleteFirestation() {
        when(firestationDAO.deleteFirestation("1")).thenReturn(true);

        boolean result = firestationService.deleteFirestation("1");

        assertTrue(result);
        verify(firestationDAO, times(1)).deleteFirestation("1");
    }

    @Test
    public void testGetPersonsCoveredByStation() {
        when(personService.getAllPersons()).thenReturn(persons);
        when(firestationDAO.getAllFirestations()).thenReturn(firestations);
        when(medicalRecordService.getMedicalRecordByPerson(any(Person.class))).thenReturn(Optional.of(medicalRecords.get(0)));

        FirestationResponse response = firestationService.getPersonsCoveredByStation("1");

        assertNotNull(response);
        assertEquals(1, response.getPersons().size());  // Check the number of persons
        assertEquals(1, response.getNumberOfAdults()); // Ensure the number of adults is correct
        assertEquals(0, response.getNumberOfChildren());  // Ensure there are no children
    }

    @Test
    public void testGetPhoneNumbersByStation() {
        when(personService.getAllPersons()).thenReturn(persons);
        when(firestationDAO.getAllFirestations()).thenReturn(firestations);

        List<String> phoneNumbers = firestationService.getPhoneNumbersByStation("1");

        assertNotNull(phoneNumbers);
        assertEquals(1, phoneNumbers.size()); // Ensure only one phone number
        assertEquals("123-456-7890", phoneNumbers.get(0));  // Check the phone number
    }

    @Test
    public void testGetFirestationInfoByAddress() {
        when(firestationDAO.getAllFirestations()).thenReturn(firestations);
        when(personService.getPersonsWithMedicalInfoByAddress("123 Main St", true)).thenReturn(new ArrayList<>());

        FirestationResponseNoCount response = firestationService.getFirestationInfoByAddress("123 Main St");

        assertNotNull(response);
        assertEquals("1", response.getStationNumber());   // Ensure the station number is null if not provided
        assertTrue(response.getPersons().isEmpty());  // Check that no persons are associated
    }
}