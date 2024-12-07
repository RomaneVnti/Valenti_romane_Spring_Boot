package com.safetyNet.safetyNetSystem.controller;

import com.safetyNet.safetyNetSystem.dto.PersonInfo;
import com.safetyNet.safetyNetSystem.model.Person;
import com.safetyNet.safetyNetSystem.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.safetyNet.safetyNetSystem.dto.ChildrenAlertResponse;

import java.util.List;
import java.util.Optional;

/**
 * Contrôleur pour gérer les opérations liées aux personnes dans le système.
 */
@RestController
@RequestMapping("")
public class PersonController {

    private static final Logger logger = LogManager.getLogger(PersonController.class);

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Récupérer toutes les personnes dans le système.
     *
     * @return une liste de toutes les personnes
     */
    @GetMapping("/person")
    public List<Person> getAllPersons() {
        logger.debug("Received request to get all persons");

        List<Person> persons = personService.getAllPersons();
        logger.info("Successfully retrieved all persons");
        return persons;
    }

    /**
     * Ajouter une nouvelle personne dans le système.
     *
     * @param person l'objet représentant la personne à ajouter
     * @return une réponse indiquant si l'ajout a réussi
     */
    @PostMapping("/person")
    public ResponseEntity<String> addPerson(@RequestBody Person person) {
        logger.debug("Received request to add person: {}", person);

        personService.addPerson(person);
        logger.info("Person added successfully: {}", person);
        return ResponseEntity.ok("Person added successfully.");
    }

    /**
     * Mettre à jour les informations d'une personne existante.
     *
     * @param firstName le prénom de la personne à mettre à jour
     * @param lastName le nom de famille de la personne à mettre à jour
     * @param updatedPerson l'objet représentant les nouvelles informations de la personne
     * @return une réponse indiquant si la mise à jour a réussi
     */
    @PutMapping("/person/{firstName}/{lastName}")
    public ResponseEntity<String> updatePerson(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody Person updatedPerson) {
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

    /**
     * Supprimer une personne du système.
     *
     * @param firstName le prénom de la personne à supprimer
     * @param lastName le nom de famille de la personne à supprimer
     * @return une réponse indiquant si la suppression a réussi
     */
    @DeleteMapping("/person/{firstName}/{lastName}")
    public ResponseEntity<String> deletePerson(
            @PathVariable String firstName,
            @PathVariable String lastName) {
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

    /**
     * Récupérer les alertes concernant les enfants d'une adresse spécifique.
     *
     * @param address l'adresse pour laquelle récupérer les alertes enfants
     * @return une réponse contenant les alertes pour les enfants à cette adresse
     */
    @GetMapping("/childAlert")
    public ChildrenAlertResponse getChildAlert(@RequestParam String address) {
        logger.debug("Received request to get child alert for address: {}", address);

        ChildrenAlertResponse response = personService.getChildAlertByAddress(address);
        logger.info("Successfully retrieved child alert for address: {}", address);
        return response;
    }

    /**
     * Récupérer les informations des personnes par leur nom de famille.
     *
     * @param lastName le nom de famille pour lequel récupérer les informations
     * @return une liste des informations des personnes ayant ce nom de famille
     */
    @GetMapping("/personInfo")
    public List<PersonInfo> getPersonInfoByLastName(@RequestParam("lastName") String lastName) {
        logger.debug("Received request to get person info for last name: {}", lastName);

        try {
            List<PersonInfo> personInfo = personService.getPersonInfoByLastName(lastName);
            logger.info("Successfully retrieved person info for last name: {}", lastName);
            return personInfo;
        } catch (Exception e) {
            logger.error("Error occurred while retrieving person info for last name: {}", lastName, e);
            return null; // Vous pouvez aussi retourner une réponse personnalisée en cas d'erreur
        }
    }

    /**
     * Récupérer les emails des habitants d'une ville donnée.
     *
     * @param city le nom de la ville pour laquelle récupérer les emails
     * @return une liste des emails des habitants de la ville
     */
    @GetMapping("/communityEmail")
    public List<String> getEmailsByCity(@RequestParam("city") String city) {
        logger.debug("Received request to get emails for city: {}", city);

        try {
            List<String> emails = personService.getEmailsByCity(city);
            logger.info("Successfully retrieved emails for city: {}", city);
            return emails;
        } catch (Exception e) {
            logger.error("Error occurred while retrieving emails for city: {}", city, e);
            return null; // Vous pouvez aussi retourner une réponse personnalisée en cas d'erreur
        }
    }
}
