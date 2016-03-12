package com.hll;

import com.lmax.disruptor.EventTranslatorOneArg;

/**
 * Created by hll on 2016/3/12.
 */
public class TestArg {
  public static void main(String[] args) {
    EventTranslatorOneArg<MessageEvent, byte[]> eventTranslatorOneArg =new EventTranslatorOneArg<MessageEvent, byte[]>() {
      @Override
      public void translateTo(MessageEvent event, long sequence, byte[] arg0) {

      }
    };
  }
}
