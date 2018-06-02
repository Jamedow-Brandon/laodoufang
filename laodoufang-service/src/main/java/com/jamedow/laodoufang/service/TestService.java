package com.jamedow.laodoufang.service;

import com.jamedow.laodoufang.config.ConsumerMethods;
import com.jamedow.laodoufang.config.ProducerMethods;
import com.jamedow.laodoufang.event.DeliveryEvent;
import com.jamedow.laodoufang.event.SolicitationEvent;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @ProducerMethods(SolicitationEvent.class)
    public void solicitation(Class a) {
        System.out.println("揽件开始");
        System.out.println("揽件成功！！");
    }

    @ConsumerMethods(SolicitationEvent.class)
    @ProducerMethods(DeliveryEvent.class)
    public void delivery() {
        System.out.println("派件开始");
        System.out.println("派件成功！！");
    }
}
