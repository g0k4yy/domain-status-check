package com.example.dnsqueries.repository;

import com.example.dnsqueries.entity.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DomainRepository extends JpaRepository<Domain,Long> {
    @Query("SELECT dom FROM Domain dom WHERE dom.domain = ?1 ")
    Optional<Domain> findByDomain(String inputURL);




}
