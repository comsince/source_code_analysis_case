package com.comsince.github;

import com.comsince.github.model.ResponseCommand;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-25 上午11:26
 **/
public class PushMessageHandler extends ChannelInboundHandlerAdapter {
    Logger logger = LoggerFactory.getLogger(PushMessageHandler.class);
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof ResponseCommand){
            ResponseCommand responseCommand = (ResponseCommand) msg;
            logger.info("response "+responseCommand.bodyToString());
        }
    }
}
