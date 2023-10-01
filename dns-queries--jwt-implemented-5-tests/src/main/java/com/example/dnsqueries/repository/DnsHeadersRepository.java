package com.example.dnsqueries.repository;

import com.example.dnsqueries.entity.DnsHeaders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DnsHeadersRepository extends JpaRepository<DnsHeaders,Long> {
    @Query("SELECT dh FROM DnsHeaders dh WHERE dh.dnsResponse.creationDate IN (SELECT (dr.creationDate) FROM DnsResponse dr WHERE dr.domain.domain = ?1) AND dh.dnsResponse.httpVersion = 'https://'")
    Optional<List<DnsHeaders>> findHttpsHeadersByDomain(String inputDomain);

    @Query("SELECT dh FROM DnsHeaders dh WHERE dh.dnsResponse.creationDate IN (SELECT (dr.creationDate) FROM DnsResponse dr WHERE dr.domain.domain = ?1) AND dh.dnsResponse.httpVersion = 'http://'")
    Optional<List<DnsHeaders>> findHttpHeadersByDomain(String inputDomain);

}
