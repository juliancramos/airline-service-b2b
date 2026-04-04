package com.airline.backend.models.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "audit_event_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditEventType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_type_id")
    private Integer eventTypeId;

    @Column(name = "event_name", length = 100, nullable = false)
    private String eventName;
}
