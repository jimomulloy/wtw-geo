package uk.commonline.weather.geo.jaxrs;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import uk.commonline.weather.geo.service.GeoLocationManager;
import uk.commonline.weather.model.Location;
import uk.commonline.weather.model.Region;

/**
 * 
 */
@Path("/location")
@Component
// @Transactional
public class GeoLocationRestService /* implements GeoLocationService */{

    @Inject
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
    @Path("source/{source}/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Location getLocation(@PathParam("source") String source, @PathParam("id") String id) {
	Location l = geoGeoLocationManager.getLocation(source, id);
	return l;
    }

    @GET
    @Path("filter/{filter}/typecode/{typecode}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Location> findByType(@PathParam("filter") String filter, @PathParam("typecode") String typeCode) {

	List<Location> locations = geoGeoLocationManager.findByType(filter, typeCode);

	return locations;
    }

    @GET
    @Path("region/lat/{lat}/long/{long}")
    @Produces(MediaType.APPLICATION_JSON)
    public long getRegion(@PathParam("lat") double latitude, @PathParam("long") double longitude) {
	return geoGeoLocationManager.getRegion(latitude, longitude);
    }

    @GET
    @Path("locate/source/{source}/lat/{lat}/long/{long}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getLocationId(@PathParam("source") String source, @PathParam("lat") double latitude, @PathParam("long") double longitude) {
	return geoGeoLocationManager.getLocationId(source, latitude, longitude);
    }

    @GET
    @Path("regioninfo/{region}")
    @Produces(MediaType.APPLICATION_JSON)
    public Region getRegionInfo(@PathParam("region") long region) {
	return geoGeoLocationManager.getRegionInfo(region);
    }
}
