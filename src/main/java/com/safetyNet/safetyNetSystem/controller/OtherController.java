package com.safetyNet.safetyNetSystem.controller;

import com.safetyNet.safetyNetSystem.service.FirestationService;
import com.safetyNet.safetyNetSystem.dto.FirestationResponseNoCount;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")  // L'URL de base pour toutes les routes du contrôleur
public class OtherController {

    private final FirestationService firestationService;

    public OtherController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    // Récupérer les numéros de téléphone des personnes couvertes par une caserne
    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getPhoneNumbersByStation(@RequestParam(name = "firestation") String firestationNumber) {
        List<String> phoneNumbers = firestationService.getPhoneNumbersByStation(firestationNumber);
        return ResponseEntity.ok(phoneNumbers);
    }

    // Route pour récupérer les informations des habitants et de la caserne à partir de l'adresse
    @GetMapping("/fire")
    public FirestationResponseNoCount getFirestationInfoByAddress(@RequestParam String address) {
        return firestationService.getFirestationInfoByAddress(address);
    }

    // Nouvelle route pour récupérer les foyers desservis par plusieurs casernes
    @GetMapping("/flood/stations")
    public List<FirestationResponseNoCount> getFloodedStations(@RequestParam List<String> stations) {
        // Appel du service pour récupérer les stations inondées en fonction des stations spécifiées
        return firestationService.getFloodedStations(stations);
    }
}
