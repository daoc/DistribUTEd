
package distributed;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dordonez@ute.edu.ec
 */
public class Director {
    private final TasksRepository tasksRep;
    
    public Director(TasksRepository tasksRep) {
        this.tasksRep = tasksRep;
    }
    
    public Director init() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8888), 0);
            server.createContext("/distributed/get", new GetHandler());
            //server.setExecutor(null); // creates a default executor
            server.start();
        } catch (IOException ex) {
            Logger.getLogger(Director.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this;
    }
    
}
