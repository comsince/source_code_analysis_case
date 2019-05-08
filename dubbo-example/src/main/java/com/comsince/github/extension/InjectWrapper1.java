package com.comsince.github.extension;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-5-8 上午10:17
 **/
public class InjectWrapper1 implements InjectExt{
    InjectExt injectExt;
    public InjectWrapper1(InjectExt injectExt){
        this.injectExt = injectExt;
    }
    @Override
    public String echo(String msg) {
        System.out.println("InjectWrapper1 echo");
        return injectExt.echo(msg);
    }
}
