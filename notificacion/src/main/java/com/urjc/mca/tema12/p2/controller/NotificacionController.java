package com.urjc.mca.tema12.p2.controller;

import com.urjc.mca.tema12.p2.model.Notification;
import com.urjc.mca.tema12.p2.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/notification")
public class NotificacionController {

    @Autowired
    OrderService orderService;


    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificaction(@PathVariable long id) {
        Notification notification = this.orderService.getNotification(id);
        if (notification == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(notification, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Long> newNotification(@RequestBody Map<String, String> notification) {

        try {
            Notification newNotification = this.orderService.save(notification);
            return new ResponseEntity<>(newNotification.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
    }
}
