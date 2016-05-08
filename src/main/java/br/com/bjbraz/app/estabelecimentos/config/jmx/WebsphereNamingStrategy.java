package br.com.bjbraz.app.estabelecimentos.config.jmx;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.springframework.jmx.export.naming.ObjectNamingStrategy;
import org.springframework.jmx.support.ObjectNameManager;
import org.springframework.util.ClassUtils;

/**
 *
 * @version $VERSION
 */
public class WebsphereNamingStrategy implements ObjectNamingStrategy {

    private String domainName;
    private String cellName;
    private String nodeName;
    private String processName;

    public ObjectName getObjectName(Object object, String name) throws MalformedObjectNameException {
        StringBuffer objectName = new StringBuffer();
        objectName.append(domainName);
        objectName.append(":type=");
        objectName.append(ClassUtils.getShortName(object.getClass()));
        objectName.append(",name=");
        objectName.append(name);
        objectName.append(",cell=");
        objectName.append(cellName);
        objectName.append(",node=");
        objectName.append(nodeName);
        objectName.append(",process=");
        objectName.append(processName);

        return ObjectNameManager.getInstance(objectName.toString());
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getCellName() {
        return cellName;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

}