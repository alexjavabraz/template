package br.com.bjbraz.app.estabelecimentos.config.jmx;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.management.JMException;
import javax.management.ObjectName;
import javax.management.modelmbean.ModelMBean;

import org.slf4j.Logger;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.jmx.export.MBeanExportException;
import org.springframework.jmx.export.SpringModelMBean;
import org.springframework.jmx.export.annotation.AnnotationJmxAttributeSource;
import org.springframework.jmx.export.assembler.MetadataMBeanInfoAssembler;
import org.springframework.jmx.export.naming.ObjectNamingStrategy;
import org.springframework.jmx.support.JmxUtils;
import org.springframework.jmx.support.MBeanRegistrationSupport;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

/**
 *
 * @version $VERSION
 */
@SuppressWarnings("unchecked")
public class ServiceMBeanExporter extends MBeanRegistrationSupport implements BeanFactoryAware, InitializingBean,
    DisposableBean {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(ServiceMBeanExporter.class);

    private static final String SERVICE_SUFFIX = "ServiceImpl";

    private DefaultListableBeanFactory beanFactory;

    private final AnnotationJmxAttributeSource annotationSource = new AnnotationJmxAttributeSource();
    private final MetadataMBeanInfoAssembler metadataAssembler = new MetadataMBeanInfoAssembler(this.annotationSource);
    private ObjectNamingStrategy namingStrategy;

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (beanFactory instanceof ListableBeanFactory) {
            this.beanFactory = (DefaultListableBeanFactory) beanFactory;
        }
        else {
            logger.info("MBeanExporter not running in a ListableBeanFactory: Autodetection of MBeans not available.");
        }
    }

    public ObjectNamingStrategy getNamingStrategy() {
        return namingStrategy;
    }

    public void setNamingStrategy(ObjectNamingStrategy namingStrategy) {
        this.namingStrategy = namingStrategy;
    }

    public void afterPropertiesSet() {
        if (this.server == null) {
            this.server = JmxUtils.locateMBeanServer();
        }

        Map beans = autodetectServices();

        if (!beans.isEmpty()) {
            for (Object o : beans.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                Assert.notNull(entry.getKey(), "Beans key must not be null");

                registerService(entry.getValue(), (String) entry.getKey());
            }
        }
    }

    public void destroy() {
        logger.info("Unregistering JMX-exposed beans on shutdown");
        unregisterBeans();
    }

    
    private Map autodetectServices() {
        Map beans = new HashMap();

        String[] beanNames = this.beanFactory.getBeanNamesForType(Object.class, true, false);
        for (String beanName : beanNames) {
            Class beanClass = this.beanFactory.getType(beanName);
            if (beanClass != null && isServiceImpl(beanClass, beanName)) {
                Object beanInstance = this.beanFactory.getBean(beanName);
                if (!beans.containsValue(beanName)
                    && (beanInstance == null || !CollectionUtils.containsInstance(beans.values(), beanInstance))) {
                    beans.put(beanName, (beanInstance != null ? beanInstance : beanName));
                }
            }
        }

        return beans;
    }

    private boolean isServiceImpl(Class beanClass, String beanName) {
        return beanName.endsWith(SERVICE_SUFFIX);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // ////////////////////

    private Class findServiceInterface(Class targetClass) {
        // a.b.FooServiceImpl must implement interface a.b.FooService
        String className = targetClass.getName();
        if (className.endsWith(SERVICE_SUFFIX)) {

            String interfaceName = className.substring(0, className.length() - 4);
            Class[] interfaces = targetClass.getInterfaces();
            for (Class i : interfaces) {
                if (i.getName().equals(interfaceName)) {
                    return i;
                }
            }

            // Tenta achar interface no pacote pai
            int x = interfaceName.lastIndexOf('.');
            int y = interfaceName.substring(0, x).lastIndexOf('.');
            interfaceName = interfaceName.substring(0, y) + interfaceName.substring(x);
            interfaces = targetClass.getInterfaces();
            for (Class i : interfaces) {
                if (i.getName().equals(interfaceName)) {
                    return i;
                }
            }
        }
        return null;
    }

    /**
     * Registra um MBean para cada metodo do servico
     */
    private void registerService(Object bean, String beanKey) {
        Class targetClass = AopUtils.getTargetClass(bean);
        Class serviceInterface = findServiceInterface(targetClass);
        if (serviceInterface != null) {
            logger.info("Service '" + beanKey + "' has been autodetected for JMX interface monitoring");
            Method[] methods = serviceInterface.getDeclaredMethods();
            for (Method method : methods) {
                StringBuilder fullMethodName = new StringBuilder(targetClass.getName() + "." + method.getName());

                for (Class classType : method.getParameterTypes()) {
                	fullMethodName.append("-");
                	fullMethodName.append(ClassUtils.getShortName(classType));
                }
                
                Object monitor = new ServiceStats(fullMethodName.toString());
                try {
                    registerMBean(monitor, fullMethodName.toString());
                }
                catch (JMException e) {
                    logger.warn("Error registering MBean: " + e.getMessage());
                }
            }
        }
    }

    private void registerMBean(Object bean, String beanKey) throws JMException {
        ObjectName objectName = this.namingStrategy.getObjectName(bean, beanKey);
        ModelMBean mbean = createAndConfigureMBean(bean, beanKey);
        doRegister(mbean, objectName);
        if (logger.isInfoEnabled()) {
            logger.debug("Created managed bean '" + beanKey + "': registering with JMX server as MBean [" + objectName
                + "]");
        }
    }

    protected ModelMBean createAndConfigureMBean(Object managedResource, String beanKey) throws MBeanExportException {
        try {
            ModelMBean mbean = new SpringModelMBean();
            mbean.setModelMBeanInfo(this.metadataAssembler.getMBeanInfo(managedResource, beanKey));
            mbean.setManagedResource(managedResource, "ObjectReference");
            return mbean;
        }
        catch (Exception ex) {
            throw new MBeanExportException("Could not create ModelMBean for managed resource [" + managedResource
                + "] with key '" + beanKey + "'", ex);
        }
    }

}
