package com.charger.charger_management.scheduler;

import com.charger.charger_management.models.Charger;
import com.charger.charger_management.repository.ChargerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ChargerStatusScheduler {

    @Autowired
    private ChargerRepository chargerRepository;

    @Scheduled(fixedRate = 60000) // Run every 60 seconds
    public void checkAndMarkUnavailable() {
        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);
        List<Charger> chargers = chargerRepository.findAll();

        for (Charger charger : chargers) {
            if (charger.getLastHeartbeat() != null && charger.getLastHeartbeat().isBefore(fiveMinutesAgo)) {
                charger.setStatus("Unavailable");
                chargerRepository.save(charger);
            }
        }
    }
}
