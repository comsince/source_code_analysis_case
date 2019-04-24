# Netty 基本原理解析
# 概述
本文章力图循序渐进的描绘网络相关的知识，进而对网络编程有一个较为整体的理解.本文是在消息通讯分布式架构基础上对NIO，AIO进行整理时，希望自己在实践中，不断加深对其理解，因而需要对所做的东西进行整理，这里附开源项目地址:
* [github universe_push](https://github.com/comsince/universe_push)
* [gitee univers_push](https://gitee.com/comsince/universe_push)
# Java NIO

* NIO Channel
* NIO Buffer
* NIO Selector

# Netty 核心源码分析

## BootStrap
### 服务端的创建过程
* 示例代码
```java
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

    
```

* 绑定开始
```java
protected boolean doStart() throws InterruptedException {
        this.channelFuture = this.bootstrap.bind(new InetSocketAddress("localhost", 6789)).sync();
        return this.channelFuture.isSuccess();
    }
```
**NOTE:** 服务开启是通过从这个bing方法开始

* 注册的就是channel注册到eventLoop多路复用器的过程

```java
protected void doRegister() throws Exception {
        boolean selected = false;
        for (;;) {
            try {
                selectionKey = javaChannel().register(eventLoop().unwrappedSelector(), 0, this);
                return;
            } catch (CancelledKeyException e) {
                if (!selected) {
                    // Force the Selector to select now as the "canceled" SelectionKey may still be
                    // cached and not removed because no Select.select(..) operation was called yet.
                    eventLoop().selectNow();
                    selected = true;
                } else {
                    // We forced a select operation on the selector before but the SelectionKey is still cached
                    // for whatever reason. JDK bug ?
                    throw e;
                }
            }
        }
    }
```

**NOTE:** 这里register初始的是0,之后会在合适的时机通过selectionKey变更事件关注类型

* 首次注册等待bind成功，调用firechannelactive方法实现监听accept事件

```java
<!-- head context-->
@Override
public void channelActive(ChannelHandlerContext ctx) throws Exception {
     ctx.fireChannelActive();
     readIfIsAutoRead();
}

private void readIfIsAutoRead() {
     if (channel.config().isAutoRead()) {
         channel.read();
         }
     }

<!-- AbstractNioChannel -->                                
@Override
    protected void doBeginRead() throws Exception {
        // Channel.read() or ChannelHandlerContext.read() was called
        final SelectionKey selectionKey = this.selectionKey;
        if (!selectionKey.isValid()) {
            return;
        }

        readPending = true;

        final int interestOps = selectionKey.interestOps();
        if ((interestOps & readInterestOp) == 0) {
            selectionKey.interestOps(interestOps | readInterestOp);
        }
    }
```

### 客户端的创建过程

* ChannelPipline
* EventLoop


# 参考资料

* [源码之下无秘密 ── 做最好的 Netty 源码分析教程](https://segmentfault.com/a/1190000007282628)
* [蚂蚁金服通信框架SOFABolt解析-协议框架解析](https://www.sofastack.tech/posts/2018-12-06-01)