package com.hll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;

/**
 * LogServer启动引导类
 * Created by hll on 2016/3/12.
 */
public class LogServerBootstrap {

  private static Logger logger = LoggerFactory.getLogger(LogServerBootstrap.class);
  private static LogService logService;
  private static LogServer logServer;


  public static void main(String[] args) {
    try {
      //异步消费服务
      logService =LogService.getInstance();
      logService.start("f://logserver.log");

      //服务器
      Configuration configuration = new Configuration();
      configuration.setHost("127.0.0.1");
      configuration.setPort(9999);

      logServer = new LogServer(configuration);
      logServer.init();
      logServer.start();
      Thread.sleep(Long.MAX_VALUE);
    } catch (InterruptedException e) {
      logger.warn("server exception:",e);
    } catch (FileNotFoundException e) {
      logger.error("找不到日志文件",e);
    } finally {
      if (logServer != null) {
        logServer.stop();
      }
      if (logService != null) {
        logService.stop();
      }
    }
  }
}
