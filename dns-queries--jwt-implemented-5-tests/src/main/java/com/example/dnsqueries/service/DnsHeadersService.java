package com.example.dnsqueries.service;

import com.example.dnsqueries.dto.DomainDto;
import com.example.dnsqueries.entity.DnsHeaders;
import com.example.dnsqueries.entity.DnsResponse;
import com.example.dnsqueries.repository.DnsHeadersRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j

public class DnsHeadersService {

    private final DnsHeadersRepository headersRepository;
    public void saveHeaders(DnsResponse inputResponse, List<String> headerList) {
        Date currentDate = new Date();
        for (String header : headerList) {
            String[] parts = header.split(":");
            String key = parts[0].trim();
            String value = parts[1].trim();
            DnsHeaders dnsHeaderEntity = DnsHeaders.builder().dnsResponse(inputResponse)
                    .keyHeader(key)
                    .valueHeader(value)
                    .createdDate(currentDate)
                    .build();
            headersRepository.save(dnsHeaderEntity);
        }
    }
    public Map<String, Map<String, String>> getHeaders(DomainDto dto) {
        List<DnsHeaders> httpsHeadersList = headersRepository.findHttpsHeadersByDomain(dto.getUrl()).orElseThrow();
        List<DnsHeaders> httpHeadersList = headersRepository.findHttpHeadersByDomain(dto.getUrl()).orElseThrow();

        Map<String, String> httpsHeaders = httpsHeadersList.stream()
                .collect(Collectors.toMap(
                        DnsHeaders::getKeyHeader,   // Key mapper function
                        DnsHeaders::getValueHeader // Value mapper function
                ));

        Map <String,String> httpHeaders =httpHeadersList.stream().collect(Collectors.toMap(
                DnsHeaders::getKeyHeader,
                DnsHeaders::getValueHeader
        ));

        Map<String, Map<String, String>> result = new HashMap<>();
        result.put("httpHeaders", httpHeaders);
        result.put("httpsHeaders", httpsHeaders);

        return result;
    }
}

