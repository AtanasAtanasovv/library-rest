package com.example.libraryrest.repositories;

import com.example.libraryrest.models.DeactivationReason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeactivationReasonDAO extends JpaRepository<DeactivationReason,Integer> {
    DeactivationReason findByName(String name);
}
