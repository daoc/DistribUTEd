/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributed;

import java.io.File;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author dordonez@ute.edu.ec
 */
public class Worker {
    private ExecutorService pool;
    private ExecutorCompletionService<Distributable> ecs;
    private Scripting exec;
    private String configFile;
    
    private int numHilos = 5;//esto debe venir de otro lado
    
    private ScriptEngineManager engineManager = new ScriptEngineManager();
    private ScriptEngine engine = engineManager.getEngineByName("nashorn");
    
    public static void main(String[] args) throws Exception {
        Worker worker = new Worker();
        worker.init(args);
        
        
        URL url = new URL("http://localhost:8888/distributed/task");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("GET");
        http.setRequestProperty("Accept", "application/octet-stream");
        http.setDoInput(true);
        //http.setDoOutput(true);
        http.connect();
        System.out.println(http.getResponseCode() + " / Task: " + http.getHeaderField("Task"));
        
        ObjectInputStream ois = new ObjectInputStream(http.getInputStream());
        Task task = (Task) ois.readObject();
        ois.close();
        http.disconnect();
        
        System.out.println(task.task);
        worker.addTask(task);
    }    

    public void init(String[] args) {
        try {
            if(args == null) {
                configFile = "config.xml";
            } else {
                configFile = args[0];
            }
            JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);           
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Config config = (Config) jaxbUnmarshaller.unmarshal(new File(configFile));
            
            
            pool = Executors.newFixedThreadPool(numHilos);
            ecs = new ExecutorCompletionService<>(pool);
            exec = new Scripting();
            exec.init();
        } catch (JAXBException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addTask(Task task) {
        try {
            String script = "";
            script += "load('nashorn:mozilla_compat.js'); importPackage(Packages.daoc.age); importPackage(Packages.daoc.age.ejemplos.sudoku); ";
            script += "var build = function(task) { ";
            script += "obj = new Poblacion(task);";
            script += "return obj;}";
            engine.eval(script);
            Invocable invocable = (Invocable) engine;
            Distributable dist = (Distributable) invocable.invokeFunction("build", task);
            dist.configureForCall();
            ecs.submit(dist);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
