package com.example.dnsqueries.service;

import com.example.dnsqueries.entity.DnsResponse;
import com.example.dnsqueries.entity.Domain;
import com.example.dnsqueries.repository.DnsResponseRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.stereotype.Service;
import com.example.dnsqueries.entity.enums.RequestVersion;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.*;


@Slf4j
@Service
@AllArgsConstructor
public class DnsResponseService {

    private final DnsResponseRepository dnsResponseRepository;
    private final DnsHeadersService headersService;

    public void makeHttpsRequest(Domain domain) {
        List<String> headerList = new ArrayList<>();
        String inputUrl = RequestVersion.HTTPS_PREFIX.getValue() + domain.getDomain();

        try {
            TrustStrategy trustStrategy = TrustAllStrategy.INSTANCE;
            SSLContextBuilder sslContextBuilder = SSLContextBuilder.create().loadTrustMaterial(trustStrategy);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(500) // Socket timeout in milliseconds
                    .setConnectTimeout(500) // Connection timeout in milliseconds
                    .build();

            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSslcontext(sslContextBuilder.build())
                    .setDefaultRequestConfig(requestConfig)
                    .setSSLSocketFactory(new SSLConnectionSocketFactory(sslContextBuilder.build(), NoopHostnameVerifier.INSTANCE))
                    .build();
            HttpGet httpGet = new HttpGet(inputUrl);

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                DnsResponse responseEntity = createAndSaveResponse(response, domain, inputUrl, RequestVersion.HTTPS_PREFIX.getValue());
                manipulateHeaderList(response, headerList, RequestVersion.HTTPS_PREFIX.getValue());
                headersService.saveHeaders(responseEntity, headerList);
            } catch (ConnectException | SocketTimeoutException | ConnectTimeoutException e) {
                log.error("Error during HTTPS request: {} ", inputUrl);
                createAndSaveResponse(null, domain, inputUrl, RequestVersion.HTTPS_PREFIX.getValue());
            } catch (IOException e) {
                log.error("IO Exception: {} ",inputUrl ,e );
            }
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            log.error("Error setting up HTTPS client: {} " , inputUrl );
        }
    }
    public void makeHttpRequest(Domain domain) {
        List<String> headerList = new ArrayList<>();
        String inputUrl = RequestVersion.HTTP_PREFIX.getValue() + domain.getDomain();

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(500) // Socket timeout in milliseconds
                .setConnectTimeout(500) // Connection timeout in milliseconds
                .build();

        try (CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build()) {
            HttpGet httpGet = new HttpGet(inputUrl);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                DnsResponse responseEntity = createAndSaveResponse(response, domain, inputUrl, RequestVersion.HTTP_PREFIX.getValue());
                manipulateHeaderList(response, headerList, RequestVersion.HTTP_PREFIX.getValue());
                headersService.saveHeaders(responseEntity, headerList);
            } catch (ConnectException | ConnectTimeoutException | SocketTimeoutException e) {
                log.error("Error during HTTPS request: {} ", inputUrl);
                createAndSaveResponse(null, domain, inputUrl, RequestVersion.HTTP_PREFIX.getValue());
            }
            catch (IOException e) {
                log.error("IO EXCEPTION IN HTTP {} " , inputUrl , e);
            }
        }
        catch (IOException e) {log.error("IO Exception", e);}
    }
    private DnsResponse createAndSaveResponse(CloseableHttpResponse response, Domain domain, String inputURL, String httpVers) {
        Date currentDate = new Date();
        DnsResponse responseEntity = DnsResponse.builder()
                .status(String.valueOf(response == null ? "CONNECTION TIMED OUT" : response.getStatusLine()))
                .httpVersion(httpVers)
                .creationDate(currentDate)
                .domain(domain)
                .fullURL(inputURL)
                .build();
        if(response != null) {
            String[] parts = String.valueOf(response.getStatusLine()).split(" ");
            String tmpStatusCode = parts[1]; // "200"
            String tmpStatusMsg = parts[2];
            String status = tmpStatusCode + " " + tmpStatusMsg;
            responseEntity.setStatus(status);
            dnsResponseRepository.save(responseEntity);
        }
        dnsResponseRepository.save(responseEntity);
        return responseEntity;
    }
    private void manipulateHeaderList(CloseableHttpResponse response, List<String> headerList, String version) {
        Header[] headers = response.getAllHeaders();
        for (Header header : headers) {headerList.add(String.valueOf(header));}
        String[] parts = String.valueOf(response.getStatusLine()).split(" ");
        String statusCode = parts[1]; // "200"
        String statusMessage = parts[2]; // "OK"
        String status = statusCode + " " + statusMessage;
        headerList.add("Status Line: " + status);
        headerList.add("Procotol Version: " + version);
    }

    public List<DnsResponse> getAllOks() {
       List<DnsResponse> dnsResponseList = dnsResponseRepository.getAllOks().orElseThrow();
        System.out.println("DNSRESPSERVICE " + dnsResponseList);
        return dnsResponseList;
    }
}
