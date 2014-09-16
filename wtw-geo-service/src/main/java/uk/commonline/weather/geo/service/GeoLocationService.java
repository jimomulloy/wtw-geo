package uk.commonline.weather.geo.service;

import java.util.List;
import java.util.Set;

import uk.commonline.weather.model.Location;
import uk.commonline.weather.model.Region;

public interface GeoLocationService {

    List<Location> findByType(String filter, String type);

    Location getLocation(String source, String id);

    String getLocationId(String source, double latitude, double longitude);

    long getRegion(double latitude, double longitude);

    Region getRegionInfo(long region);

    Set<Region> getRegions(double swlatitude, double swlongitude, double nelatitude, double nelongitude);

}
