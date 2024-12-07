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

/**
 * Test de la classe {@link FirestationService}.
 * Cette classe de test vérifie les méthodes du service FirestationService, notamment celles qui manipulent
 * les casernes de pompiers, les personnes couvertes par ces casernes et les informations médicales.
 */
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

    /**
     * Initialise les objets nécessaires pour les tests avant chaque méthode de test.
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialisation des casernes
        firestations = new ArrayList<>();
        Firestation firestation1 = new Firestation("123 Main St", "1");
        firestations.add(firestation1);

        // Initialisation des personnes
        persons = new ArrayList<>();
        Person person1 = new Person("John", "Doe", "123 Main St", "City", "12345", "123-456-7890", "john.doe@example.com");
        persons.add(person1);

        // Initialisation des dossiers médicaux
        medicalRecords = new ArrayList<>();
        List<String> medications = new ArrayList<>();
        medications.add("Aspirin");
        List<String> allergies = new ArrayList<>();
        allergies.add("Peanuts");
        MedicalRecord medicalRecord1 = new MedicalRecord("John", "Doe", "01/01/1980", medications, allergies);
        medicalRecords.add(medicalRecord1);
    }

    /**
     * Teste la méthode {@link FirestationService#getAllFirestations()} pour récupérer toutes les casernes de pompiers.
     * Verifie que la méthode retourne bien la liste des casernes.
     */
    @Test
    public void testGetAllFirestations() {
        when(firestationDAO.getAllFirestations()).thenReturn(firestations);

        List<Firestation> result = firestationService.getAllFirestations();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("123 Main St", result.get(0).getAddress());
    }

    /**
     * Teste la méthode {@link FirestationService#addFirestation(Firestation)} pour ajouter une nouvelle caserne.
     * Vérifie que la méthode addFirestation de FirestationDAO est appelée.
     */
    @Test
    public void testAddFirestation() {
        Firestation firestation = new Firestation("2", "456 Elm St");

        firestationService.addFirestation(firestation);

        verify(firestationDAO, times(1)).addFirestation(firestation);
    }

    /**
     * Teste la méthode {@link FirestationService#updateFirestation(String, Firestation)} pour mettre à jour une caserne.
     * Vérifie que la caserne mise à jour est bien retournée par le service.
     */
    @Test
    public void testUpdateFirestation() {
        Firestation updatedFirestation = new Firestation("789 Oak St", "1");

        when(firestationDAO.updateFirestation("1", updatedFirestation)).thenReturn(Optional.of(updatedFirestation));

        Optional<Firestation> result = firestationService.updateFirestation("1", updatedFirestation);

        assertTrue(result.isPresent());
        assertEquals("789 Oak St", result.get().getAddress());
    }

    /**
     * Teste la méthode {@link FirestationService#deleteFirestation(String)} pour supprimer une caserne.
     * Vérifie que la méthode deleteFirestation de FirestationDAO est appelée et que la caserne est bien supprimée.
     */
    @Test
    public void testDeleteFirestation() {
        when(firestationDAO.deleteFirestation("1")).thenReturn(true);

        boolean result = firestationService.deleteFirestation("1");

        assertTrue(result);
        verify(firestationDAO, times(1)).deleteFirestation("1");
    }

    /**
     * Teste la méthode {@link FirestationService#getPersonsCoveredByStation(String)} pour obtenir les personnes couvertes par une caserne.
     * Vérifie que les informations des personnes sont bien retournées, incluant le nombre d'adultes et d'enfants.
     */
    @Test
    public void testGetPersonsCoveredByStation() {
        when(personService.getAllPersons()).thenReturn(persons);
        when(firestationDAO.getAllFirestations()).thenReturn(firestations);
        when(medicalRecordService.getMedicalRecordByPerson(any(Person.class))).thenReturn(Optional.of(medicalRecords.get(0)));

        FirestationResponse response = firestationService.getPersonsCoveredByStation("1");

        assertNotNull(response);
        assertEquals(1, response.getPersons().size());  // Vérifie le nombre de personnes couvertes
        assertEquals(1, response.getNumberOfAdults());  // Vérifie le nombre d'adultes
        assertEquals(0, response.getNumberOfChildren());  // Vérifie le nombre d'enfants
    }

    /**
     * Teste la méthode {@link FirestationService#getPhoneNumbersByStation(String)} pour obtenir les numéros de téléphone des personnes couvertes par une caserne.
     * Vérifie que la méthode retourne bien la liste des numéros de téléphone.
     */
    @Test
    public void testGetPhoneNumbersByStation() {
        when(personService.getAllPersons()).thenReturn(persons);
        when(firestationDAO.getAllFirestations()).thenReturn(firestations);

        List<String> phoneNumbers = firestationService.getPhoneNumbersByStation("1");

        assertNotNull(phoneNumbers);
        assertEquals(1, phoneNumbers.size()); // Vérifie qu'il y a un seul numéro
        assertEquals("123-456-7890", phoneNumbers.get(0));  // Vérifie le numéro de téléphone
    }

    /**
     * Teste la méthode {@link FirestationService#getFirestationInfoByAddress(String)} pour obtenir des informations sur une caserne à partir de son adresse.
     * Vérifie que les informations retournées sont correctes, incluant le numéro de station et les personnes associées.
     */
    @Test
    public void testGetFirestationInfoByAddress() {
        when(firestationDAO.getAllFirestations()).thenReturn(firestations);
        when(personService.getPersonsWithMedicalInfoByAddress("123 Main St", true)).thenReturn(new ArrayList<>());

        FirestationResponseNoCount response = firestationService.getFirestationInfoByAddress("123 Main St");

        assertNotNull(response);
        assertEquals("1", response.getStationNumber());   // Vérifie le numéro de la station
        assertTrue(response.getPersons().isEmpty());  // Vérifie qu'il n'y a pas de personnes associées
    }

    /**
     * Teste la méthode {@link FirestationService#getFloodedStations(List)} pour obtenir les stations inondées à partir d'une liste de numéros de station.
     * Vérifie que la méthode retourne bien les stations et les personnes associées.
     */
    @Test
    public void testGetFloodedStations() {
        // Préparer des données de test
        List<String> stationsRequested = new ArrayList<>();
        stationsRequested.add("1");

        // Mock des casernes et des personnes
        List<Firestation> firestations = new ArrayList<>();
        Firestation firestation1 = new Firestation("123 Main St", "1");
        firestations.add(firestation1);

        List<Person> persons = new ArrayList<>();
        Person person1 = new Person("John", "Doe", "123 Main St", "City", "12345", "123-456-7890", "john.doe@example.com");
        persons.add(person1);

        // Mock des services
        when(firestationDAO.getAllFirestations()).thenReturn(firestations);
        when(personService.getAllPersons()).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordByPerson(any(Person.class))).thenReturn(Optional.of(medicalRecords.get(0)));

        // Appel de la méthode
        List<FirestationResponseNoCount> floodedStations = firestationService.getFloodedStations(stationsRequested);

        // Vérification des résultats
        assertNotNull(floodedStations);
        assertEquals(1, floodedStations.size());  // Vérifie qu'une station est retournée
        assertEquals("1", floodedStations.get(0).getStationNumber());  // Vérifie le numéro de station
        assertEquals(1, floodedStations.get(0).getPersons().size());  // Vérifie le nombre de personnes
    }

    /**
     * Teste le cas où la liste des stations demandées est vide dans la méthode {@link FirestationService#getFloodedStations(List)}.
     * Vérifie que la méthode retourne une liste vide.
     */
    @Test
    public void testGetFloodedStationsEmptyList() {
        List<String> stationsRequested = new ArrayList<>();
        List<FirestationResponseNoCount> floodedStations = firestationService.getFloodedStations(stationsRequested);

        assertNotNull(floodedStations);
        assertTrue(floodedStations.isEmpty());  // La liste devrait être vide
    }

    /**
     * Teste le cas où aucune station ne correspond dans la méthode {@link FirestationService#getFloodedStations(List)}.
     * Vérifie que la méthode retourne une liste vide lorsque la station demandée n'existe pas.
     */
    @Test
    public void testGetFloodedStationsNoMatchingStation() {
        List<String> stationsRequested = new ArrayList<>();
        stationsRequested.add("999");  // Station inexistante

        when(firestationDAO.getAllFirestations()).thenReturn(firestations);

        List<FirestationResponseNoCount> floodedStations = firestationService.getFloodedStations(stationsRequested);

        assertNotNull(floodedStations);
        assertTrue(floodedStations.isEmpty());  // Aucune station trouvée
    }
}
