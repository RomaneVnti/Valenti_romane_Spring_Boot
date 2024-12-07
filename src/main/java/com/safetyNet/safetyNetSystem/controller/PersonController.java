package com.safetyNet.safetyNetSystem.controller;

import com.safetyNet.safetyNetSystem.dto.PersonInfo;
import com.safetyNet.safetyNetSystem.model.Person;
import com.safetyNet.safetyNetSystem.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.safetyNet.safetyNetSystem.dto.ChildrenAlertResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("")
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // Obtenir toutes les personnes sur la liste
    @GetMapping("/person")
    public List<Person> getAllPersons() throws IOException {
        logger.debug("Received request to get all persons");

        List<Person> persons = personService.getAllPersons();
        logger.info("Successfully retrieved all persons");
        return persons;
    }

    // Ajouter une nouvelle personne
    @PostMapping("/person")
    public ResponseEntity<String> addPerson(@RequestBody Person person) throws IOException {
        logger.debug("Received request to add person: {}", person);

        personService.addPerson(person);
        logger.info("Person added successfully: {}", person);
        return ResponseEntity.ok("Person added successfully.");
    }

    // Mettre à jour une personne existante
    @PutMapping("/person/{firstName}/{lastName}")
    public ResponseEntity<String> updatePerson(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody Person updatedPerson) throws IOException {
        logger.debug("Received request to update person: {} {}", firstName, lastName);

        Optional<Person> updated = personService.updatePerson(firstName, lastName, updatedPerson);

        if (updated.isPresent()) {
            logger.info("Person updated successfully: {} {}", firstName, lastName);
            return ResponseEntity.ok("Person updated successfully.");
        } else {
            logger.warn("Person not found for update: {} {}", firstName, lastName);
            return ResponseEntity.notFound().build(); // Personne non trouvée
        }
    }

    // Supprimer une personne
    @DeleteMapping("/person/{firstName}/{lastName}")
    public ResponseEntity<String> deletePerson(
            @PathVariable String firstName,
            @PathVariable String lastName) throws IOException {
        logger.debug("Received request to delete person: {} {}", firstName, lastName);

        boolean removed = personService.deletePerson(firstName, lastName);

        if (removed) {
            logger.info("Person deleted successfully: {} {}", firstName, lastName);
            return ResponseEntity.ok("Person deleted successfully.");
        } else {
            logger.warn("Person not found for deletion: {} {}", firstName, lastName);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/childAlert")
    public ChildrenAlertResponse getChildAlert(@RequestParam String address) throws IOException {
        logger.debug("Received request to get child alert for address: {}", address);

        ChildrenAlertResponse response = personService.getChildAlertByAddress(address);
        logger.info("Successfully retrieved child alert for address: {}", address);
        return response;
    }

    // Route pour récupérer les informations des personnes par nom de famille
    @GetMapping("/personInfo")
    public List<PersonInfo> getPersonInfoByLastName(@RequestParam("lastName") String lastName) {
        logger.debug("Received request to get person info for last name: {}", lastName);

        try {
            List<PersonInfo> personInfo = personService.getPersonInfoByLastName(lastName);
            logger.info("Successfully retrieved person info for last name: {}", lastName);
            return personInfo;
        } catch (Exception e) {
            logger.error("Error occurred while retrieving person info for last name: {}", lastName, e);
            return null;  // Vous pouvez aussi retourner une réponse personnalisée en cas d'erreur
        }
    }

    // Route pour récupérer les emails des habitants d'une ville
    @GetMapping("/communityEmail")
    public List<String> getEmailsByCity(@RequestParam("city") String city) {
        logger.debug("Received request to get emails for city: {}", city);

        try {
            List<String> emails = personService.getEmailsByCity(city);
            logger.info("Successfully retrieved emails for city: {}", city);
            return emails;
        } catch (Exception e) {
            logger.error("Error occurred while retrieving emails for city: {}", city, e);
            return null;  // Vous pouvez aussi retourner une réponse personnalisée en cas d'erreur
        }
    }
}
