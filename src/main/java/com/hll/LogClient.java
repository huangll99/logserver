package com.hll;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpHost;
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

  private static Logger logger= LoggerFactory.getLogger(LogClient.class);

  public static void main(String[] args) throws IOException {


    for (int i=0;i<1000;i++){
      CloseableHttpClient client = HttpClients.createDefault();
      HttpPost httpPost=new HttpPost("http://localhost:9999");
      StringEntity stringEntity = new StringEntity("hello-"+i, Charsets.UTF_8);
      httpPost.setEntity(stringEntity);
      CloseableHttpResponse response = client.execute(httpPost);
      logger.info(response.toString());
      client.close();
    }

  }
}
