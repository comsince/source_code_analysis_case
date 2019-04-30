package com.comsince.github;

import com.comsince.github.api.GreetingsService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-29 下午3:54
 **/
public class Client {
    public static void main(String[] args) {
        ReferenceConfig<GreetingsService> referenceConfig = new ReferenceConfig<GreetingsService>();
        referenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        referenceConfig.setRegistry(new RegistryConfig("multicast://224.5.6.7:1234"));
        referenceConfig.setInterface(GreetingsService.class);
        GreetingsService greetingService = referenceConfig.get();
        System.out.println(greetingService.sayHi("world"));
    }
}
