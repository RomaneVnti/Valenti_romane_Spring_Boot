package com.safetyNet.safetyNetSystem.controller;

import com.safetyNet.safetyNetSystem.dto.PersonInfo;
import com.safetyNet.safetyNetSystem.dto.ChildrenAlertResponse;
import com.safetyNet.safetyNetSystem.model.Person;
import com.safetyNet.safetyNetSystem.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.safetyNet.safetyNetSystem.dto.ChildInfo;

import java.io.IOException;
import java.util.Optional;

/**
 * Test unitaire de la classe PersonController.
 * Les tests vérifient les fonctionnalités de gestion des personnes telles que l'ajout, la mise à jour, la suppression et la récupération d'informations.
 */
class PersonControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test pour récupérer toutes les personnes.
     * Vérifie que la méthode `getAllPersons()` retourne la liste correcte de personnes.
     *
     * @throws IOException si une erreur se produit lors de la récupération des données.
     */
    @Test
    void testGetAllPersons() throws IOException {
        Person person1 = new Person();
        Person person2 = new Person();
        List<Person> mockPersons = Arrays.asList(person1, person2);

        when(personService.getAllPersons()).thenReturn(mockPersons);

        List<Person> response = personController.getAllPersons();

        assertEquals(mockPersons, response);
    }

    /**
     * Test pour ajouter une nouvelle personne.
     * Vérifie que l'ajout d'une personne retourne la réponse correcte.
     *
     * @throws IOException si une erreur se produit lors de l'ajout de la personne.
     */
    @Test
    void testAddPerson() throws IOException {
        Person person = new Person();
        ResponseEntity<String> response = personController.addPerson(person);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Person added successfully.", response.getBody());
    }

    /**
     * Test pour mettre à jour une personne existante.
     * Vérifie que la mise à jour d'une personne retourne la réponse correcte.
     *
     * @throws IOException si une erreur se produit lors de la mise à jour de la personne.
     */
    @Test
    void testUpdatePerson() throws IOException {
        String firstName = "John";
        String lastName = "Doe";
        Person updatedPerson = new Person();
        Optional<Person> updated = Optional.of(updatedPerson);

        when(personService.updatePerson(firstName, lastName, updatedPerson)).thenReturn(updated);

        ResponseEntity<String> response = personController.updatePerson(firstName, lastName, updatedPerson);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Person updated successfully.", response.getBody());
    }

    /**
     * Test pour supprimer une personne.
     * Vérifie que la suppression d'une personne retourne la réponse correcte.
     *
     * @throws IOException si une erreur se produit lors de la suppression de la personne.
     */
    @Test
    void testDeletePerson() throws IOException {
        String firstName = "John";
        String lastName = "Doe";
        boolean removed = true;

        when(personService.deletePerson(firstName, lastName)).thenReturn(removed);

        ResponseEntity<String> response = personController.deletePerson(firstName, lastName);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Person deleted successfully.", response.getBody());
    }

    /**
     * Test pour récupérer les alertes enfants par adresse.
     * Vérifie que la méthode `getChildAlert()` retourne les alertes d'enfants et d'adultes pour une adresse donnée.
     *
     * @throws IOException si une erreur se produit lors de la récupération des alertes.
     */
    @Test
    void testGetChildAlert() throws IOException {
        String address = "123 Main St";

        // Créez des listes simulées pour les enfants et les adultes
        ChildInfo child1 = new ChildInfo("Johnny", "Doe", 5);
        ChildInfo child2 = new ChildInfo("Sally", "Doe", 8);
        List<ChildInfo> children = Arrays.asList(child1, child2);

        PersonInfo adult1 = new PersonInfo("John", "Doe", address, "123-456-7890");
        PersonInfo adult2 = new PersonInfo("Jane", "Doe", address, "987-654-3210");
        List<PersonInfo> adults = Arrays.asList(adult1, adult2);

        // Créez l'objet ChildrenAlertResponse avec les listes créées
        ChildrenAlertResponse mockResponse = new ChildrenAlertResponse(children, adults);

        // Simulez l'appel au service
        when(personService.getChildAlertByAddress(address)).thenReturn(mockResponse);

        // Appel du contrôleur
        ChildrenAlertResponse response = personController.getChildAlert(address);

        // Vérification du résultat
        assertEquals(mockResponse, response);
    }

    /**
     * Test pour récupérer les informations des personnes par nom de famille.
     * Vérifie que la méthode `getPersonInfoByLastName()` retourne les informations des personnes correspondant à un nom de famille donné.
     */
    @Test
    void testGetPersonInfoByLastName() {
        String lastName = "Doe";

        // Créez des objets PersonInfo avec les arguments requis
        PersonInfo personInfo1 = new PersonInfo("John", "Doe", "123 Main St", "123-456-7890");
        PersonInfo personInfo2 = new PersonInfo("Jane", "Doe", "456 Elm St", "987-654-3210");

        // Liste des objets PersonInfo à renvoyer
        List<PersonInfo> mockResponse = Arrays.asList(personInfo1, personInfo2);

        // Simulation de l'appel du service
        when(personService.getPersonInfoByLastName(lastName)).thenReturn(mockResponse);

        // Appel du contrôleur
        List<PersonInfo> response = personController.getPersonInfoByLastName(lastName);

        // Vérification du résultat
        assertEquals(mockResponse, response);
    }

    /**
     * Test pour récupérer les emails des habitants d'une ville.
     * Vérifie que la méthode `getEmailsByCity()` retourne les emails des personnes d'une ville donnée.
     */
    @Test
    void testGetEmailsByCity() {
        String city = "Paris";
        List<String> mockEmails = Arrays.asList("john.doe@example.com", "jane.doe@example.com");

        when(personService.getEmailsByCity(city)).thenReturn(mockEmails);

        List<String> response = personController.getEmailsByCity(city);

        assertEquals(mockEmails, response);
    }
}
