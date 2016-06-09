package com.kingzoo.kingcat.project.istockui.notification.service;

/**
 * 发送通知服务
 * Created by gonghongrui on 16/3/29.
 */
public interface NotificationService {

    void sendToAll(String title, String message);
    void sendToOne(String alian, String title, String message);
}
