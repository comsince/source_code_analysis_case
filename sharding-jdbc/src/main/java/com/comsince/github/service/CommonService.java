package com.comsince.github.service;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-1-28 下午2:44
 **/
public interface CommonService {
    void initEnvironment();

    void cleanEnvironment();

    void processSuccess();

    void processFailure();

    void printData();
}
