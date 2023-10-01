//package com.example.dnsqueries.controller;
//
//import com.example.dnsqueries.service.DnsDomainService;
//import io.swagger.v3.oas.annotations.Hidden;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/demo-controller")
//public class DemoController {
//
//    private final DnsDomainService domainService;
//    @GetMapping
//    public ResponseEntity<String> sayHello() {
//        return ResponseEntity.ok("Hello from secured endpoint");
//    }
//
//    @GetMapping("/make-request-for-all")
//    public ResponseEntity<Boolean> makeRequestForAll() {
//        domainService.makeRequestForAll();
//        boolean returnable = true;
//        return ResponseEntity.ok(returnable);
//    }
//
//
//
////    @GetMapping("/sonradan-eklenen-reuqest")
////    public ResponseEntity<Boolean> sayBuyaka() {
////        domainService.makeRequestForAll();
////        boolean returnable = true;
////        return ResponseEntity.ok(returnable);
////    }
//}
