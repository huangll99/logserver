package com.hll.logserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hll on 2016/3/12.
 */
public class LogServer {

  private Logger logger = LoggerFactory.getLogger(LogServer.class);

  private Configuration config;

  private NioEventLoopGroup bossGroup;
  private NioEventLoopGroup workerGroup;
  private ServerBootstrap bootstrap;

  public LogServer(Configuration config) {
    this.config = config;
  }

  /**
   * 初始化Server
   */
  public void init() {
    //接入线程
    bossGroup = new NioEventLoopGroup(1);
    //读写线程组
    workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);

    bootstrap = new ServerBootstrap();
    bootstrap.group(bossGroup, workerGroup)
        .channel(NioServerSocketChannel.class)
        .option(ChannelOption.SO_BACKLOG, 100)
        .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
        .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)  //保持连接
        .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS,1000*10)
        .childHandler(new ChannelInitializer<NioSocketChannel>() {
          @Override
          protected void initChannel(NioSocketChannel ch) throws Exception {
            ch.pipeline()
                .addLast(new HttpRequestDecoder())
                .addLast(new HttpObjectAggregator(1024 * 1024))
                .addLast(new HttpResponseEncoder())
                .addLast(new LogMessageHandler());
          }
        });
  }

  /**
   * 启动Server
   */
  public void start() {
    bootstrap.bind(config.getHost(), config.getPort())
        .addListener(future -> {
          if (future.isSuccess()) {
            logger.info("LogServer started at port: {}", config.getPort());
          } else {
            logger.info("LogServer start failed at port: {}", config.getPort());
          }
        }).syncUninterruptibly();
  }

  /**
   * 停止Server
   */
  public void stop() {
    workerGroup.shutdownGracefully();
    bossGroup.shutdownGracefully();
  }
}
