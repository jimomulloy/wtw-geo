package uk.commonline.weather.geo.jaxrs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = { GeoLocationRestService.class })
public class SpringConfig {
}