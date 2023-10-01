package com.example.dnsqueries.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "dns_headers")
public class DnsHeaders {
    @Id
    @GeneratedValue
    @Column(name ="id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "response_id")
    private DnsResponse dnsResponse;

    @Column (name = "key_header",length = 1024)
    private String keyHeader;

    @Column (name = "key_value",length = 1024)
    private String valueHeader;

    @Column(name = "create_date",length = 1024)
    private Date createdDate;
}
