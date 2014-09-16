package uk.commonline.weather.geo.client.jaxrs;

import java.util.List;
import java.util.Set;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import uk.commonline.data.client.jaxrs.AbstractCrudClient;
import uk.commonline.weather.geo.service.GeoLocationService;
import uk.commonline.weather.model.Location;
import uk.commonline.weather.model.Region;

/**
 */
public class GeoLocationClient extends AbstractCrudClient<Location> implements GeoLocationService {

    @Override
    public List<Location> findByType(String filter, String type) {
        GenericType<List<Location>> list = new GenericType<List<Location>>() {
        };
        List<Location> locations = getRestClient().getClient().target(getRestClient().createUrl("http://localhost:8080/wtwgeo/webresources/"))
                .path(getPath()).path("filter/{filter}/typecode/{typecode}").resolveTemplate("filter", filter).request(MediaType.APPLICATION_JSON)
                .get(list);
        return locations;
    }

    @Override
    public Location getLocation(String source, String id) {
        Location location = getRestClient().getClient().target(getRestClient().createUrl("http://localhost:8080/wtwgeo/webresources/"))
                .path(getPath()).path("source/{source}/id/{id}").resolveTemplate("id", id).request(MediaType.APPLICATION_JSON).get(Location.class);
        if (location == null) {
            location = newCiInstance();
        }
        return location;
    }

    @Override
    public String getLocationId(String source, double latitude, double longitude) {
        String id = getRestClient().getClient().target(getRestClient().createUrl("http://localhost:8080/wtwgeo/webresources/")).path(getPath())
                .path("locate/source/{source}/lat/{lat}/long/{long}").resolveTemplate("source", source).resolveTemplate("lat", latitude)
                .resolveTemplate("long", longitude).request(MediaType.APPLICATION_JSON).get(String.class);
        return id;
    }

    @Override
    protected String getPath() {
        return "location";
    }

    @Override
    public long getRegion(double latitude, double longitude) {
        long region = getRestClient().getClient().target(getRestClient().createUrl("http://localhost:8080/wtwgeo/webresources/")).path(getPath())
                .path("region/lat/{lat}/long/{long}").resolveTemplate("lat", latitude).resolveTemplate("long", longitude)
                .request(MediaType.APPLICATION_JSON).get(Long.class);
        return region;
    }

    @Override
    public Region getRegionInfo(long region) {
        Region entry = getRestClient().getClient().target(getRestClient().createUrl("http://localhost:8080/wtwgeo/webresources/")).path(getPath())
                .path("regioninfo/{region}").resolveTemplate("region", region).request(MediaType.APPLICATION_JSON).get(Region.class);
        return entry;
    }

    @Override
    public Set<Region> getRegions(double swlatitude, double swlongitude, double nelatitude, double nelongitude) {
        GenericType<Set<Region>> set = new GenericType<Set<Region>>() {
        };
        Set<Region> regions = getRestClient().getClient().target(getRestClient().createUrl("http://localhost:8080/wtwgeo/webresources/"))
                .path(getPath()).path("regions/swlat/{swlat}/swlong/{swlong}/nelat/{nelat}/nelong/{nelong}").resolveTemplate("swlat", swlatitude)
                .resolveTemplate("swlong", swlongitude).resolveTemplate("nelat", nelatitude).resolveTemplate("nelong", nelongitude)
                .request(MediaType.APPLICATION_JSON).get(set);
        return regions;
    }

}
