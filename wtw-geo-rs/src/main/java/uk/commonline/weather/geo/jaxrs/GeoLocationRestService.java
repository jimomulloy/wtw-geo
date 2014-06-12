package uk.commonline.weather.geo.jaxrs;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.commonline.weather.geo.service.GeoLocationManager;
import uk.commonline.weather.model.Location;

/**
 * 
 */
@Path("/location")
@Component
// @Transactional
public class GeoLocationRestService /* implements GeoLocationService */{

    @Autowired
    GeoLocationManager geoGeoLocationManager;

    protected GeoLocationManager getGeoLocationManager() {
	return geoGeoLocationManager;
    }

    public void setGeoLocationManager(GeoLocationManager geoLocationManager) {
	this.geoGeoLocationManager = geoLocationManager;
    }

    public Class<Location> getEiClass() {
	return Location.class;
    }

    @GET
    @Path("zip/{zip}")
    @Produces(MediaType.APPLICATION_JSON)
    public Location findByZip(@PathParam("zip") String zip) {

	Location l = geoGeoLocationManager.findByZip(zip);
	System.out.println("!!GeoLocationRestService after findbyzip");
	if (l == null) {
	    l = new Location();
	    l.setCity("London");
	}
	return l;
    }

    @GET
    @Path("town/{zip}")
    @Produces(MediaType.APPLICATION_JSON)
    public Location findByTown(@PathParam("town") String town) {
	Location l = geoGeoLocationManager.findByTown(town);
	if (l == null) {
	    l = new Location();
	    l.setCity("London");
	}
	return l;
    }

    @GET
    @Path("woeid/{woeid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Location findByWoeid(@PathParam("woeid") String woeid) {

	Location l = geoGeoLocationManager.findByWoeid(woeid);
	if (l == null) {
	    l = new Location();
	    l.setCity("London");
	}
	return l;
    }

    @GET
    @Path("filter/{filter}/typecode/{typecode}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Location> findAllByType(@PathParam("filter") String filter, @PathParam("typecode") String typeCode) {
	System.out.println("!!GeoLocationRestService findbyzip");

	List<Location> locations = geoGeoLocationManager.findAllByType(filter, typeCode);

	return locations;
    }
}
