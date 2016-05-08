package br.com.bjbraz.app.estabelecimentos.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * Used to bootstrap the web application. It configures:
 * <ol>
 * <li>The running mode - it is set from the system property "runningMode" or
 * if it's not defined, from web.xml context-param named "runningMode"</li>
 * <li>Logback (using <code>LoggingConfigurator</code>)</li>
 * <li>Spring (using <code>classpath:META-INF/root-context.xml</code>)</li>
 * </ol>
 *
 * @version $VERSION
 */
public class ContextLoaderListener implements ServletContextListener {

    private ContextLoader contextLoader;
    private LoggingConfigurator loggingConfigurator;

    /**
     * Initialize the root web application context.
     */
    public void contextInitialized(ServletContextEvent event) {
        String displayName = event.getServletContext().getServletContextName();
        System.out.println("[[ Starting up " + displayName + " ]]");

        RunningMode.configure();

        loggingConfigurator = new LoggingConfigurator();
        loggingConfigurator.configure();

        Logger startup = LoggerFactory.getLogger("STARTUP");
        startup.info("================================================================");
        startup.info("  Starting system configuration...");
        startup.info("  Application Name    [" + displayName + "]");
        startup.info("  Application Version [" + Version.getApplicationVersion() + "]");
        startup.info("  Running mode        " + RunningMode.get());

        this.contextLoader = new CustomContextLoader();
        this.contextLoader.initWebApplicationContext(event.getServletContext());

        startup.info("  Application context configured");
        startup.info("================================================================");
    }

    /**
     * Close the root web application context.
     */
    public void contextDestroyed(ServletContextEvent event) {
        String displayName = event.getServletContext().getServletContextName();
        
        Logger shutdown = LoggerFactory.getLogger("SHUTDOWN"); 
        shutdown.info("================================================================");
        shutdown.info("  Stopping application:  [" + displayName + "]");
        if (this.contextLoader != null) {
            this.contextLoader.closeWebApplicationContext(event.getServletContext());
        }
        shutdown.info("  Application stopped");
        shutdown.info("Stopping logcontext and reseting all appenders...");
        try {
        	loggingConfigurator.destroy();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        shutdown.info("================================================================");
    }

    /**
     * Customizacao do ContextLoader para usar um configLocation nao padrao
     */
    private static final class CustomContextLoader extends ContextLoader {

        protected static final String DEFAULT_CONFIG_LOCATION = "classpath:META-INF/root-context.xml";

        protected void customizeContext(ServletContext servletContext,
            ConfigurableWebApplicationContext applicationContext) {
            String configLocation = DEFAULT_CONFIG_LOCATION;
            if (configLocation != null) {
                applicationContext.setConfigLocation(configLocation);
            }
        }
    }

}
