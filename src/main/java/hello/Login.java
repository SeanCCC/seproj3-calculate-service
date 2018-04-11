package hello;

import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

public class Login {
    private final long id;
    private final String result;
    private static Logger logger = Logger.getLogger(Greeting.class.getName());

    public Login (String idf, String pwd) throws IOException {
        FileHandler fileHandler = new FileHandler("./login_log.log");
        fileHandler.setLevel(Level.INFO); //Log的層級
        logger.addHandler(fileHandler);
//        System.out.println(""+idf+" "+pwd);
        if(idf.equals("test") && pwd.equals("test")) {
            this.id = 1;
            this.result = "Logged in";
            GreetingController.login = true;
            logger.info("login success");
        }
        else {
            this.id = 0;
            this.result = "Wrong id or password";
            logger.info("login failed");
        }
    }

    public long getId() {
        return id;
    }

    public String getResult() {
        return result;
    }
}
