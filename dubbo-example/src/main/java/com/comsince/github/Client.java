package com.comsince.github;

import com.comsince.github.api.GreetingsService;
import com.comsince.github.extension.InjectExt;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.remoting.exchange.Exchanger;
import org.apache.dubbo.rpc.Protocol;


/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-29 下午3:54
 **/
public class Client {
    public static void main(String[] args) {
        PushService pushService = pushService();
        for (int i=0;i<1;i++){
            pushService.pushAll("push from client "+i);
        }
    }

    public static void extensionTest(){
        InjectExt injectExt = ExtensionLoader.getExtensionLoader(InjectExt.class).getExtension("injection");
        System.out.println(injectExt.echo("hello"));
        InjectExt injectExt1 = ExtensionLoader.getExtensionLoader(InjectExt.class).getExtension("injection1");
        System.out.println(injectExt1.echo("hello1"));
    }

    public static void sayHi(){
        ReferenceConfig<GreetingsService> referenceConfig = new ReferenceConfig<GreetingsService>();
        referenceConfig.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://172.16.46.201:2181"));
        referenceConfig.setInterface(GreetingsService.class);
        GreetingsService greetingService = referenceConfig.get();
        System.out.println(greetingService.sayHi("world"));
    }


    public static void testExtendSion(){
        Exchanger exchanger = ExtensionLoader.getExtensionLoader(Exchanger.class).getExtension("header");
        System.out.println(exchanger);
    }


    public static void inject(){
       Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
       System.out.println(protocol);

      // ExtensionLoader.getExtensionLoader(Transporter.class).getAdaptiveExtension();
    }

    public static PushService pushService(){
        ReferenceConfig<PushService> referenceConfig = new ReferenceConfig<PushService>();
        referenceConfig.setApplication(new ApplicationConfig("push-dubbo-consumer"));
        referenceConfig.setRegistry(new RegistryConfig("zookeeper://zk-test-master1.meizu.mz:2181"));
        referenceConfig.setInterface(PushService.class);
        PushService pushService = referenceConfig.get();
        return pushService;
    }
}
