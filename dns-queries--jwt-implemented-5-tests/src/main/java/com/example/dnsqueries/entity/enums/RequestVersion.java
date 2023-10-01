package com.example.dnsqueries.entity.enums;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RequestVersion {
    HTTP_PREFIX("http://"),
    HTTPS_PREFIX("https://");

    private final String value;
}
