package com.example.magasin.dao;

import com.example.magasin.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationDao extends JpaRepository<Notification, Integer> {
}
