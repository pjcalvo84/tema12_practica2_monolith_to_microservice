package com.urjc.mca.tema12.p2.repository;

import com.urjc.mca.tema12.p2.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificationRepository extends JpaRepository<Notification, Long> {

}