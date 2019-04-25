package com.comsince.github;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-24 下午4:40
 **/
public class MyInboundHandler extends ChannelInboundHandlerAdapter{

    Logger logger = LoggerFactory.getLogger(MyInboundHandler.class);

    Client client;

    public MyInboundHandler(){
    }

    public MyInboundHandler(Client client) {
        this();
        this.client = client;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("TCP Connected");
        if(client != null){
            client.scheduleHeartBeat();
        }
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("TCP DisConnected");
        if(client != null){
            client.stop();
            client.scheduledExecutorService.schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        client.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },10, TimeUnit.SECONDS);
        }
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.info("exception",cause);
    }
}
