package br.com.bjbraz.app.estabelecimentos.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;


/**
 *
 * @version $VERSION
 */
public class Version {
    private static Logger logger = LoggerFactory.getLogger(Version.class);

    private static final String TITLE_UNDEFINED = "TITLE_UNDEFINED";
    private static final String NAME_UNDEFINED = "NAME_UNDEFINED";
    private static final String PACKAGE_UNDEFINED = "PACKAGE_UNDEFINED";
    private static final String VERSION_UNDEFINED = "VERSION_UNDEFINED";
    private static final String VENDOR_UNDEFINED = "VENDOR_UNDEFINED";

    private static Version frameworkVersion;
    private static Version applicationVersion;

    static {
        frameworkVersion = new Version("framework");
        applicationVersion = new Version("application");
    }

    public static Version getFrameworkVersion() {
        return frameworkVersion;
    }

    public static Version getApplicationVersion() {
        return applicationVersion;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private String title = TITLE_UNDEFINED;
    private String name = NAME_UNDEFINED;
    private String pkg = PACKAGE_UNDEFINED;
    private String version = VERSION_UNDEFINED;
    private String vendor = VENDOR_UNDEFINED;

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }

    public String getPackage() {
        return pkg;
    }

    public String getVersion() {
        return version;
    }

    public String getVendor() {
        return vendor;
    }

    public String toString() {
        return name + ": " + version;
    }

    public Version(String baseName) {
        readProperties(baseName);
    }

    protected void readProperties(String baseName) {
        String fileName = "classpath:META-INF/config/" + baseName + ".properties";
        Resource res = new DefaultResourceLoader().getResource(fileName);

        InputStream inputStream;
        try {
            inputStream = res.getInputStream();
            if (inputStream != null) {
                Properties p = new Properties();
                p.load(inputStream);

                title = p.getProperty(baseName + ".title", TITLE_UNDEFINED);
                name = p.getProperty(baseName + ".name", NAME_UNDEFINED);
                pkg = p.getProperty(baseName + ".package", PACKAGE_UNDEFINED);
                version = p.getProperty(baseName + ".version", VERSION_UNDEFINED);
                vendor = p.getProperty(baseName + ".vendor", VENDOR_UNDEFINED);

                inputStream.close();
            }
        }
        catch (IOException e) {
            logger.error("Can't read configuration file: " + fileName, e);
        }
    }

}
