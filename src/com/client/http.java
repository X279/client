package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class http {
    private URL obj;
    private HttpURLConnection con;
    private final String recvAddress;
    private final String subject;
    private final String information;
    private final boolean state; //true为发送邮件，false为验证邮箱

    public http(String recvAddress,String subject,String information,boolean state) {
        this.recvAddress = recvAddress;
        this.subject = subject;
        this.information = information;
        this.state = state;
    }

    public String setRequest() throws IOException {
        String url = "http://localhost:8080/";
        if(state){
            url += "send?address=";
//            url+=recvAddress;
            url += java.net.URLEncoder.encode(recvAddress, "utf-8");
            if(subject != null){
                url += "&&topic=";
                url += java.net.URLEncoder.encode(subject, "utf-8");;
            }
            if(information != null){
                url += "&&information=";
                url += java.net.URLEncoder.encode(information, "utf-8");
            }
        }
        else{
            url += "varify?address=";
            url += java.net.URLEncoder.encode(recvAddress, "utf-8");
        }
        System.out.println(url);
        obj = new URL(url);
        con = (HttpURLConnection)obj.openConnection();
        con.setRequestProperty("User-Agent","Mozilla/5.0(Windows NT 6.1; WOW64)...");
        con.setRequestProperty("Accept-Language", "*");
        //en-US,en;q=0.5,zh-cn,zh;q=0.9
        con.setRequestMethod("GET");
        int responedCode = con.getResponseCode();
        String responedBody = readResponseBody(con.getInputStream());
        System.out.println("服务器返回信息："+responedBody);
        return responedBody;
    }

    private String readResponseBody(InputStream inputStream) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
