package com.kingzoo.kingcat.project.istock.notification.jpush;

import com.kingzoo.kingcat.project.istock.notification.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by gonghongrui on 16/4/6.
 */
@Service
public class JPushNotificationService implements NotificationService {
    protected static final Logger LOGGER = LoggerFactory.getLogger(JPushNotificationService.class);
    @Override
    public void sendToAll(String title, String message) {
        LOGGER.error("method not implement");
    }

    @Override
    public void sendToOne(String alian, String title, String message) {
        JPushUtils.sendIosAlert(alian, title, message);
    }


}
