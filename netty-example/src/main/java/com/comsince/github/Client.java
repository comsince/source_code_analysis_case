package com.comsince.github;

import com.comsince.github.model.HeartBeatRequest;
import com.comsince.github.model.RequestCommand;
import com.comsince.github.model.Signal;
import com.comsince.github.model.SubRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-19 上午11:01
 *
 *
 * 本demo使用时的基于开源项目[universe_push](https://github.com/comsince/universe_push) 项目的作为长连接服务器
 **/
public class Client {
    Logger logger = LoggerFactory.getLogger(Client.class);

    ScheduledExecutorService scheduledExecutorService;
    String host;
    int port;
    volatile int interval = 1;
    boolean isStop = false;
    public Client(){
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("schedule",true));
        initClientBootrap();
    }

    public Client(String host,int port){
        this();
        this.host = host;
        this.port = port;
    }

    protected Bootstrap                 bootstrap;
    private Channel channel;


    private void initClientBootrap(){
        bootstrap = new Bootstrap();
        EventLoopGroup workGroup = new NioEventLoopGroup(Runtime
                .getRuntime().availableProcessors() + 1, new NamedThreadFactory(
                "netty-client-worker", true));
        bootstrap.group(workGroup).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_KEEPALIVE, true);
        this.bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);


        bootstrap.handler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel channel) {
                 channel.pipeline().addLast(new MyInboundHandler(Client.this));
                 channel.pipeline().addLast(new PushMessageEncoder());
                 channel.pipeline().addLast(new PushMessageDecoder());
                 channel.pipeline().addLast(new PushMessageHandler());
            }
        });
    }

    private Channel connect() throws Exception {
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));
        future.awaitUninterruptibly();
        String address = host + ":" + port;
        if (!future.isDone()) {
            String errMsg = "Create connection to " + address + " timeout!";
            logger.warn(errMsg);
            throw new Exception(errMsg);
        }
        if (future.isCancelled()) {
            String errMsg = "Create connection to " + address + " cancelled by user!";
            logger.warn(errMsg);
            throw new Exception(errMsg);
        }
        if (!future.isSuccess()) {
            String errMsg = "Create connection to " + address + " error!";
            logger.warn(errMsg);
            throw new Exception(errMsg, future.cause());
        }
        isStop = false;
        channel = future.channel();
        return channel;
    }

    public void writeMessage(){
        channel.write("message");
    }

    public ChannelFuture sub(){
        logger.info("send sub signal");
        return channel.writeAndFlush(new SubRequest());
    }

    public void scheduleHeartBeat(){
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                if(!isStop){
                    int heartInterval = interval++ * 30 * 1000;
                    logger.info("send heartbeat interval "+heartInterval);
                    channel.writeAndFlush(new HeartBeatRequest(heartInterval));
                    scheduledExecutorService.schedule(this,heartInterval,TimeUnit.MILLISECONDS);
                }
            }
        },interval++ * 30 * 1000,TimeUnit.MILLISECONDS);
    }

    private void disconnect(){
        channel.disconnect();
    }

    public void start(){
        try {
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        isStop = true;
        resetInterval();
        disconnect();
    }

    private void resetInterval(){
        interval = 1;
    }
}
