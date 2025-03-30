package com.charger.charger_management.controller;

import com.charger.charger_management.models.Charger;
import com.charger.charger_management.service.ChargerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chargers") 
public class ChargerController {

    @Autowired
    private ChargerService chargerService;

    // Get all chargers
    @GetMapping
    public List<Charger> getAllChargers() {
        return chargerService.getAllChargers();
    }

    // Get a single charger by ID
    @GetMapping("/{id}")
    public Optional<Charger> getChargerById(@PathVariable String id) {
        return chargerService.getChargerById(id);
    }

    // Add a new charger
    @PostMapping
    public Charger addCharger(@RequestBody Charger charger) {
        return chargerService.addCharger(charger);
    }

    // Update an existing charger
    @PutMapping("/{id}")
    public Charger updateCharger(@PathVariable String id, @RequestBody Charger charger) {
        return chargerService.updateCharger(id, charger);
    }

    // Delete a charger
    @DeleteMapping("/{id}")
    public String deleteCharger(@PathVariable String id) {
        chargerService.deleteCharger(id);
        return "Charger with ID " + id + " deleted successfully.";
    }
}
