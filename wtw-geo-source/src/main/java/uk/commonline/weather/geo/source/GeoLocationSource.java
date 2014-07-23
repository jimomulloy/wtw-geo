package uk.commonline.weather.geo.source;

import java.util.List;

import uk.commonline.weather.model.Location;
import uk.commonline.weather.model.Region;

public interface GeoLocationSource {
    	
    	List<Location> findByType(String filter, String type);

	String getLocationId(double latitude, double longitude);
	
	Location getLocation(String id);
	
	String getSourceName();
}
