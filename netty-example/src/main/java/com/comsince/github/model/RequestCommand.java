package com.comsince.github.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.internal.StringUtil;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-25 上午10:50
 **/
public class RequestCommand {
    private Header header;
    private Signal signal;
    private String body;

    public RequestCommand(Signal signal,String body){
        this.signal = signal;
        this.body = body;
    }

    public ByteBuf ByteArray(){
        header = new Header();
        header.setSignal(signal);
        if(!StringUtil.isNullOrEmpty(body)){
            header.setLength(body.getBytes().length);
        }
        ByteBuf byteBuf = Unpooled.buffer(header.LENGTH+header.getLength());
        byteBuf.writeBytes(header.getContents());
        byteBuf.writeBytes(body.getBytes());
        return byteBuf;
    }
}
