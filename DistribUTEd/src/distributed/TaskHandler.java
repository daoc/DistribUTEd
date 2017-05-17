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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author dordonez@ute.edu.ec
 */
public class TaskHandler implements HttpHandler {

    public TaskHandler() {
    }

    @Override
    public void handle(HttpExchange hEx) throws IOException {
        //https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpExchange.html
        //https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/package-summary.html
        
        switch(hEx.getRequestMethod()) {
            case "GET":
                System.out.println("GET");
                break;
            case "POST":
                System.out.println("POST");
                break;
            case "PUT":
                System.out.println("PUT");
                break;
            case "OPTIONS":
                System.out.println("OPTIONS");
                break;               
            default:
                System.out.println("NO soportado");
        }
        
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
    
    public void doGet(HttpExchange hEx) {
        try {
            Task task = getTask(hEx);
            
            hEx.getResponseHeaders().add("Task", "OK");
            hEx.sendResponseHeaders(200, 0);
        } catch (IOException ex) {
            Logger.getLogger(TaskHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Task getTask(HttpExchange hEx) {
        String inst = ".setAtributos(9*9, 1, 9).setMetaAptitud(0).setMaxTiempoCalculo(0, 4, 0).setNumIndividuos(100000).setParamReset(10, 25).setMetodoGeneracion(new GeneracionRestringida(new RestriccionSudoku(Sudoku.leeArchivoSudokus(\"0.txt\").get(0)))).setFuncionAptitud(new FuncionAptitudSudoku())";
        Task task = new Task(hEx.getLocalAddress().getHostName(), hEx.getLocalAddress().getPort(), 
                hEx.getRemoteAddress().getHostName(), hEx.getRemoteAddress().getPort(),
                "0.txt", "0", inst, null);
        return task;
    }
    
}
