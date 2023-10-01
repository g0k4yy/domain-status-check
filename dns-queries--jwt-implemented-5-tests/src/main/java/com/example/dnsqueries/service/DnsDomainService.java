package com.example.dnsqueries.service;


import com.example.dnsqueries.dto.DomainDto;
import com.example.dnsqueries.entity.DnsResponse;
import com.example.dnsqueries.entity.Domain;
import com.example.dnsqueries.exception.exceptions.CreationError;
import com.example.dnsqueries.repository.DomainRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@AllArgsConstructor
public class DnsDomainService {

    private final DomainRepository domainRepository;
    private final DnsResponseService responseService;
    @PostConstruct
    public void init() {

        createAndSaveDomainEntity(DomainDto.builder().url("www.yurticikargo.com").build());
        createAndSaveDomainEntity(DomainDto.builder().url("www.arikanliholding.com").build());
        createAndSaveDomainEntity(DomainDto.builder().url("www.itu.edu.tr").build());
        createAndSaveDomainEntity(DomainDto.builder().url("www.enmersan.com.tr").build());
        createAndSaveDomainEntity(DomainDto.builder().url("yurti.ci").build());
        createAndSaveDomainEntity(DomainDto.builder().url("www.slr.com.tr").build());
        createAndSaveDomainEntity(DomainDto.builder().url("httpbin.org/status/500").build());
    }

    public Domain createAndSaveDomainEntity(DomainDto dto) {
        String inputURL = dto.getUrl();
        Date currentDate = new Date();
        try {
            Domain newDomain = Domain.builder().domain(inputURL).date(currentDate).build();
            domainRepository.save(newDomain);
            return newDomain;
        }
        catch(Exception e){
            throw new CreationError("Something went wrong!",e);
        }
    }

    public void makeRequest(DomainDto dto) {
        Domain myDomain = domainRepository.findByDomain(dto.getUrl()).orElseThrow();
        responseService.makeHttpRequest(myDomain);
        responseService.makeHttpsRequest(myDomain);
    }

    public void makeRequestForAll() {
        List<Domain> domains = domainRepository.findAll();
        try (ExecutorService executorService = Executors.newCachedThreadPool()) {

            for (Domain domain : domains) {
                executorService.submit(() -> {
                    responseService.makeHttpsRequest(domain);
                    responseService.makeHttpRequest(domain);
                });
            }
            executorService.shutdown();
        }
    }

    public List<DnsResponse> getAllOks() {
        List<DnsResponse> dnsResponseList = responseService.getAllOks();
        System.out.println("DNSDOMAINSERVICE " + dnsResponseList);
        return dnsResponseList;
    }
}

// feature callable
// feature future

