package br.com.bjbraz.app.estabelecimentos.config;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

import org.springframework.util.ClassUtils;

/**
 *
 * @version $VERSION
 */
public class RunningMode {

    private static Set<String> modes;
    private static String environment;
    
    public static final String WEBSPHERE = "websphere";
    public static final String TOMCAT = "tomcat";
    public static final String GERONIMO = "geronimo";
    public static final String BATCH = "batch";
    public static final String UNKNOWN = "unknown";
    public static boolean configured;

    public static Set<String> get() {
        return Collections.unmodifiableSet(modes);
    }

    public static void configure(String defaultEnvironment) {
        environment=defaultEnvironment;
        configure();
    }
    
    public static void configure() {
        if (modes != null) {
            System.out.println("WARNING: Running modes already defined");
            return;
        }

        modes = new LinkedHashSet<String>();

        // runningMode do tipo de servidor
        modes.add(detectServerFamily());

        // runningMode definido externamente
        try {
            String systemRunningModes = System.getProperty("runningMode", null);
            
            if(systemRunningModes == null){
            	systemRunningModes = "dev";
            }
            
            parse(systemRunningModes, modes);
        }
        catch (SecurityException e) {
            System.out.println("WARNING: Can't read system property \"runningMode\": " + e);
        }

        configured = true;
    }

    private static void parse(String string, Set<String> list) {
        if (string == null) {
            return;
        }

        Scanner sc = new Scanner(string).useDelimiter(",");
        while (sc.hasNext()) {
            String s = sc.next().trim();
            if (!s.equals("")) {
                list.add(s);
            }
        }
    }

    private static String detectServerFamily() {
        if (ClassUtils.isPresent("com.ibm.wsspi.uow.UOWManager", ClassUtils.getDefaultClassLoader())) {
            return WEBSPHERE;
        }
        else if (ClassUtils.isPresent("org.apache.catalina.startup.Bootstrap", ClassUtils.getDefaultClassLoader())) {
            return TOMCAT;
        }
        else if (ClassUtils.isPresent("org.apache.geronimo.kernel.Kernel", ClassUtils.getDefaultClassLoader())) {
            return GERONIMO;
        }
        else if (environment != null) {
            if (environment.equalsIgnoreCase(BATCH)) {
                return BATCH;
            }
        }
        return UNKNOWN;
    }

    public static boolean isConfigured() {
        return configured;
    }
}
