package com.hll.logserver;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.apache.commons.codec.Charsets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.Executors;

/**
 * Created by hll on 2016/3/12.
 */
public class LogService {

  private LogService() {
  }

  private static final LogService INSTANCE = new LogService();

  public static LogService getInstance() {
    return INSTANCE;
  }

  private Disruptor<MessageEvent> disruptor;
  private PrintWriter writer;

  public void start(String filename) throws FileNotFoundException {
    File file = new File(filename);
    writer = new PrintWriter(file);
    disruptor = new Disruptor<>(MessageEvent.MESSAGE_ENTRY_FACTORY, 1024 * 1024,
        Executors.newCachedThreadPool(), ProducerType.MULTI, new BlockingWaitStrategy());
    //noinspection unchecked
    disruptor.handleEventsWith((EventHandler<MessageEvent>) (event, sequence, endOfBatch) -> {
      writer.println(new String(event.getMsg(), Charsets.UTF_8));
      writer.flush();
    });
    disruptor.start();
  }

  public void stop() {
    if (disruptor != null)
      disruptor.shutdown();
    if (writer != null)
      writer.close();
  }

  public RingBuffer<MessageEvent> getRingBuffer() {
    return disruptor.getRingBuffer();
  }
}
