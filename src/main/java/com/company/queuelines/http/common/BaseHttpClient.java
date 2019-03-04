package com.company.queuelines.http.common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BaseHttpClient {

    public String sendGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod(HttpMethod.GET);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public boolean sendPost(String url, String requestBody) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod(HttpMethod.POST);

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(requestBody);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();

        return isSuccess(responseCode);
    }

    private static boolean isSuccess(int responseCode) {
        return responseCode == HttpStatus.SUCCESS_CODE || responseCode == HttpStatus.SUCCESS_NO_RESPONSE_CODE;
    }
}
