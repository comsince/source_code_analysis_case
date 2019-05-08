package com.comsince.github;

import com.comsince.github.api.GreetingsService;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;

import java.io.IOException;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-29 下午3:56
 **/
public class Server {
    public static void main(String[] args) throws IOException {
        ServiceConfig<GreetingsService> serviceConfig = new ServiceConfig<GreetingsService>();
        serviceConfig.setApplication(new ApplicationConfig("first-dubbo-provider"));
        serviceConfig.setRegistry(new RegistryConfig("zookeeper://172.16.46.201:2181"));
        serviceConfig.setInterface(GreetingsService.class);
        serviceConfig.setRef(new GreetingsServiceImpl());
        serviceConfig.export();
        System.in.read();
    }
}
