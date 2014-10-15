package uk.commonline.weather.geo.service;

import java.util.List;
import java.util.Set;

import uk.commonline.weather.model.Location;
import uk.commonline.weather.model.Region;

/**
 * @author Jim O'Mulloy
 *
 *         WTW Geo Location service API
 * 
 */
public interface GeoLocationService {

    /**
     * Find locations by filter
     * 
     * @param filter
     * @param type
     * @return
     */
    List<Location> findByType(String filter, String type);

    /**
     * Get Location by id for given source
     * 
     * @param source
     * @param id
     * @return
     */
    Location getLocation(String source, String id);

    /**
     * Get id of Location for given source at latitiude, longitude
     * 
     * @param source
     * @param latitude
     * @param longitude
     * @return
     */
    String getLocationId(String source, double latitude, double longitude);

    /**
     * Get region id for given latitude, longitude
     * 
     * @param latitude
     * @param longitude
     * @return
     */
    long getRegion(double latitude, double longitude);

    /**
     * Get Region Info for given Region id
     * 
     * @param region
     * @return
     */
    Region getRegionInfo(long region);

    /**
     * Get list of Regions within given sw/ne latitude, longitude bounds
     * 
     * @param swlatitude
     * @param swlongitude
     * @param nelatitude
     * @param nelongitude
     * @return
     */
    Set<Region> getRegions(double swlatitude, double swlongitude, double nelatitude, double nelongitude);

}
