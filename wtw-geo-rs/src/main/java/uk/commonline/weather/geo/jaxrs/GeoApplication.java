package uk.commonline.weather.geo.jaxrs;

import java.util.logging.Logger;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("webresources")
public class GeoApplication extends ResourceConfig {
    public GeoApplication() {
        packages("uk.commonline.weather.geo.jaxrs;uk.commonline.weather.model;org.codehaus.jackson.jaxrs");

        // Enable LoggingFilter & output entity.     
        registerInstances(new LoggingFilter(Logger.getLogger(GeoApplication.class.getName()), true));
 
    }
}