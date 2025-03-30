package com.charger.charger_management.repository;

import com.charger.charger_management.models.Charger;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargerRepository extends MongoRepository<Charger, String> {
}
