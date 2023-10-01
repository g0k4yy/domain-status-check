package com.example.dnsqueries.entity;

import com.example.dnsqueries.entity.enums.RequestVersion;
import jakarta.persistence.*;
import lombok.*;
import org.apache.coyote.Request;

import java.util.Date;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "dns_response")
@Entity
public class DnsResponse {
    @Id
    @GeneratedValue
    @Column(name="id")
    private long id;

    @Column(name = "creation_date")
    private Date creationDate;

    @ManyToOne
    @JoinColumn(name = "domain_id")
    private Domain domain;

    @Column(name = "http_version")
    private String httpVersion;

    @Column (name = "status")
    private  String status;

    @Column (name = "full_url")
    private String fullURL;

//date
}
