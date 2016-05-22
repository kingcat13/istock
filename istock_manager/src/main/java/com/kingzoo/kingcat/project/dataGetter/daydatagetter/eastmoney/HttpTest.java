package com.kingzoo.kingcat.project.dataGetter.daydatagetter.eastmoney;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by gonghongrui on 14/11/16.
 */
public class HttpTest {

    public static void main(String[] args) throws IOException {
        String url = "http://data.eastmoney.com/zjlx/600748.html";


        HttpGet httpGet = new HttpGet(url);
        HttpClient httpClient = HttpClients.createDefault();

            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String json = EntityUtils.toString(entity, "UTF-8");

        System.out.println(json);
    }
}
