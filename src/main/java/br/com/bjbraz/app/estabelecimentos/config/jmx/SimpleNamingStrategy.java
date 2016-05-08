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
public class SimpleNamingStrategy implements ObjectNamingStrategy {

    private String domainName;

    public ObjectName getObjectName(Object object, String name) throws MalformedObjectNameException {
        StringBuffer objectName = new StringBuffer();
        objectName.append(domainName);
        objectName.append(":type=");
        objectName.append(ClassUtils.getShortName(object.getClass()));
        objectName.append(",name=");
        objectName.append(name);
        return ObjectNameManager.getInstance(objectName.toString());
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

}