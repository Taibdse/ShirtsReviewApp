/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taibd.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Dell
 */
public class HttpUtils {
    public static InputStream getHttp(String href) throws MalformedURLException, IOException{
        URL url = null;
        HttpURLConnection connection = null;
        InputStream is = null;
        System.out.println("connection");
        
        try {
            href = normalizeString(href);
            url = new URL(href);
            connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent", 
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            
            is = connection.getInputStream();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
        
        return is;
    }
    
    public static String getHttpContent(String href) throws MalformedURLException, IOException {
        URL url = null;
        HttpURLConnection connection = null;
        InputStream is = null;
        String data = "";
        
        try {
            href = normalizeString(href);
            url = new URL(href);
            connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent", 
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            
            is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = "";
            while((line = br.readLine()) != null) {
                data += line;
            }
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
        return data;
    }

    private static String normalizeString(String url) {
        return url.replace("&amp;", "&");
    }
}
