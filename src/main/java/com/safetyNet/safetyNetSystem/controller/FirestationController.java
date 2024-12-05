package com.safetyNet.safetyNetSystem.controller;

import com.safetyNet.safetyNetSystem.model.Firestation;
import com.safetyNet.safetyNetSystem.service.FirestationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.safetyNet.safetyNetSystem.dto.FirestationResponse;
import com.safetyNet.safetyNetSystem.dto.FirestationResponseNoCount;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


import java.util.Optional;

@RestController
@RequestMapping("/firestation")
public class FirestationController {

    private final FirestationService firestationService;

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    // Ajouter une caserne/adresse
    @PostMapping
    public ResponseEntity<String> addFirestation(@RequestBody Firestation firestation) {
        firestationService.addFirestation(firestation);
        return ResponseEntity.ok("Firestation mapping added successfully.");
    }

    // Mettre à jour le numéro de caserne d'une adresse
    @PutMapping("/{address}")
    public ResponseEntity<String> updateFirestation(
            @PathVariable String address,  // Adresse de la caserne passée en PathVariable
            @RequestBody Firestation firestation) {  // Object Firestation passé dans le body
        // Appeler le service avec l'adresse et l'objet Firestation
        Optional<Firestation> updatedFirestation = firestationService.updateFirestation(address, firestation);

        if (updatedFirestation.isPresent()) {
            return ResponseEntity.ok("Firestation mapping updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Supprimer une caserne/adresse
    @DeleteMapping("/{address}")
    public ResponseEntity<String> deleteFirestation(@PathVariable String address) {
        boolean removed = firestationService.deleteFirestation(address);

        if (removed) {
            return ResponseEntity.ok("Firestation mapping deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Récupérer les personnes couvertes par une caserne
    @GetMapping
    public FirestationResponse getPersonsCoveredByStation(@RequestParam String stationNumber) {
        return firestationService.getPersonsCoveredByStation(stationNumber);
    }

}
