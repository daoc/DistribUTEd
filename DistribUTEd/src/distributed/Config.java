
package distributed;

import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dordonez@ute.edu.ec
 */
@XmlRootElement
public class Config {
    @XmlElementWrapper(name = "packages")
    public List<String> impPackage;
    
    public String distImp;
    
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("distImp: ");
        buffer.append(distImp);
        buffer.append(" packages: ");
        buffer.append(impPackage.stream().collect(Collectors.joining(", ")));
        return buffer.toString();
    }
}
