package com.comsince.github;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.SocketChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-19 上午10:46
 **/
public class NettyMain {

    Logger logger = LoggerFactory.getLogger(NettyMain.class);

    public static void main(String[] args) throws Exception{
        //buffer();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Server server = new Server();
//                try {
//                    server.doStart();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();


         Client client =  new Client("172.16.177.107",6789);
         client.start();
         for(;;){
             Thread.sleep(Integer.MAX_VALUE);
         }

    }


    public static void buffer(){
        ByteBuf byteBuf = Unpooled.buffer(128,1024);
        System.out.println("readIndex "+byteBuf.readerIndex()+" writeIndex "+byteBuf.writerIndex()+" capacity "+byteBuf.capacity());
        byte[] test = new byte[129];
        byteBuf.writeBytes(test);
        System.out.println("readIndex "+byteBuf.readerIndex()+" writeIndex "+byteBuf.writerIndex()+" capacity "+byteBuf.capacity());
        byteBuf.writeByte(1);
        System.out.println("readIndex "+byteBuf.readerIndex()+" writeIndex "+byteBuf.writerIndex() +" capacity "+byteBuf.capacity());
        for(int i = 0;i<6;i++){
            byteBuf.writeByte(0);
        }
        System.out.println("readIndex "+byteBuf.readerIndex()+" writeIndex "+byteBuf.writerIndex() +" capacity "+byteBuf.capacity());
    }


}
