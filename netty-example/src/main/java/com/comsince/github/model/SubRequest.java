package com.comsince.github.model;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-25 上午10:55
 **/
public class SubRequest extends RequestCommand{

    public SubRequest() {
        super(Signal.SUB, "{\"uid\":\"\"}");
    }
}
