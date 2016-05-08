package br.com.bjbraz.app.estabelecimentos.config.jmx;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import br.com.bjbraz.app.estabelecimentos.config.Version;

/**
 *
 * @version $VERSION
 */
@ManagedResource
public class FrameworkVersionInfo {

    private String title = Version.getFrameworkVersion().getTitle();
    private String name = Version.getFrameworkVersion().getName();
    private String pkg = Version.getFrameworkVersion().getPackage();
    private String version = Version.getFrameworkVersion().getVersion();
    private String vendor = Version.getFrameworkVersion().getVendor();

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
