
package distributed;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author dordonez@ute.edu.ec
 */
public class Bootstrap {
    public static void main(String[] args) throws IOException {
        String jar = "C:\\Users\\diego\\GitHub\\Varios\\DistribUTEd\\DistribUTEd\\dist\\DistribUTEd.jar";
        String cp = "java -cp %s distribute.test.Boot PRUEBA";
        Process pro = Runtime.getRuntime().exec(String.format(cp, jar));
        Scanner scan = new Scanner(pro.getErrorStream());
        while(scan.hasNextLine()) System.out.println(scan.nextLine());
        
        System.out.println("LISTO!!!");
        
    }
}
