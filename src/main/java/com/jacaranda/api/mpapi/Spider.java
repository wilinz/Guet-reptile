package com.jacaranda.api.mpapi;
/*
  @author Limou
 * @apiNote for mini_program jacaranda course
 */
//Dangling Javadoc comment
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Spider {

    public static String accept_encoding;
    public static String referer;
    public static String user_agent;
    public static String content_type;
    public static String referer1;
    public static String ck;
    public static String connection;
    public static String Host;
    public static URIBuilder uriBuilder;
    private static String msg;//成绩的json串
    private static String stu_score;
    private static String course_table;//课表的json串
    private static String credit;
    private static String exam;
    private static String select;

    public static String get_grade(String username,String password,String us,String pwd) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        List<NameValuePair> params = new ArrayList<>();
        String encryptPassword = Encrypt.encrypt(password);

        HttpPost httpPost = new HttpPost("https://v.guet.edu.cn/do-login");

        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", encryptPassword));
        CloseableHttpResponse httpResponse = null;
        CloseableHttpResponse httpResponse1 = null;
        String web_return = null;
        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "utf8");
            httpPost.setEntity(formEntity);
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                web_return = EntityUtils.toString(httpEntity, "utf-8");
                System.out.println(web_return);
            }
            if (web_return != null && web_return.contains("\"success\": false")) {
                msg = "error";
                httpResponse.close();
                return msg;
            }

            accept_encoding = "gzip, deflate, br";

            referer = "https://v.guet.edu.cn/login";
            user_agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.72 Safari/537.36 Edg/90.0.818.42";
            content_type = "text/html; charset=UTF-8";
            connection = "keep-alive";
            Host = "v.guet.edu.cn";
            referer1 = "https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/?mCode=000708";

            String path = "/etc/ckcode/1/" + username;
            File filepath = new File(path);
            if (!filepath.exists()) {
                boolean makadirectory = filepath.mkdir();
                if (!makadirectory) {
                    System.out.println("Create directory failed!");
                }
            }
            while (true) {
                String ckcode = GetCkImg.getCkImg(path, httpClient);
                ck = ckcode.substring(ckcode.indexOf("[") + 11, ckcode.indexOf("]") - 2);
                uriBuilder = new URIBuilder("https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/Login/SubmitLogin");
                uriBuilder.addParameter("us", us)
                        .addParameter("pwd", pwd)
                        .addParameter("ck", ck)
                        .build();

                HttpPost httpPost1 = new HttpPost(uriBuilder.build());

                httpResponse1 = httpClient.execute(httpPost1);
                String system_return = null;

                if (httpResponse1.getStatusLine().getStatusCode() == 200) {
                    HttpEntity httpEntity1 = httpResponse1.getEntity();
                    system_return = EntityUtils.toString(httpEntity1);
                    System.out.println(system_return);
                }
                assert system_return != null;
                if (!system_return.contains("\"success\":false")) break;
            }
            HttpGet get_stu_score = new HttpGet("https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/Student/GetStuScore");
            get_stu_score.addHeader("content-type", "application/json;charset=UTF-8");
            get_stu_score.addHeader("User-Agent", user_agent);
            get_stu_score.addHeader("referer", "https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/Login/MainDesktop");

            httpResponse1 = httpClient.execute(get_stu_score);
            if (httpResponse1.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity1 = httpResponse1.getEntity();
                stu_score = EntityUtils.toString(httpEntity1);
                msg = stu_score.substring(stu_score.indexOf('['), stu_score.indexOf(']') + 1);
                System.out.println(msg);
            }

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpResponse1!=null)httpResponse1.close();
                if (httpResponse!=null)httpResponse.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stu_score;
    }

    public static String get_courses(String username,String password,String us,String pwd) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        List<NameValuePair> params = new ArrayList<>();
        String encryptPassword = Encrypt.encrypt(password);

        HttpPost httpPost = new HttpPost("https://v.guet.edu.cn/do-login");

        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", encryptPassword));
        CloseableHttpResponse httpResponse = null;
        CloseableHttpResponse httpResponse1 = null;
        String web_return = null;
        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "utf8");
            httpPost.setEntity(formEntity);
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                web_return = EntityUtils.toString(httpEntity, "utf-8");
                System.out.println(web_return);
            }
            if (web_return != null && web_return.contains("\"success\": false")) {
                msg = "error";
                httpResponse.close();
                return msg;
            }

            accept_encoding = "gzip, deflate, br";

            referer = "https://v.guet.edu.cn/login";
            user_agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.72 Safari/537.36 Edg/90.0.818.42";
            content_type = "text/html; charset=UTF-8";
            connection = "keep-alive";
            Host = "v.guet.edu.cn";
            referer1 = "https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/?mCode=000708";

            String path = "/etc/ckcode/2/" + username;
            File filepath = new File(path);
            if (!filepath.exists()) {
                boolean makadirectory = filepath.mkdir();
                if (!makadirectory) {
                    System.out.println("Create directory failed!");
                }
            }
            while (true) {
                String ckcode = GetCkImg.getCkImg(path, httpClient);
                ck = ckcode.substring(ckcode.indexOf("[") + 11, ckcode.indexOf("]") - 2);
                uriBuilder = new URIBuilder("https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/Login/SubmitLogin");
                uriBuilder.addParameter("us", us)
                        .addParameter("pwd", pwd)
                        .addParameter("ck", ck)
                        .build();

                HttpPost httpPost1 = new HttpPost(uriBuilder.build());

                httpResponse1 = httpClient.execute(httpPost1);
                String system_return = null;

                if (httpResponse1.getStatusLine().getStatusCode() == 200) {
                    HttpEntity httpEntity1 = httpResponse1.getEntity();
                    system_return = EntityUtils.toString(httpEntity1);
                    System.out.println(system_return);
                }
                assert system_return != null;
                if (!system_return.contains("\"success\":false")) break;
            }
            HttpGet get_course = new HttpGet("https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/student/getstutable?term=2021-2022-1&page=1&start=0&limit=25");
            get_course.addHeader("content-type", "application/json;charset=UTF-8");
            get_course.addHeader("User-Agent", user_agent);
            get_course.addHeader("referer", "https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/Login/MainDesktop");

            httpResponse1 = httpClient.execute(get_course);
            if (httpResponse1.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity1 = httpResponse1.getEntity();
                course_table = EntityUtils.toString(httpEntity1);
                msg = course_table.substring(course_table.indexOf('['), course_table.indexOf(']') + 1);
                System.out.println(msg);
            }

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpResponse1!=null)httpResponse1.close();
                if (httpResponse!=null)httpResponse.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return course_table;
    }
    public static String get_credit(String username,String password,String us,String pwd){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        List<NameValuePair> params = new ArrayList<>();
        String encryptPassword = Encrypt.encrypt(password);

        HttpPost httpPost = new HttpPost("https://v.guet.edu.cn/do-login");

        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", encryptPassword));
        CloseableHttpResponse httpResponse = null;
        CloseableHttpResponse httpResponse1 = null;
        String web_return = null;
        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "utf8");
            httpPost.setEntity(formEntity);
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                web_return = EntityUtils.toString(httpEntity, "utf-8");
                System.out.println(web_return);
            }
            if (web_return != null && web_return.contains("\"success\": false")) {
                msg = "error";
                httpResponse.close();
                return msg;
            }

            accept_encoding = "gzip, deflate, br";

            referer = "https://v.guet.edu.cn/login";
            user_agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.72 Safari/537.36 Edg/90.0.818.42";
            content_type = "text/html; charset=UTF-8";
            connection = "keep-alive";
            Host = "v.guet.edu.cn";
            referer1 = "https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/?mCode=000708";

            String path = "/etc/ckcode/3/" + username;
            File filepath = new File(path);
            if (!filepath.exists()) {
                boolean makadirectory = filepath.mkdir();
                if (!makadirectory) {
                    System.out.println("Create directory failed!");
                }
            }
            while (true) {
                String ckcode = GetCkImg.getCkImg(path, httpClient);
                ck = ckcode.substring(ckcode.indexOf("[") + 11, ckcode.indexOf("]") - 2);
                uriBuilder = new URIBuilder("https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/Login/SubmitLogin");
                uriBuilder.addParameter("us", us)
                        .addParameter("pwd", pwd)
                        .addParameter("ck", ck)
                        .build();

                HttpPost httpPost1 = new HttpPost(uriBuilder.build());

                httpResponse1 = httpClient.execute(httpPost1);
                String system_return = null;

                if (httpResponse1.getStatusLine().getStatusCode() == 200) {
                    HttpEntity httpEntity1 = httpResponse1.getEntity();
                    system_return = EntityUtils.toString(httpEntity1);
                    System.out.println(system_return);
                }
                assert system_return != null;
                if (!system_return.contains("\"success\":false")) break;
            }
            HttpGet get_credit = new HttpGet("https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/student/Getyxxf");
            get_credit.addHeader("content-type", "application/json;charset=UTF-8");
            get_credit.addHeader("User-Agent", user_agent);
            get_credit.addHeader("referer", "https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/Login/MainDesktop");

            httpResponse1 = httpClient.execute(get_credit);
            if (httpResponse1.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity1 = httpResponse1.getEntity();
                credit = EntityUtils.toString(httpEntity1);
                msg = credit.substring(credit.indexOf('['), credit.indexOf(']') + 1);
                System.out.println(msg);
            }

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpResponse1!=null)httpResponse1.close();
                if (httpResponse!=null)httpResponse.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return credit;
    }
    public static String get_exam(String username,String password,String us,String pwd){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        List<NameValuePair> params = new ArrayList<>();
        String encryptPassword = Encrypt.encrypt(password);

        HttpPost httpPost = new HttpPost("https://v.guet.edu.cn/do-login");

        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", encryptPassword));
        CloseableHttpResponse httpResponse = null;
        CloseableHttpResponse httpResponse1 = null;
        String web_return = null;
        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "utf8");
            httpPost.setEntity(formEntity);
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                web_return = EntityUtils.toString(httpEntity, "utf-8");
                System.out.println(web_return);
            }
            if (web_return != null && web_return.contains("\"success\": false")) {
                msg = "error";
                httpResponse.close();
                return msg;
            }

            accept_encoding = "gzip, deflate, br";

            referer = "https://v.guet.edu.cn/login";
            user_agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.72 Safari/537.36 Edg/90.0.818.42";
            content_type = "text/html; charset=UTF-8";
            connection = "keep-alive";
            Host = "v.guet.edu.cn";
            referer1 = "https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/?mCode=000708";

            String path = "/etc/ckcode/4/" + username;
            File filepath = new File(path);
            if (!filepath.exists()) {
                boolean makadirectory = filepath.mkdir();
                if (!makadirectory) {
                    System.out.println("Create directory failed!");
                }
            }
            while (true) {
                String ckcode = GetCkImg.getCkImg(path, httpClient);
                ck = ckcode.substring(ckcode.indexOf("[") + 11, ckcode.indexOf("]") - 2);
                uriBuilder = new URIBuilder("https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/Login/SubmitLogin");
                uriBuilder.addParameter("us", us)
                        .addParameter("pwd", pwd)
                        .addParameter("ck", ck)
                        .build();

                HttpPost httpPost1 = new HttpPost(uriBuilder.build());

                httpResponse1 = httpClient.execute(httpPost1);
                String system_return = null;

                if (httpResponse1.getStatusLine().getStatusCode() == 200) {
                    HttpEntity httpEntity1 = httpResponse1.getEntity();
                    system_return = EntityUtils.toString(httpEntity1);
                    System.out.println(system_return);
                }
                assert system_return != null;
                if (!system_return.contains("\"success\":false")) break;
            }
            HttpGet get_exam = new HttpGet("https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/student/getexamap");
            get_exam.addHeader("content-type", "application/json;charset=UTF-8");
            get_exam.addHeader("User-Agent", user_agent);
            get_exam.addHeader("referer", "https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/Login/MainDesktop");

            httpResponse1 = httpClient.execute(get_exam);
            if (httpResponse1.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity1 = httpResponse1.getEntity();
                exam = EntityUtils.toString(httpEntity1);
                msg = exam.substring(exam.indexOf('['), exam.indexOf(']') + 1);
                System.out.println(msg);
            }

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpResponse1!=null)httpResponse1.close();
                if (httpResponse!=null)httpResponse.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return exam;
    }

    //已选课程
    public static String have_select(String username,String password,String us,String pwd){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        List<NameValuePair> params = new ArrayList<>();
        String encryptPassword = Encrypt.encrypt(password);

        HttpPost httpPost = new HttpPost("https://v.guet.edu.cn/do-login");

        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", encryptPassword));
        CloseableHttpResponse httpResponse = null;
        CloseableHttpResponse httpResponse1 = null;
        String web_return = null;
        try {
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "utf8");
            httpPost.setEntity(formEntity);
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                web_return = EntityUtils.toString(httpEntity, "utf-8");
                System.out.println(web_return);
            }
            if (web_return != null && web_return.contains("\"success\": false")) {
                msg = "error";
                httpResponse.close();
                return msg;
            }

            accept_encoding = "gzip, deflate, br";

            referer = "https://v.guet.edu.cn/login";
            user_agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.72 Safari/537.36 Edg/90.0.818.42";
            content_type = "text/html; charset=UTF-8";
            connection = "keep-alive";
            Host = "v.guet.edu.cn";
            referer1 = "https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/?mCode=000708";

            String path = "/etc/ckcode/5/" + username;
            File filepath = new File(path);
            if (!filepath.exists()) {
                boolean makadirectory = filepath.mkdir();
                if (!makadirectory) {
                    System.out.println("Create directory failed!");
                }
            }
            while (true) {
                String ckcode = GetCkImg.getCkImg(path, httpClient);
                ck = ckcode.substring(ckcode.indexOf("[") + 11, ckcode.indexOf("]") - 2);
                uriBuilder = new URIBuilder("https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/Login/SubmitLogin");
                uriBuilder.addParameter("us", us)
                        .addParameter("pwd", pwd)
                        .addParameter("ck", ck)
                        .build();

                HttpPost httpPost1 = new HttpPost(uriBuilder.build());

                httpResponse1 = httpClient.execute(httpPost1);
                String system_return = null;

                if (httpResponse1.getStatusLine().getStatusCode() == 200) {
                    HttpEntity httpEntity1 = httpResponse1.getEntity();
                    system_return = EntityUtils.toString(httpEntity1);
                    System.out.println(system_return);
                }
                assert system_return != null;
                if (!system_return.contains("\"success\":false")) break;
            }
            HttpGet have_select = new HttpGet("https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/student/GetSctCourse?vpn-12-o1-172.16.13.22&comm=1%401&term=2021-2022-1&page=1&start=0&limit=25");
            have_select.addHeader("content-type", "application/json;charset=UTF-8");
            have_select.addHeader("User-Agent", user_agent);
            have_select.addHeader("referer", "https://v.guet.edu.cn/http/77726476706e69737468656265737421a1a013d2766626012d46dbfe/Login/MainDesktop");

            httpResponse1 = httpClient.execute(have_select);
            if (httpResponse1.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity1 = httpResponse1.getEntity();
                select = EntityUtils.toString(httpEntity1);
                msg = select.substring(select.indexOf('['), select.indexOf(']') + 1);
                System.out.println(select);
            }

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            try {
                if (httpResponse1!=null)httpResponse1.close();
                if (httpResponse!=null)httpResponse.close();
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return select;
    }
}