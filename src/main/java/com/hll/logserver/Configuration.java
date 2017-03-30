package com.hll.logserver;

/**
 * Created by hll on 2016/3/12.
 */
public class Configuration {
  private String host;
  private int port;

  public Configuration() {
  }

  public Configuration(String host, int port) {
    this.host = host;
    this.port = port;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }
}
