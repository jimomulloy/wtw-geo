package uk.commonline.weather.geo.source;

import java.util.List;

import uk.commonline.weather.model.Location;

/**
 * @author Jim O'Mulloy
 * 
 * WTW Geo location data source API
 *
 */
public interface GeoLocationSource {

    /**
     * Find Locations by filter
     * 
     * @param filter
     * @param type
     * @return
     */
    List<Location> findByType(String filter, String type);

    /**
     * Get Location info for given id
     * 
     * @param id
     * @return
     */
    Location getLocation(String id);

    /**
     * Get Location id for given latitude, longitude
     * 
     * @param latitude
     * @param longitude
     * @return
     */
    String getLocationId(double latitude, double longitude);

    /**
     * @return name of source
     */
    String getSourceName();
}
