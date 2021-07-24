package com.jacaranda.api.mpapi;

import com.baidu.ai.api.GeneralBasic;
import org.apache.commons.io.FileUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class GetCkImg {

    public static String ckcode;
    public static String ckPath;
    public static String getCkImg(String filepath, CloseableHttpClient httpClient ) {


        HttpGet httpGet = new HttpGet("https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/login/GetValidateCode");
        httpGet.addHeader(new BasicHeader("referer", "https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/?mCode=000708"));
        httpGet.addHeader(new BasicHeader("accept-encoding", "gzip, deflate, br"));
        httpGet.addHeader(new BasicHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:88.0) Gecko/20100101 Firefox/88.0"));
        httpGet.addHeader(new BasicHeader("connection", "keep-alive"));

        httpGet.addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        httpGet.addHeader(new BasicHeader("Accept-language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2"));
        httpGet.addHeader(new BasicHeader("Host", "v.guet.edu.cn"));
        httpGet.addHeader(new BasicHeader("Upgrade-Insecure-Requests", "1"));

        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            Header[] headers = response.getHeaders("set-cookie");
            for (Header header : headers) {
                System.out.println(header.getName() + ":6555" + header.getValue());
            }

            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();

            ckPath = filepath + "/ck.jpeg";
            FileUtils.copyToFile(inputStream, new File(ckPath));
            ckcode = GeneralBasic.generalBasic(ckPath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return ckcode;
    }
}
