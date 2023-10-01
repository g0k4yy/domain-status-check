package com.example.dnsqueries.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "domain")
@Entity
public class Domain {
    @GeneratedValue
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "domain", unique = true)
    private String domain;

    @Column (name="date")
    private Date date;
}
