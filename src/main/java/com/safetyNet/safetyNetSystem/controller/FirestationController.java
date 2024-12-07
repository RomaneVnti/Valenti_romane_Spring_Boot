package com.safetyNet.safetyNetSystem.controller;

import com.safetyNet.safetyNetSystem.model.Firestation;
import com.safetyNet.safetyNetSystem.service.FirestationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.safetyNet.safetyNetSystem.dto.FirestationResponse;
import com.safetyNet.safetyNetSystem.dto.FirestationResponseNoCount;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/firestation")
public class FirestationController {

    private static final Logger logger = LogManager.getLogger(FirestationController.class);

    private final FirestationService firestationService;

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    // Ajouter une caserne/adresse
    @PostMapping
    public ResponseEntity<String> addFirestation(@RequestBody Firestation firestation) {
        logger.debug("Received request to add firestation: {}", firestation);

        try {
            firestationService.addFirestation(firestation);
            logger.info("Firestation mapping added successfully: {}", firestation);
            return ResponseEntity.ok("Firestation mapping added successfully.");
        } catch (Exception e) {
            logger.error("Error occurred while adding firestation: {}", firestation, e);
            return ResponseEntity.status(500).body("Failed to add firestation.");
        }
    }

    // Mettre à jour le numéro de caserne d'une adresse
    @PutMapping("/{address}")
    public ResponseEntity<String> updateFirestation(
            @PathVariable String address,
            @RequestBody Firestation firestation) {

        logger.debug("Received request to update firestation for address: {}, with data: {}", address, firestation);

        try {
            Optional<Firestation> updatedFirestation = firestationService.updateFirestation(address, firestation);

            if (updatedFirestation.isPresent()) {
                logger.info("Firestation mapping updated successfully for address: {}", address);
                return ResponseEntity.ok("Firestation mapping updated successfully.");
            } else {
                logger.warn("Firestation mapping not found for address: {}", address);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error occurred while updating firestation for address: {}", address, e);
            return ResponseEntity.status(500).body("Failed to update firestation.");
        }
    }

    // Supprimer une caserne/adresse
    @DeleteMapping("/{address}")
    public ResponseEntity<String> deleteFirestation(@PathVariable String address) {
        logger.debug("Received request to delete firestation for address: {}", address);

        try {
            boolean removed = firestationService.deleteFirestation(address);

            if (removed) {
                logger.info("Firestation mapping deleted successfully for address: {}", address);
                return ResponseEntity.ok("Firestation mapping deleted successfully.");
            } else {
                logger.warn("Firestation mapping not found for address: {}", address);
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Error occurred while deleting firestation for address: {}", address, e);
            return ResponseEntity.status(500).body("Failed to delete firestation.");
        }
    }

    // Récupérer les personnes couvertes par une caserne
    @GetMapping
    public FirestationResponse getPersonsCoveredByStation(@RequestParam String stationNumber) {
        logger.debug("Received request to get persons covered by firestation with stationNumber: {}", stationNumber);

        try {
            FirestationResponse response = firestationService.getPersonsCoveredByStation(stationNumber);
            logger.info("Successfully retrieved persons covered by firestation with stationNumber: {}", stationNumber);
            return response;
        } catch (Exception e) {
            logger.error("Error occurred while retrieving persons covered by firestation with stationNumber: {}", stationNumber, e);
            return null;  // or an appropriate error response
        }
    }
}
