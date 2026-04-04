package com.airline.backend.repositories;

import com.airline.backend.models.entities.AuditEventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuditEventTypeRepository extends JpaRepository<AuditEventType, Integer> {

    Optional<AuditEventType> findByEventName(String eventName);
}
