package com.hll.logserver;

import com.lmax.disruptor.EventFactory;

/**
 * Created by hll on 2016/3/12.
 */
public class MessageEvent {
  private byte[] msg;

  public byte[] getMsg() {
    return msg;
  }

  public void setMsg(byte[] msg) {
    this.msg = msg;
  }

  public static EventFactory<MessageEvent> MESSAGE_ENTRY_FACTORY= MessageEvent::new;
}
