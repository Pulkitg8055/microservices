package com.microservices.clients.fraud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@FeignClient(
        name = "fraud",
        url = "${clients.fraud.url}"
)
public interface FraudClient {
    @GetMapping("api/v1/fraud-check/{customerId}")
    public FraudCheckResponse isFraudster(
            @PathVariable("customerId") Integer customerId);
}
