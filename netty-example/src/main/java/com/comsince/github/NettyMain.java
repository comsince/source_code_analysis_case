package com.comsince.github;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-19 上午10:46
 **/
public class NettyMain {

    Logger logger = LoggerFactory.getLogger(NettyMain.class);

    public static void main(String[] args) throws Exception{
        new Thread(new Runnable() {
            @Override
            public void run() {
                Server server = new Server();
                try {
                    server.doStart();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


//         Client client =  new Client();
//         client.connect("localhost",6789);
         for(;;){
             Thread.sleep(Integer.MAX_VALUE);
         }
    }


}
