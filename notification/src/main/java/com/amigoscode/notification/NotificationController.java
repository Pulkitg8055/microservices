package com.amigoscode.notification;

import com.amigoscode.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("api/v1/notification")
    public void sendNotification(NotificationRequest notificationRequest) {
        log.info("New notification... {}", notificationRequest.toCustomerId());
        notificationService.send(notificationRequest);
    }

}
