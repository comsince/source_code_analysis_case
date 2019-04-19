package com.comsince.github;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-19 上午11:17
 **/
public class Server {

    Logger logger = LoggerFactory.getLogger(Server.class);

    public Server(){
        init();
    }

    /** server bootstrap */
    private ServerBootstrap                             bootstrap;
    /** channelFuture */
    private ChannelFuture                               channelFuture;

    EventLoopGroup bossGroup = new NioEventLoopGroup( 1, new NamedThreadFactory(
            "netty-server-boss", true));

    EventLoopGroup workerGroup = new NioEventLoopGroup( Runtime
            .getRuntime()
            .availableProcessors() * 2, new NamedThreadFactory(
            "netty-server-worker", true));

    private void init(){
        this.bootstrap = new ServerBootstrap();
        this.bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true);


        this.bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);


        this.bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel channel) {

            }
        });
    }

    protected boolean doStart() throws InterruptedException {
        this.channelFuture = this.bootstrap.bind(new InetSocketAddress("localhost", 6789)).sync();
        return this.channelFuture.isSuccess();
    }

    protected boolean doStop() {
        if (null != this.channelFuture) {
            this.channelFuture.channel().close();
        }
        this.bossGroup.shutdownGracefully();

        logger.warn("Rpc Server stopped!");
        return true;
    }
}
