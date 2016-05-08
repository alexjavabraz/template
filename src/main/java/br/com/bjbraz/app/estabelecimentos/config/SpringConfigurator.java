package br.com.bjbraz.app.estabelecimentos.config;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * <p/>
 * Configure the Spring application context from files on standard locations,
 * based on a runnng mode<p/>
 * <p/>
 * First it reads all property files in the order below and makes the properties
 * available in the application context. The files in each directory are sorted
 * alphabetically. System properties override other property sources.<p/>
 * <ol>
 * <li>classpath*:META-INF/config/*.properties</li>
 * <li>classpath*:META-INF/config/MODE/*.properties</li>
 * <li>/WEB-INF/config/*.properties</li>
 * <li>/WEB-INF/config/MODE/*.properties</li>
 * </ol>
 * <p/>
 * Then it reads all xml files in the order below and makes the beans available
 * in the application context. The files in each directory are sorted
 * alphabetically.<p/>
 * <ol>
 * <li>classpath*:META-INF/config/*.xml</li>
 * <li>classpath*:META-INF/config/MODE/*.xml</li>
 * <li>/WEB-INF/config/*.xml</li>
 * <li>/WEB-INF/config/MODE/*.xml</li>
 * </ol>
 * @version $VERSION
 */
public class SpringConfigurator extends PropertyPlaceholderConfigurer implements ResourceLoaderAware {

    protected Logger logger = LoggerFactory.getLogger(SpringConfigurator.class);

    private static final String STANDARD_LOCATION_PREFIX = "classpath*:META-INF/config";
    private static final String STANDARD_WEB_LOCATION_PREFIX = "/WEB-INF/config";

    protected boolean inWebConteiner;

    protected ResourceLoader resourceLoader;

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;

        if (resourceLoader instanceof XmlWebApplicationContext) {
            Resource r = resourceLoader.getResource(STANDARD_WEB_LOCATION_PREFIX);
            if (r.exists()) {
                inWebConteiner = true;
            }
        }
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
    	try {
	        setSystemPropertiesMode(SYSTEM_PROPERTIES_MODE_OVERRIDE);
	
	        // define properties
	        setLocations(findProperties());
	
	        // define xml contexts
	        BeanDefinitionReader reader = new XmlBeanDefinitionReader((DefaultListableBeanFactory) factory);
	        reader.loadBeanDefinitions(findContexts());
	
	        super.postProcessBeanFactory(factory);
    	} catch (Exception ex) {
    		throw new IllegalStateException(ex);
    	}
    }

    protected Resource[] findProperties() {
        Resource[] resources = findStandardResources("properties");
        for (Resource r : resources) {
            logger.debug("Properties -> " + r);
        }
        return resources;
    }

    protected Resource[] findContexts() {
        Resource[] resources = findStandardResources("xml");
        for (Resource r : resources) {
            logger.debug("Context -> " + r);
        }
        return resources;
    }

    protected Resource[] findStandardResources(String extension) {
        Resource[] resources = findModeResources(STANDARD_LOCATION_PREFIX, extension);
        if (inWebConteiner) {
            Resource[] webResources = findModeResources(STANDARD_WEB_LOCATION_PREFIX, extension);
            resources = concat(resources, webResources);
        }
        return resources;
    }

    protected Resource[] findModeResources(String prefix, String extension) {
        Resource[] resources = findResources(prefix + "/*." + extension);
        Set<String> runningModes = RunningMode.get();
        for (String runningMode : runningModes) {
            Resource[] modeResources = findResources(prefix + "/" + runningMode + "/*." + extension);
            resources = concat(resources, modeResources);
        }
        return resources;
    }

    protected Resource[] findResources(String locationPattern) {
        ResourcePatternResolver resolver = getResolver();
        try {
            Resource[] resources = resolver.getResources(locationPattern);
            Arrays.sort(resources, new ResourceComparator());
            return resources;
        }
        catch (IOException e) {
            throw new BeanInitializationException("Error reading resources", e);
        }
    }

    @SuppressWarnings("unchecked")
    protected ResourcePatternResolver getResolver() {
        if (inWebConteiner) {
            try {
                Constructor ctor = ((Class) Class.forName("ServletContextResourcePatternResolver")).getConstructor();
                return (ResourcePatternResolver) ctor.newInstance(resourceLoader);
            }
            catch (Exception e) {
                // ignore
            }
        }
        return new PathMatchingResourcePatternResolver(resourceLoader);
    }

    protected Resource[] concat(Resource[] a1, Resource[] a2) {
        int l1 = a1.length;
        int l2 = a2.length;

        Resource[] r = new Resource[l1 + l2];

        System.arraycopy(a1, 0, r, 0, l1);
        System.arraycopy(a2, 0, r, l1, l2);

        return r;
    }

    protected final static class ResourceComparator implements Comparator<Resource> {

        protected static final String WEB_INF_CLASSES_META_INF_CONFIG = "/WEB-INF/classes/META-INF/config/";

        public int compare(Resource r1, Resource r2) {
            try {
                String name1 = r1.getURL().toExternalForm().replace('\\', '/');
                String name2 = r2.getURL().toExternalForm().replace('\\', '/');
                boolean webInfClasses1 = name1.indexOf(WEB_INF_CLASSES_META_INF_CONFIG) != -1;
                boolean webInfClasses2 = name2.indexOf(WEB_INF_CLASSES_META_INF_CONFIG) != -1;
                if (!webInfClasses1 && webInfClasses2) {
                    return -1;
                }
                if (webInfClasses1 && !webInfClasses2) {
                    return 1;
                }
            }
            catch (IOException io) {
                // ignore
            }

            return r1.getFilename().compareTo(r2.getFilename());
        }
    }

}