/*
* #'appdownload.yurticikargo.com',
#'akademi.yurticikargo.com',
#'vpnank1.yurticikargo.com',
#'www.yurticikargo.com',
#'ykwebprod.yurticikargo.com',
#'admmobileapp.yurticikargo.com',
#'admwebservices.yurticikargo.com',
#'sslvpn.yurticikargo.com',
#'vpnist1.yurticikargo.com',
#'beta.yurticikargo.com',
#'selfservis.yurticikargo.com',
#'ykadfs.yurticikargo.com',
#'restservices.yurticikargo.com',
#'ykgameapi.yurticikargo.com',
#'courierappapi.yurticikargo.com',
#'ykdpdapi.yurticikargo.com',
#'www.enmersan.com.tr',
#'www.slr.com.tr',
#'yurti.ci',
#'www.yurticiinsaat.com.tr',
#'btkapitest.yurticikargo.com',
#'geogateway.yurticikargo.com',
#'geogatewaytest.yurticikargo.com',
#'geogatewayv2.yurticikargo.com',
#'gisapitest.yurticikargo.com',
#'ideliverytest.yurticikargo.com',
#'livetracking.yurticikargo.com',
#'lojisofterpgwtest.yurticikargo.com',
#'mdatest.yurticikargo.com',
#'ngialiexpressgwtest.yurticikargo.com',
#'ortakposgwtest.yurticikargo.com',
#'ykdpdapitest.yurticikargo.com',
#'ykpluscustapitest.yurticikargo.com',
#'esatinalmatest.yurticikargo.com.tr',
#'erpapimwtest.yurticikargo.com',
#'erpapitest.yurticikargo.com',
#'hrempsurveytest.yurticikargo.com',
#'isbasvurutest.yurticikargo.com',
#'perntfformtest.yurticikargo.com',
#'customerappapi.yurticikargo.com',
#'restnew.yurticikargo.com',
#'ykplusappapi.yurticikargo.com',
#'ykteamappapi.yurticikargo.com',
#'ykwebapi.yurticikargo.com',
#'webservices.yurticikargo.com',
#'mapp.yurticikargo.com',
#'ykwebtest.yurticikargo.com',
#'akademitest.yurticikargo.com',
#'authtest.yurticikargo.com',
#'trainingapitest.yurticikargo.com',
#'trainingauthtest.yurticikargo.com',
#'enote.yurticikargo.com',
#'testenote.yurticikargo.com',
#'ws.yurticikargo.com',
#'imap.ykacente.com',
#'mailsrv01.ykacente.com',
#'imap.yksube.com',
#'mailsrv01.yksube.com',
'imap.yk-tmi.com',
#'mailsrv01.yk-tmi.com',
'www.ibrahimarikan.com',
#'yk2b.yurticikargo.com',
#'www.arikanliholding.com',
#'erpdogumgunu.yurticikargo.com.tr',
#'go.yurticikargo.com',
#'pilotrestservices.yurticikargo.com',
'dvr.yurticikargo.com',
#'ortakposgw.yurticikargo.com',
#'egitimpilot.yurticikargo.com',
#'erpapi.yurticikargo.com',
#'erpapimw.yurticikargo.com',
#'isbasvuru.yurticikargo.com',
#'trainingapi.yurticikargo.com',
#'trainingauth.yurticikargo.com',
#'geoserver.yurticikargo.com',
#'testrestservicesnew.yurticikargo.com',
#'hepsiburadaapi.yurticikargo.com',
#'idelivery.yurticikargo.com',
#'kontrolsen.de',
#'anket.yurticikargo.com',
#'egitim.yurticikargo.com',
#'harita.yurticikargo.com',
#'hrempsurvey.yurticikargo.com',
#'mobilehrcms.yurticikargo.com',
#'mobilehrcmsadmin.yurticikargo.com',
#'mobilehrcmstest.yurticikargo.com',
#'mutabakat.yurticikargo.com',
#'perntfform.yurticikargo.com',
#'store.yurticikargo.com',
#'vehicleratingapi.yurticikargo.com',
#'testrestservices.yurticikargo.com',
#'efa.yurticikargo.com',
#'ngialiexpressgw.yurticikargo.com',
#'ykshare.yurticikargo.com',
#'vpn.yurticikargo.com',
#'admreports.yurticikargo.com',
#'reports.yurticikargo.com',
#'bilgiguvenligi.yurticikargo.com',
#'testwebservices.yurticikargo.com',
#'testws.yurticikargo.com',
#'sslwebservices.yurticikargo.com',
#'sms.yurticikargo.com',
#'mapptest.yurticikargo.com',
#'btkapi.yurticikargo.com',
#'mail1.yurticikargo.com.tr',
#'mailgateway.yurticikargo.com.tr',
#'betabireysel.yurticikargo.com',
#'geocode.yurticikargo.com',
#'geodata.yurticikargo.com',
#'testselfservis.yurticikargo.com',
#'mda.yurticikargo.com',
#'zimmettakip.yurticikargo.com',
#'bireysel.yurticikargo.com',
#'ykpluscustapi.yurticikargo.com',
#'ftp.yurticikargo.com',
#'ykviewmob.yurticikargo.com',
#'mmnws.yurticikargo.com',
#'autodiscover.yurticikargo.com'

*
* */


