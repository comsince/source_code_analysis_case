package com.comsince.github;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @author comsicne
 * Copyright (c) [2019]
 * @Time 19-6-24 下午5:07
 **/
public class GooglePlayProcessor implements PageProcessor {
    Logger logger = LoggerFactory.getLogger(GooglePlayProcessor.class);
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public void process(Page page) {
        List<String> appOtherinfoList = page.getHtml().xpath("//div[@class='W4P4ne']").regex("<span class=\"htlgb\">([^\n]+)</span>").all();
        logger.info("appinfolist size {}",appOtherinfoList.size());
        for(String info : appOtherinfoList){
            logger.info(info);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
