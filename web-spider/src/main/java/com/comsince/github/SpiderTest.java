package com.comsince.github;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.selector.XpathSelector;

/**
 * @author comsicne
 * Copyright (c) [2019]
 * @Time 19-6-24 下午5:03
 **/
public class SpiderTest {

    public static void main(String[] args){
        Spider.create(new GooglePlayProcessor()).addUrl("https://play.google.com/store/apps/details?id=com.doodle.countershot").thread(5).run();
    }

}
