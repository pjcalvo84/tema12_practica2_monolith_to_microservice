package com.urjc.mca.tema12.p2.service;

import com.urjc.mca.tema12.p2.model.Notification;
import com.urjc.mca.tema12.p2.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Transactional
    public Notification save(Map<String, String> notification) {
        Notification newNotification = new Notification(-1l, Long.parseLong(notification.get("customer")),
                Integer.parseInt(notification.get("quantity")), new Date());
        return notificationRepository.save(newNotification);
    }


    public Notification getNotification(long id) {
        return notificationRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
