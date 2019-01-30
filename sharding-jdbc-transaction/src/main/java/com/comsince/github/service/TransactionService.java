package com.comsince.github.service;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-1-30 上午10:45
 **/
public interface TransactionService extends CommonService {

    void processFailureWithLocal();

    void processFailureWithXa();

    void processFailureWithBase();

    void printTransactionType();
}
