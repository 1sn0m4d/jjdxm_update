package com.dou361.update.business;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Administrator
 */
public class DefaultUpdateWorker extends UpdateWorker {
    @Override
    protected String check(String urlStr) throws Exception {
        URL getUrl = new URL(urlStr);
        HttpURLConnection urlConn = (HttpURLConnection) getUrl.openConnection();
        urlConn.setDoInput(true);
        urlConn.setDoOutput(true);
        urlConn.setUseCaches(false);
        urlConn.setConnectTimeout(10000);
        urlConn.setRequestMethod("GET");
        urlConn.connect();

        int responseCode = urlConn.getResponseCode();
        if (responseCode < 200 || responseCode >= 300) {
            throw new HttpException(responseCode,urlConn.getResponseMessage());
        }

        BufferedReader bis = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "utf-8"));

        StringBuilder sb = new StringBuilder();
        String lines;
        while ((lines = bis.readLine()) != null) {
            sb.append(lines);
        }

        return sb.toString();
    }

}
