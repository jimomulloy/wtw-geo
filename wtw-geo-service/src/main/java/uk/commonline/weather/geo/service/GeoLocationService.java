package uk.commonline.weather.geo.service;

import java.util.List;

import uk.commonline.weather.model.Location;
import uk.commonline.weather.model.Region;

public interface GeoLocationService {

    List<Location> findByType(String filter, String type);

    long getRegion(double latitude, double longitude);

    Region getRegionInfo(long region);

    String getLocationId(String source, double latitude, double longitude);

    Location getLocation(String source, String id);

}
