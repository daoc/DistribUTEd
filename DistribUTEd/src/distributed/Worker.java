
package distributed;

import java.io.File;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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
    private Config config;
    
    //private int numHilos = 5;//esto debe venir de otro lado
    
    private ScriptEngineManager engineManager = new ScriptEngineManager();
    private ScriptEngine engine = engineManager.getEngineByName("nashorn");
    
    public static void main(String[] args) {
        Worker worker = new Worker();
        worker.init(args);
        worker.eagerFetchTask();
    }    

    public void init(String[] args) {
        try {
            if(args.length == 0) {
                configFile = "config.xml";
            } else {
                configFile = args[0];
            }
            JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);           
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            config = (Config) jaxbUnmarshaller.unmarshal(new File(configFile));           
            
            if(config.numHilos <= 0) {
                config.numHilos = Runtime.getRuntime().availableProcessors();
            }
            
            System.out.println(config);
            
            pool = Executors.newFixedThreadPool(config.numHilos);
            ecs = new ExecutorCompletionService<>(pool);
            exec = new Scripting();
            exec.init(config);
        } catch (JAXBException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void eagerFetchTask() {
        try {
            for(int i = 0; i < 5; i++) {
                URL url = new URL(config.taskServerUrl);
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
                addTask(task);
            }
        } catch (Exception ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addTask(Task task) {
        try {
            Distributable dist = (Distributable) exec.invocable.invokeFunction("build", task);
            dist.configureForCall();
            ecs.submit(dist);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
