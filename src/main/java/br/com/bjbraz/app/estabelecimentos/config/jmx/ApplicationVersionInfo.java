package br.com.bjbraz.app.estabelecimentos.config.jmx;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import br.com.bjbraz.app.estabelecimentos.config.Version;

/**
 *
 * @version $VERSION
 */
@ManagedResource
public class ApplicationVersionInfo {

    private String title = Version.getApplicationVersion().getTitle();
    private String name = Version.getApplicationVersion().getName();
    private String pkg = Version.getApplicationVersion().getPackage();
    private String version = Version.getApplicationVersion().getVersion();
    private String vendor = Version.getApplicationVersion().getVendor();

    @ManagedAttribute
    public String getTitle() {
        return title;
    }

    @ManagedAttribute
    public String getName() {
        return name;
    }

    @ManagedAttribute
    public String getPkg() {
        return pkg;
    }

    @ManagedAttribute
    public String getVersion() {
        return version;
    }

    @ManagedAttribute
    public String getVendor() {
        return vendor;
    }

}