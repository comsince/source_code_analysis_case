package com.comsince.github;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-8 下午3:01
 **/
public class SpringMain {
    private static final int EMBED_ZOOKEEPER_PORT = 5181;

    // CHECKSTYLE:OFF
    public static void main(final String[] args) {
        // CHECKSTYLE:ON
        EmbedZookeeperServer.start(EMBED_ZOOKEEPER_PORT);
        new ClassPathXmlApplicationContext("classpath:application-elastic-job.xml");
    }
}
