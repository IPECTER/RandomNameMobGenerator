package com.github.ipecter.randomnamemobgenerator;

import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Scanner;

public class RandomNickNameGenerator {

    public final static String getRandomNickName(){
        try {
            URL url = new URL("https://nickname.hwanmoo.kr/?format=text&count=1");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            InputStream stream = connection.getInputStream();
            String response = new Scanner(stream, Charset.forName("UTF-8")).useDelimiter("\\A").next();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }

}
