package com.hll;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志消息处理器
 * Created by hll on 2016/3/12.
 */
public class LogMessageHandler extends ChannelHandlerAdapter {
  private final EventTranslatorOneArg<MessageEvent, byte[]> translator = new EventTranslatorOneArg<MessageEvent, byte[]>() {
    @Override
    public void translateTo(MessageEvent event, long sequence, byte[] arg0) {
      event.setMsg(arg0);
    }
  };

  private Logger logger = LoggerFactory.getLogger(LogMessageHandler.class);

  private RingBuffer<MessageEvent> ringBuffer=LogService.getInstance().getRingBuffer();

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    //读取消息
    FullHttpRequest request = (FullHttpRequest) msg;
    ByteBuf content = request.content();
    byte[] bytes = new byte[content.readableBytes()];
    content.readBytes(bytes);
    request.release();

    //将消息放入ringBuffer
    ringBuffer.publishEvent(translator,bytes);

    //返回OK响应
    ctx.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK));
  }
}
