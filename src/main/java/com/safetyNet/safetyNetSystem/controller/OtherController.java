package com.safetyNet.safetyNetSystem.controller;

import com.safetyNet.safetyNetSystem.service.FirestationService;
import com.safetyNet.safetyNetSystem.dto.FirestationResponseNoCount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")  // L'URL de base pour toutes les routes du contrôleur
public class OtherController {

    private static final Logger logger = LogManager.getLogger(OtherController.class);

    private final FirestationService firestationService;

    public OtherController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    // Récupérer les numéros de téléphone des personnes couvertes par une caserne
    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getPhoneNumbersByStation(@RequestParam(name = "firestation") String firestationNumber) {
        logger.debug("Received request to get phone numbers for firestation: {}", firestationNumber);

        try {
            List<String> phoneNumbers = firestationService.getPhoneNumbersByStation(firestationNumber);
            logger.info("Successfully retrieved phone numbers for firestation: {}", firestationNumber);
            return ResponseEntity.ok(phoneNumbers);
        } catch (Exception e) {
            logger.error("Error occurred while retrieving phone numbers for firestation: {}", firestationNumber, e);
            return ResponseEntity.status(500).body(null);
        }
    }

    // Route pour récupérer les informations des habitants et de la caserne à partir de l'adresse
    @GetMapping("/fire")
    public FirestationResponseNoCount getFirestationInfoByAddress(@RequestParam String address) {
        logger.debug("Received request to get firestation info for address: {}", address);

        try {
            FirestationResponseNoCount response = firestationService.getFirestationInfoByAddress(address);
            logger.info("Successfully retrieved firestation info for address: {}", address);
            return response;
        } catch (Exception e) {
            logger.error("Error occurred while retrieving firestation info for address: {}", address, e);
            return null;  // Vous pouvez aussi retourner une réponse personnalisée en cas d'erreur
        }
    }

    // Nouvelle route pour récupérer les foyers desservis par plusieurs casernes
    @GetMapping("/flood/stations")
    public List<FirestationResponseNoCount> getFloodedStations(@RequestParam List<String> stations) {
        logger.debug("Received request to get flooded stations for stations: {}", stations);

        try {
            List<FirestationResponseNoCount> floodedStations = firestationService.getFloodedStations(stations);
            logger.info("Successfully retrieved flooded stations for stations: {}", stations);
            return floodedStations;
        } catch (Exception e) {
            logger.error("Error occurred while retrieving flooded stations for stations: {}", stations, e);
            return null;  // Vous pouvez aussi retourner une réponse personnalisée en cas d'erreur
        }
    }
}
