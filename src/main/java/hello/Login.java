package hello;

import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.net.*;
import org.json.*;

public class Login {
    private final long id;
    private final String result;
    private static Logger logger = Logger.getLogger(Greeting.class.getName());

    private int sendGet(String token) throws IOException {

        String url = "http://localhost:8084/valid?token="+token;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());
        JSONObject json = new JSONObject(response.toString());
        return json.getInt("rvl");
    }

    public Login (String token) throws IOException {
        FileHandler fileHandler = new FileHandler("./login_log.log");
        fileHandler.setLevel(Level.INFO); //Log的層級
        logger.addHandler(fileHandler);
//        System.out.println(""+idf+" "+pwd);
        int valid_res = sendGet(token);
        if(valid_res == 0){
            this.id=0;
            this.result="login failed; token missing or validation failed, Plz go to localhost:8084?id={id}&pwd={pwd} for token, and then come back with param token={token}";
            logger.info("login failed; token missing or validation failed");
        }
        else {
            this.id=1;
            this.result="login success";
            GreetingController.userToken=token;
            logger.info("login success with token "+token);
        }
    }

    public long getId() {
        return id;
    }

    public String getResult() {
        return result;
    }
}
