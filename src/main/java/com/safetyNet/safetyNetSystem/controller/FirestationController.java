package com.safetyNet.safetyNetSystem.controller;

import com.safetyNet.safetyNetSystem.model.Firestation;
import com.safetyNet.safetyNetSystem.service.FirestationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @PathVariable String address,
            @RequestBody Firestation firestation) {
        Optional<Firestation> updatedFirestation = firestationService.updateFirestation(address, firestation.getStation());

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
}
