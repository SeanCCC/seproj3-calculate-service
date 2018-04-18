package hello;

import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;


public class Greeting {
    // test 1
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

    static int getOrder(char input){
        switch(input){
            case '*': case '/':
                return 2;
            case '+': case '-':
                return 1;
            default:
                return 0;
        }
    }

    public static int op(int opr, int lhs, int rhs){
        switch(opr){
            case 43:
                return lhs+rhs;
            case 45:
                return lhs-rhs;
            case 42:
                return lhs*rhs;
            case 47:
                return lhs/rhs;
            default:
                return -1;
        }
    }

    static int eval(Integer[] postfix){
        int i=0;
        Stack<Integer> st = new Stack<Integer>();
        for(i=0;i<postfix.length;i++){
            switch(postfix[i]){
                case 42: case 43: case 47: case 45:
                    int rhs = st.pop();
                    int lhs = st.pop();
                    st.push(op(postfix[i],lhs,rhs));
                    break;
                default:
                    st.push(postfix[i]*-1);
                    break;
            }
        }
        return st.pop();
    }

    static Stack<Integer> infix2postfix(char[] infix){
        Stack<Character> st = new Stack<Character>();
        Stack<Integer> postfix = new Stack<Integer>();
        int i,cvTmp;
        // System.out.println("flag1");
        for(i=0;i<infix.length;i++){
            // System.out.println("handling char "+infix[i]);
            switch(infix[i]) {
                case '(':
                    st.push(infix[i]);
                    break;
                case '+': case '-': case '*': case '/':
                    // System.out.println("fa"+" "+infix[i]);
                    while(!st.empty() && getOrder(st.peek()) >= getOrder(infix[i])){
                        cvTmp = st.pop();
                        postfix.push(cvTmp);
                    }
                    st.push(infix[i]);
                    break;
                case ')':
                    while(st.peek() != '(') {
                        cvTmp = st.pop();
                        postfix.push(cvTmp);
                    }
                    st.pop();
                    break;
                default:
                    int numCnt = 0;
                    while( i+numCnt+1<infix.length && Character.isDigit(infix[i+numCnt+1])){
                        numCnt++;
                    }
                    cvTmp = Integer.parseInt(new String(infix).substring(i, i+numCnt+1));
                    i=i+numCnt;
                    postfix.push(cvTmp*-1);
            }
        }
        // System.out.println("flag2");
        while(!st.empty()){
            cvTmp = st.pop();
            postfix.push(cvTmp);
        }
        return postfix;
    }

    static int solver(String input){
        Stack<Integer> postfix;
        Vector postfixVec = new Vector<Integer>();
        Stack<Integer> tmp = new Stack<Integer>();
        char[] charArray = input.toCharArray();
        postfix = infix2postfix(charArray);
        while(!postfix.empty()){
            tmp.push(postfix.pop());
        }
        while(!tmp.empty()){
            postfixVec.addElement(tmp.pop());
        }
        Integer[] a = new Integer[postfixVec.size()];
        postfixVec.toArray(a);
        return eval(a);
    }

    public Greeting(long id, String content) throws IOException {
        FileHandler fileHandler = new FileHandler("./logging.log");
        fileHandler.setLevel(Level.INFO); //Log的層級
        logger.addHandler(fileHandler);
        logger.info("received expression:"+content);

        int valid_result = sendGet(GreetingController.userToken);
        if(valid_result==1) {
            this.id=1;
        }
        else{
            this.id=-1;
        }
        if(this.id >= 0) this.result = content+"="+solver(content);
        else this.result = "Please login first";
        logger.info("reporting the result:"+this.result);
    }

    public long getId() {
        return id;
    }

    public String getResult() {
        return result;
    }

}
