package hello;

import java.io.*;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private final AtomicLong counter = new AtomicLong();
    public static String userToken="";

    @RequestMapping("/infixEval")
    public Greeting greeting(@RequestParam(value="exp", defaultValue="-1") String exp) throws IOException {
        long cnt = counter.incrementAndGet();
        return new Greeting(cnt, exp);
    }

    @RequestMapping("/login")
    public Login login(@RequestParam(value="token", defaultValue="") String token) throws IOException {
        return new Login(token);
    }

    @RequestMapping("/logout")
    public Logout logout() throws IOException {
        return new Logout();
    }

}
