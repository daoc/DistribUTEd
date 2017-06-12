
package distribute.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author dordonez@ute.edu.ec
 */
public class Boot {
    public static void main(String [] args) throws IOException {
        
        FileWriter f = new FileWriter("C:\\Users\\Diego\\Desktop\\BootLog.txt", true);
        //PrintWriter p = new PrintWriter(f, true);
        f.write("ARRANCADA POR BOOTSTRAPPING: " + (args.length > 0 ? args[0] : "NOARGS") + "\n");
        f.flush();
    }
}
