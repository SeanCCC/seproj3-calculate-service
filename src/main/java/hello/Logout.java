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
        GreetingController.userToken="";
        this.id = 0;
        this.result = "Go to localhost:8084?token={token} to logout";
        logger.info("redir to oauth and clear buffer token");

    }

    public long getId() {
        return id;
    }

    public String getResult() {
        return result;
    }
}
