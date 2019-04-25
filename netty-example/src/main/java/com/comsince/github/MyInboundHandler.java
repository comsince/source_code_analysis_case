package com.comsince.github;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-24 下午4:40
 **/
public class MyInboundHandler extends ChannelInboundHandlerAdapter{

    Logger logger = LoggerFactory.getLogger(MyInboundHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("TCP Connected");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("TCP DisConnected");
        super.channelInactive(ctx);
    }
}
