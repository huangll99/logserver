package com.hll.logserver;

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
      logService = LogService.getInstance();
      logService.start("f://logserver.log");

      //服务器
      Configuration configuration = new Configuration(args[0], Integer.parseInt(args[1]));
      logServer = new LogServer(configuration);
      logServer.init();
      logServer.start();
      Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override
        public void run() {
          logServer.stop();
          logService.stop();
          System.exit(0);
        }
      });
    } catch (FileNotFoundException e) {
      logger.error("找不到日志文件", e);
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
