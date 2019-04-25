package com.comsince.github;

import com.comsince.github.model.RequestCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-25 上午10:43
 **/
public class PushMessageEncoder extends MessageToByteEncoder<RequestCommand>{
    @Override
    protected void encode(ChannelHandlerContext ctx, RequestCommand msg, ByteBuf out) throws Exception {
       out.writeBytes(msg.ByteArray());
    }
}
