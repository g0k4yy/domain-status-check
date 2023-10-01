package com.example.dnsqueries.repository;

import com.example.dnsqueries.entity.DnsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DnsResponseRepository  extends JpaRepository<DnsResponse,Long> {
    @Query("SELECT dr FROM DnsResponse dr WHERE dr.status = '200 OK'")
    Optional<List<DnsResponse>> getAllOks();
}
