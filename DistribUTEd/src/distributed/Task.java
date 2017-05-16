
package distributed;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author dordonez@ute.edu.ec
 */
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public final String callerIp;
    public final int callerPort;
    public final String workerIp;
    public final int workerPort;
    public final String category;
    public final String id;
    public final String task;
    public final HashMap<String,String> taskExtraData;
    public boolean request = true;
    public String response;
    public HashMap<String,String> responseExtraData;

    public Task(String callerIp, int callerPort, String workerIp, int workerPort, String category, String id, String task, HashMap<String, String> taskExtraData) {
        this.callerIp = callerIp;
        this.callerPort = callerPort;
        this.workerIp = workerIp;
        this.workerPort = workerPort;
        this.category = category;
        this.id = id;
        this.task = task;
        this.taskExtraData = taskExtraData;
    }
    
}
