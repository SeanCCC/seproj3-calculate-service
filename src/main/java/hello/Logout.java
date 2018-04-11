package hello;

import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

public class Logout {
    private final long id;
    private final String result;
    private static Logger logger = Logger.getLogger(Greeting.class.getName());

    public Logout () throws IOException {
        FileHandler fileHandler = new FileHandler("./logout_log.log");
        fileHandler.setLevel(Level.INFO); //Log的層級
        logger.addHandler(fileHandler);
//        System.out.println(""+idf+" "+pwd);

        if (GreetingController.login){
            GreetingController.login = false;
            this.id = 1;
            this.result = "logged out";
            logger.info("logged out");
        }
        else {
            this.id = 0;
            this.result = "ask to log out while not logged in";
            logger.info("ask to log out while not logged in");
        }

    }

    public long getId() {
        return id;
    }

    public String getResult() {
        return result;
    }
}
