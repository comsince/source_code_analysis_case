package com.comsince.github.model;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-25 下午2:21
 **/
public class HeartBeatRequest extends RequestCommand{
    public HeartBeatRequest(int interval) {
        super(Signal.PING, "{\"interval\":" + interval + "}");
    }
}
