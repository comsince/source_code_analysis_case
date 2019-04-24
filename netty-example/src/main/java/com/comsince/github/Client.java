package com.comsince.github;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-19 上午11:01
 **/
public class Client {
    Logger logger = LoggerFactory.getLogger(Client.class);

    public Client(){
        initClientBootrap();
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
                 channel.pipeline().addLast(new MyInboundHandler());
            }
        });
    }

    public Channel connect(String targetIP, int targetPort) throws Exception {
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(targetIP, targetPort));
        future.awaitUninterruptibly();
        String address = targetIP + ":" + targetPort;
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
        channel = future.channel();
        return channel;
    }

    public void writeMessage(){
        channel.write("message");
    }
}
