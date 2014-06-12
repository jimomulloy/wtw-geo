package uk.commonline.weather.geo.client.jaxrs;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import uk.commonline.data.client.jaxrs.AbstractCrudClient;
import uk.commonline.weather.geo.service.GeoLocationService;
import uk.commonline.weather.model.Location;
import uk.commonline.weather.model.Weather;

/**
 */
public class GeoLocationClient extends AbstractCrudClient<Location> implements GeoLocationService {

    protected String getPath() {
	return "location";
    }

    public Location findByZip(final String zip) {
	Location location = getRestClient().getClient().target(getRestClient().createUrl("http://localhost:8080/wtwgeo/webresources/"))
		.path(getPath()).path("zip/{zip}").resolveTemplate("zip", zip).request(MediaType.APPLICATION_JSON).get(Location.class);
	if (location == null) {
	    location = newCiInstance();
	}
	return location;
    }

    @Override
    public Location findByTown(String town) {
	Location location = getRestClient().getClient().target(getRestClient().createUrl("http://localhost:8080/wtwgeo/webresources/"))
		.path(getPath()).path("town/{town}").resolveTemplate("town", town).request(MediaType.APPLICATION_JSON).get(Location.class);
	if (location == null) {
	    location = newCiInstance();
	}
	return location;
    }

    @Override
    public List<Location> findAllByType(String filter, String type) {
	GenericType<List<Location>> list = new GenericType<List<Location>>() {
	};
	List<Location> locations = getRestClient().getClient().target(getRestClient().createUrl("http://localhost:8080/wtwgeo/webresources/"))
		.path(getPath()).path("filter/{filter}/typecode/{typecode}").resolveTemplate("filter", filter).resolveTemplate("filter", filter)
		.request(MediaType.APPLICATION_JSON).get(list);
	return locations;
    }

    @Override
    public Location findByWoeid(String woeid) {
	Location location = getRestClient().getClient().target(getRestClient().createUrl("http://localhost:8080/wtwgeo/webresources/"))
		.path(getPath()).path("woied/{woeid}").resolveTemplate("woeid", woeid).request(MediaType.APPLICATION_JSON).get(Location.class);
	if (location == null) {
	    location = newCiInstance();
	}
	return location;
    }
}
