package com.airline.backend.repositories;

import com.airline.backend.models.entities.InternalEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InternalEmployeeRepository extends JpaRepository<InternalEmployee, Integer> {

    Optional<InternalEmployee> findByEmail(String email);

    Optional<InternalEmployee> findByDocumentTypeAndDocumentNumber(String documentType, String documentNumber);
}
