package com.safetyNet.safetyNetSystem.controller;

import com.safetyNet.safetyNetSystem.model.MedicalRecord;
import com.safetyNet.safetyNetSystem.service.MedicalRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    // Ajouter un dossier médical
    @PostMapping
    public ResponseEntity<String> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        medicalRecordService.addMedicalRecord(medicalRecord);
        return ResponseEntity.ok("Medical record added successfully.");
    }

    // Mettre à jour un dossier médical existant
    @PutMapping("/{firstName}/{lastName}")
    public ResponseEntity<String> updateMedicalRecord(
            @PathVariable String firstName,
            @PathVariable String lastName,
            @RequestBody MedicalRecord updatedRecord) {
        Optional<MedicalRecord> updated = medicalRecordService.updateMedicalRecord(firstName, lastName, updatedRecord);

        if (updated.isPresent()) {
            return ResponseEntity.ok("Medical record updated successfully.");
        } else {
            return ResponseEntity.notFound().build(); // Dossier médical non trouvé
        }
    }

    // Supprimer un dossier médical
    @DeleteMapping("/{firstName}/{lastName}")
    public ResponseEntity<String> deleteMedicalRecord(
            @PathVariable String firstName,
            @PathVariable String lastName) {
        boolean removed = medicalRecordService.deleteMedicalRecord(firstName, lastName);

        if (removed) {
            return ResponseEntity.ok("Medical record deleted successfully.");
        } else {
            return ResponseEntity.notFound().build(); // Dossier médical non trouvé
        }
    }
}
