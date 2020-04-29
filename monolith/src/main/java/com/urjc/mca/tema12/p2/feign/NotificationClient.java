package com.urjc.mca.tema12.p2.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(value = "notification", url = "${notification.host}")
public interface NotificationClient {


    @RequestMapping(method = RequestMethod.POST, value = "/notification/")
    Long sendNotification(Map<String, String> notification);


}
