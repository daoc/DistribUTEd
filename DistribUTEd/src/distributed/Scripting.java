
package distributed;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author dordonez@ute.edu.ec
 */
public class Scripting {

    public final ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
    public final Invocable invocable = (Invocable) engine;
    
    public void init() {
        String importPackage = "importPackage(Packages.%s);";
        String buildFunction = "";
        buildFunction += "var build = function(task) { ";
        buildFunction += "obj = new %s(task);";
        buildFunction += "return obj;}";
        try {
            engine.eval("load('nashorn:mozilla_compat.js');");
            
        } catch (ScriptException ex) {
            Logger.getLogger(Scripting.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadScript(String script) {
        
    }
    
}
