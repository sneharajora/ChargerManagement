package com.charger.charger_management.service;

import com.charger.charger_management.models.Charger;
import com.charger.charger_management.repository.ChargerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class ChargerService {

    @Autowired
    private ChargerRepository chargerRepository;

    //  Get all chargers
    public List<Charger> getAllChargers() {
        return chargerRepository.findAll();
    }

    //  Get charger by ID
    public Optional<Charger> getChargerById(String id) {
        return chargerRepository.findById(id);
    }

    //Add a new charger
    public Charger addCharger(Charger charger) {
        return chargerRepository.save(charger);
    }

    // Update charger
    public Charger updateCharger(String id, Charger chargerDetails) {
        Optional<Charger> chargerOptional = chargerRepository.findById(id);
        if (chargerOptional.isPresent()) {
            Charger charger = chargerOptional.get();
            charger.setName(chargerDetails.getName());
            charger.setLocation(chargerDetails.getLocation());
            charger.setStatus(chargerDetails.getStatus());
            return chargerRepository.save(charger);
        }
        return null;
    }

    //  Delete a charger
    public void deleteCharger(String id) {
        chargerRepository.deleteById(id);
    }

    //  Update charger status (when receiving OCPP messages)
    public void updateChargerStatus(String chargerId, String status) {
        Optional<Charger> chargerOptional = chargerRepository.findById(chargerId);
        if (chargerOptional.isPresent()) {
            Charger charger = chargerOptional.get();
            charger.setStatus(status);
            chargerRepository.save(charger);
        }
    }

    //Track heartbeat (to check if charger is online)
    public void updateLastHeartbeat(String chargerId) {
        Optional<Charger> chargerOptional = chargerRepository.findById(chargerId);
        if (chargerOptional.isPresent()) {
            Charger charger = chargerOptional.get();
            charger.setLastHeartbeat(LocalDateTime.now());  // Save current time as last heartbeat
            chargerRepository.save(charger);
        }
    }
}
