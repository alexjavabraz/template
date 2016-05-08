package br.com.bjbraz.app.estabelecimentos.config;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.DisposableBean;

import br.com.bjbraz.app.estabelecimentos.config.RunningMode;

import ch.qos.logback.classic.BasicConfigurator;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * Configure log4j from standard file locations, based on a running mode. 
 * Looks for a Logback configuration file in the order below:
 * <ol>
 * <li>logging.xml</li>
 * <li>logging-MODE.xml</li>
 * </ol>
 * Both files are used and the configuration is merged.
 * @version $VERSION
 */
public class LoggingConfigurator implements DisposableBean {

    private static final String LOG_CONFIG_PREFIX = "/META-INF/log/";
    private static final String LOG_CONFIG_NAME = "logback";

    private static final String STANDARD_LOGGER_NAME = "STANDARD_APPLICATION_LOG_FILE";

    private LoggerContext loggerContext;

    public LoggingConfigurator() {
        loggerContext = (LoggerContext) org.slf4j.LoggerFactory.getILoggerFactory();
        if (loggerContext.isStarted()) { 
        	loggerContext.stop();
        	loggerContext.reset();
        }
        loggerContext.start();
    }

    public void configure() {

        // Se BASE_LOG_PATH nao estiver definida, usamos o diretorio corrente como default
        String baseLogPath = System.getProperty("BASE_LOG_PATH");
        if (baseLogPath == null) {
            System.setProperty("BASE_LOG_PATH", ".");
        }

        // Search for configuration files
        List<URL> resources = new ArrayList<URL>();
        findResource(LOG_CONFIG_NAME, resources);
        Set<String> runningModes = RunningMode.get();
        for (String rm : runningModes) {
            findResource(LOG_CONFIG_NAME + "-" + rm, resources);
        }

        // Read configuration files
        if (resources.size() > 0) {
            for (URL r : resources) {
                System.out.println("Logging configuration: [" + r.getFile()
                    + "]");
                configure(r);
            }
            reportStandardAppender();
        }
        else {
            System.out.println("Logging configuration: [BasicConfigurator]");
            BasicConfigurator.configureDefaultContext();
        }

    }

    @SuppressWarnings("unchecked")
    protected void reportStandardAppender() {
        Logger root = loggerContext.getLogger("root");
        try {
            FileAppender appender = (FileAppender) root.getAppender(STANDARD_LOGGER_NAME);
            System.out.println("Standard log output:   [" + appender.getFile() + "]");
        }
        catch (Exception e) {
            System.out.println("WARNING: Can't get standard log Appender");
        }
    }

    protected void findResource(String fileName, List<URL> resources) {
        String file = LOG_CONFIG_PREFIX + fileName + ".xml";
        URL url = getClass().getResource(file);
        if (url != null) {
            resources.add(url);
        }
    }

    protected void configure(URL url) {
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(loggerContext);
        try {
            configurator.doConfigure(url);
        }
        catch (JoranException e) {
            System.out.println("ERROR: Can't configure logging");
            StatusPrinter.print(loggerContext);
        }
    }

    public void destroy() throws Exception {
    	if (loggerContext.isStarted()) {
    		loggerContext.stop();
        	loggerContext.reset();
    	}
    }
}
