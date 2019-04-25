package com.comsince.github.model;

import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-25 上午10:59
 **/
public class ResponseCommand {
    private Header header;
    private byte[] body;

    public ResponseCommand(ByteBuf byteBuf){
        header = new Header(byteBuf.nioBuffer());
    }

    public void setBody(byte[] body){
        this.body = body;
    }

    public int getContentLength(){
        return header.getLength();
    }

    public String bodyToString(){
        try {
            return new String(body,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
