package com.charger.charger_management.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;


@Document(collection = "chargers") // Use @Document instead of @Entity for MongoDB
public class Charger {

    @Id
    private String id;  // Change from Long to String

    private String name;
    private String location;
    private String status;

    public Charger() {
    }

    public Charger(String name, String location, String status) {
        this.name = name;
        this.location = location;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

     private LocalDateTime lastHeartbeat;

    public void setLastHeartbeat(LocalDateTime lastHeartbeat) {
        this.lastHeartbeat = lastHeartbeat;
    }

    public LocalDateTime getLastHeartbeat() {
        return lastHeartbeat;
    }
}
