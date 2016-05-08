package br.com.bjbraz.app.estabelecimentos.config.jmx;

import java.util.Date;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

/**
 *
 * @version $VERSION
 */
@ManagedResource
public class ServiceStats {

    private Monitor monitor;
    
    public ServiceStats() {
        monitor = MonitorFactory.getMonitor("", "ms."); // get the same monitor used by MonitorAspect
    }
    
    public ServiceStats(String name) {
        monitor = MonitorFactory.getMonitor(name, "ms."); // get the same monitor used by MonitorAspect
    }

    @ManagedAttribute
    public double getHits() {
        return monitor.getHits();
    }

    @ManagedAttribute
    public double getLastValue() {
        return monitor.getLastValue();
    }

    @ManagedAttribute
    public double getMin() {
        return monitor.getMin();
    }

    @ManagedAttribute
    public double getMax() {
        return monitor.getMax();
    }

    @ManagedAttribute
    public double getAvg() {
        return monitor.getAvg();
    }

    @ManagedAttribute
    public double getTotal() {
        return monitor.getTotal();
    }

    @ManagedAttribute
    public double getStdDev() {
        return monitor.getStdDev();
    }

    @ManagedAttribute
    public Date getFirstAccess() {
        return monitor.getFirstAccess();
    }

    @ManagedAttribute
    public Date getLastAccess() {
        return monitor.getLastAccess();
    }

}
