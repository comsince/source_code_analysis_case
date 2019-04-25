package com.comsince.github;

import com.comsince.github.model.Header;
import com.comsince.github.model.ResponseCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-25 上午10:43
 **/
public class PushMessageDecoder extends ByteToMessageDecoder{
    Logger logger = LoggerFactory.getLogger(PushMessageDecoder.class);
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.resetReaderIndex();
        if(in.readableBytes() >= Header.LENGTH){
            in.markReaderIndex();
            ByteBuf headerBuf = ctx.alloc().buffer(Header.LENGTH);
            in.readBytes(headerBuf);
            ResponseCommand responseCommand = new ResponseCommand(headerBuf);
            int contentLength = responseCommand.getContentLength();
            if(contentLength != 0 && in.readableBytes() >= contentLength){
                byte[] content = new byte[contentLength];
                in.readBytes(content);
                responseCommand.setBody(content);
                out.add(responseCommand);
            }
        }
    }
}
