package com.comsince.github.extension;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-5-8 上午10:19
 **/
public class InjectWrapper2 implements InjectExt{
    InjectExt injectExt;
    public InjectWrapper2(InjectExt injectExt){
        this.injectExt = injectExt;
    }
    @Override
    public String echo(String msg) {
        System.out.println("InjectWrapper2 echo");
        return injectExt.echo(msg);
    }
}
