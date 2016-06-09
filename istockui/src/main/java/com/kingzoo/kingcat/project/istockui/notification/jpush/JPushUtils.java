package com.kingzoo.kingcat.project.istockui.notification.jpush;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.notification.IosAlert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Created by gonghongrui on 16/4/6.
 */
public class JPushUtils {

    protected static final Logger LOGGER = LoggerFactory.getLogger(JPushUtils.class);

    private static final String appKey ="652deedaeffc5b1b66cba3c5";

    private static final String masterSecret = "2fa6f4e7fd8a36ae402a8f4c";

    public static void sendIosAlert(String alias, String title, String message ) {

        JPushClient jpushClient = new JPushClient(masterSecret, appKey);

        IosAlert alert = IosAlert.newBuilder()
                .setTitleAndBody(title, message)
                .setActionLocKey("PLAY")
                .build();

        HashMap<String, String> extra =  new HashMap();

        try {
            PushResult result = jpushClient.sendIosNotificationWithAlias(alert, extra, alias);
            LOGGER.info("Got result - " + result);
        } catch (APIConnectionException e) {
            LOGGER.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            LOGGER.error("Error response from JPush server. Should review and fix it. ", e);
            LOGGER.info("HTTP Status: " + e.getStatus());
            LOGGER.info("Error Code: " + e.getErrorCode());
            LOGGER.info("Error Message: " + e.getErrorMessage());
        }
    }
}