package com.safetyNet.safetyNetSystem.controller;

import com.safetyNet.safetyNetSystem.dto.PersonInfo;
import com.safetyNet.safetyNetSystem.model.Person;
import com.safetyNet.safetyNetSystem.service.PersonService;
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

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // Obtenir toutes les personnes sur la liste
    @GetMapping("/person")
    public List<Person> getAllPersons() throws IOException {
        return personService.getAllPersons();
    }

    // Ajouter une nouvelle personne
    @PostMapping("/person")
    public ResponseEntity<String> addPerson(@RequestBody Person person) throws IOException {
        personService.addPerson(person);
        return ResponseEntity.ok("Person added successfully.");
    }

    // Mettre à jour une personne existante
    @PutMapping("/person/{firstName}/{lastName}")
    public ResponseEntity<String> updatePerson(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody Person updatedPerson) throws IOException {
        Optional<Person> updated = personService.updatePerson(firstName, lastName, updatedPerson);

        if (updated.isPresent()) {
            return ResponseEntity.ok("Person updated successfully.");
        } else {
            return ResponseEntity.notFound().build(); // Personne non trouvée
        }
    }

    // Supprimer une personne
    @DeleteMapping("/person/{firstName}/{lastName}")
    public ResponseEntity<String> deletePerson(
            @PathVariable String firstName,
            @PathVariable String lastName) throws IOException {
        boolean removed = personService.deletePerson(firstName, lastName);

        if (removed) {
            return ResponseEntity.ok("Person deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/childAlert")
    public ChildrenAlertResponse getChildAlert(@RequestParam String address) throws IOException {
        return personService.getChildAlertByAddress(address);
    }

    // Route pour récupérer les informations des personnes par nom de famille
    @GetMapping("/personInfo")
    public List<PersonInfo> getPersonInfoByLastName(@RequestParam("lastName") String lastName) {
        // Appeler le service pour récupérer les informations des personnes par nom de famille
        return personService.getPersonInfoByLastName(lastName);
    }

}
