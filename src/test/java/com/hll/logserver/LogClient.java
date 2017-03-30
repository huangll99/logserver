package com.hll.logserver;

import org.apache.commons.codec.Charsets;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by hll on 2016/3/12.
 */
public class LogClient {

  private static Logger logger = LoggerFactory.getLogger(LogClient.class);

  public static void main(String[] args) throws InterruptedException {

    long start = System.currentTimeMillis();
    Thread[] threads = new Thread[4];
    for (int t = 0; t < 4; t++) {
      Thread thread = new Thread(() -> {
        try {

          for (int i = 0; i < 100000; i++) {
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost("http://127.0.0.1:9999");
            StringEntity stringEntity = new StringEntity("hello-hello-hello-hello-hello-hello-hello-hello-hello-hello-hello-hello-hello-hello-hello-" + i, Charsets.UTF_8);
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = null;
            response = client.execute(httpPost);
            //logger.info(response.toString());
            client.close();
          }

        } catch (ClientProtocolException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }

      });
      thread.start();
      threads[t] = thread;
    }
    for (int i = 0; i < threads.length; i++) {
      threads[i].join();
    }
    long end = System.currentTimeMillis();

    System.out.println("Duration:" + (end - start) / 1000 + "s");
  }
}
