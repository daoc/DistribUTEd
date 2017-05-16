/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.stream.Collectors;

/**
 *
 * @author dordonez@ute.edu.ec
 */
public class GetHandler implements HttpHandler {

    public GetHandler() {
    }

    @Override
    public void handle(HttpExchange hEx) throws IOException {
        //https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpExchange.html
        //https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/package-summary.html
        
        String sLog = "";
        sLog += hEx.getRequestMethod();
        sLog += hEx.getRequestHeaders().keySet().stream().collect(Collectors.joining(", "));
        //sLog += hEx.getRequestHeaders().values().stream().collect(Collectors.joining(", "));
        System.out.println(sLog);
        /*
        InputStream is = hEx.getRequestBody();
        //read(is); // .. read the request body
        String response = "This is the response";
        hEx.sendResponseHeaders(200, response.length());
        OutputStream os = hEx.getResponseBody();
        os.write(response.getBytes());
        os.close();
        */
    }
    
}
