package com.comsince.github.extension;

import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-5-7 下午6:13
 **/
class SimpleExtAdaptive implements com.comsince.github.extension.SimpleExt {
    public java.lang.String echo(org.apache.dubbo.common.URL arg0, java.lang.String arg1)  {
        if (arg0 == null) throw new IllegalArgumentException("url == null");
        org.apache.dubbo.common.URL url = arg0;
        String extName = url.getParameter("simple.ext", "impl1");
        if(extName == null) throw new IllegalStateException("Failed to get extension (com.comsince.github.extension.SimpleExt) name from url (" + url.toString() + ") use keys([simple.ext])");
        com.comsince.github.extension.SimpleExt extension = (com.comsince.github.extension.SimpleExt) ExtensionLoader.getExtensionLoader(com.comsince.github.extension.SimpleExt.class).getExtension(extName);
        return extension.echo(arg0, arg1);
    }
    public java.lang.String yell(org.apache.dubbo.common.URL arg0, java.lang.String arg1)  {
        if (arg0 == null) throw new IllegalArgumentException("url == null");
        org.apache.dubbo.common.URL url = arg0;
        String extName = url.getParameter("key1", url.getParameter("key2", "impl1"));
        if(extName == null) throw new IllegalStateException("Failed to get extension (com.comsince.github.extension.SimpleExt) name from url (" + url.toString() + ") use keys([key1, key2])");
        com.comsince.github.extension.SimpleExt extension = (com.comsince.github.extension.SimpleExt)ExtensionLoader.getExtensionLoader(com.comsince.github.extension.SimpleExt.class).getExtension(extName);
        return extension.yell(arg0, arg1);
    }
    public java.lang.String bang(org.apache.dubbo.common.URL arg0, int arg1)  {
        throw new UnsupportedOperationException("The method public abstract java.lang.String com.comsince.github.extension.SimpleExt.bang(org.apache.dubbo.common.URL,int) of interface com.comsince.github.extension.SimpleExt is not adaptive method!");
    }
}