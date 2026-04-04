package com.airline.backend.repositories;

import com.airline.backend.models.entities.AuditEventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditEventTypeRepository extends JpaRepository<AuditEventType, Integer> {
}
