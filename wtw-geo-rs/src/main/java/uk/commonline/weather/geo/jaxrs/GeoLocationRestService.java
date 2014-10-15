package uk.commonline.weather.geo.jaxrs;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import uk.commonline.weather.geo.service.GeoLocationService;
import uk.commonline.weather.model.Location;
import uk.commonline.weather.model.Region;

/**
 * @author Jim O'Mulloy
 * 
 *         WTW Geo Location JAXRS Service
 *
 */
@Path("/location")
@Component
public class GeoLocationRestService {

    @Inject
    GeoLocationService geoGeoLocationService;

    @GET
    @Path("filter/{filter}/typecode/{typecode}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Location> findByType(@PathParam("filter") String filter, @PathParam("typecode") String typeCode) {

        List<Location> locations = geoGeoLocationService.findByType(filter, typeCode);

        return locations;
    }

    public Class<Location> getEiClass() {
        return Location.class;
    }

    protected GeoLocationService getGeoLocationService() {
        return geoGeoLocationService;
    }

    @GET
    @Path("source/{source}/id/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Location getLocation(@PathParam("source") String source, @PathParam("id") String id) {
        Location l = geoGeoLocationService.getLocation(source, id);
        return l;
    }

    @GET
    @Path("locate/source/{source}/lat/{lat}/long/{long}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getLocationId(@PathParam("source") String source, @PathParam("lat") double latitude, @PathParam("long") double longitude) {
        return geoGeoLocationService.getLocationId(source, latitude, longitude);
    }

    @GET
    @Path("region/lat/{lat}/long/{long}")
    @Produces(MediaType.APPLICATION_JSON)
    public long getRegion(@PathParam("lat") double latitude, @PathParam("long") double longitude) {
        return geoGeoLocationService.getRegion(latitude, longitude);
    }

    @GET
    @Path("regions/swlat/{swlat}/swlong/{swlong}/nelat/{nelat}/nelong/{nelong}")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Region> getRegion(@PathParam("swlat") double swlatitude, @PathParam("swlong") double swlongitude,
            @PathParam("nelat") double nelatitude, @PathParam("nelong") double nelongitude) {
        return geoGeoLocationService.getRegions(swlatitude, swlongitude, nelatitude, nelongitude);
    }

    @GET
    @Path("regioninfo/{region}")
    @Produces(MediaType.APPLICATION_JSON)
    public Region getRegionInfo(@PathParam("region") long region) {
        return geoGeoLocationService.getRegionInfo(region);
    }

    public void setGeoLocationService(GeoLocationService geoLocationService) {
        this.geoGeoLocationService = geoLocationService;
    }
}
