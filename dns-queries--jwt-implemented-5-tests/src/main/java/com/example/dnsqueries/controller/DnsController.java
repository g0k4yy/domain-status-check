package com.example.dnsqueries.controller;
import com.example.dnsqueries.dto.DomainDto;
import com.example.dnsqueries.entity.DnsResponse;
import com.example.dnsqueries.service.DnsDomainService;
import com.example.dnsqueries.service.DnsHeadersService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/dns-project")
public class DnsController {
    private final DnsDomainService domainService;
    private final DnsHeadersService headersService;

    @GetMapping("/make-request")
    public ResponseEntity<Map<String, Map<String, String>>> makeRequest(DomainDto dto) {
        domainService.makeRequest(dto);
        Map<String, Map<String, String>> resultList = headersService.getHeaders(dto);
        return ResponseEntity.ok(resultList);
    }
    @GetMapping("/get-last-status")
    public ResponseEntity<Map<String, Map<String, String>>> getLastStatus (DomainDto dto) {
        Map<String, Map<String, String>> resultList = headersService.getHeaders(dto);
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/make-request-for-all")
    public ResponseEntity<Boolean> makeRequestForAll() {
        try{
            domainService.makeRequestForAll();
        }
        catch (Exception ex){
            log.error("Exception is thrown {} " , ex);
        }
        boolean returnable = true;
        return ResponseEntity.ok(returnable);
    }
    @PostMapping("/add-domain")
    public ResponseEntity<Boolean> addDomain(DomainDto dto) {
        domainService.createAndSaveDomainEntity(dto);
        boolean returnable = true;
        return ResponseEntity.ok(returnable);
    }
    @GetMapping("/get-all-oks")
    public ResponseEntity<List<DnsResponse>> getallOks() {
        List<DnsResponse> dnsResponseList = domainService.getAllOks();
        System.out.println("DNSCONTROLLER " + dnsResponseList);
        return ResponseEntity.ok(dnsResponseList);
    }
}
