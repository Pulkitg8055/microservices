package com.microservices.customer;

import com.microservices.clients.fraud.FraudCheckResponse;
import com.microservices.clients.fraud.FraudClient;
import com.microservices.clients.notification.NotificationClient;
import com.microservices.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record CustomerService(
        CustomerRepository customerRepository,
        RestTemplate restTemplate,
        FraudClient fraudClient,
        NotificationClient notificationClient) {
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        //todo: check if email valid
        //todo: check if email not taken
        customerRepository.saveAndFlush(customer);

//        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject("http://FRAUD/api/v1/fraud-check/{customerId}",
//                FraudCheckResponse.class, customer.getId());

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
        if(fraudCheckResponse.isFraudster())
            throw new IllegalStateException("fraudster");

        notificationClient.sendNotification(
                new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),
                        String.format("Hi %s, welcome to Amigoscode...",
                                customer.getFirstName())
                )
        );

    }
}
