package uk.commonline.weather.geo.source;

import java.util.List;

import uk.commonline.weather.model.Location;

public interface GeoLocationSource {

    List<Location> findByType(String filter, String type);

    Location getLocation(String id);

    String getLocationId(double latitude, double longitude);

    String getSourceName();
}
