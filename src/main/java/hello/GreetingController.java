package hello;

import java.io.*;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private final AtomicLong counter = new AtomicLong();
    public static boolean login = false;

    @RequestMapping("/infixEval")
    public Greeting greeting(@RequestParam(value="exp", defaultValue="-1") String exp) throws IOException {
        long cnt = counter.incrementAndGet();
        if(login) return new Greeting(cnt, exp);
        else return new Greeting(-1, exp);
    }

    @RequestMapping("/login")
    public Login login(@RequestParam(value="id", defaultValue="") String idf, @RequestParam(value="pwd", defaultValue="") String pwd) throws IOException {
        return new Login(idf, pwd);
    }

    @RequestMapping("/logout")
    public Logout logout() throws IOException {
        return new Logout();
    }

}
