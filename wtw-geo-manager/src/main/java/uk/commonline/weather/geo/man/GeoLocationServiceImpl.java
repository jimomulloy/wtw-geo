package uk.commonline.weather.geo.man;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import uk.commonline.weather.geo.service.GeoGrid;
import uk.commonline.weather.geo.service.GeoLocationService;
import uk.commonline.weather.geo.source.GeoLocationSource;
import uk.commonline.weather.model.Location;
import uk.commonline.weather.model.Region;

public class GeoLocationServiceImpl implements GeoLocationService {

    @Inject
    List<GeoLocationSource> geoLocationSources;

    private GeoGrid geoGrid;

    GeoLocationServiceImpl() {
        geoGrid = new GeoGrid();
        geoGrid.init(20);
    }

    @Override
    public List<Location> findByType(String filter, String type) {
        List<Location> ls = new ArrayList<Location>();
        for (GeoLocationSource geoLocationSource : geoLocationSources) {
            ls = geoLocationSource.findByType(filter, type);
        }
        return ls;
    }

    protected List<GeoLocationSource> getGeoLocationSources() {
        return geoLocationSources;
    }

    @Override
    public Location getLocation(String source, String id) {
        for (GeoLocationSource geoLocationSource : geoLocationSources) {
            if (geoLocationSource.getSourceName().equalsIgnoreCase(source)) {
                return geoLocationSource.getLocation(id);
            }
        }
        return null;
    }

    @Override
    public String getLocationId(String source, double latitude, double longitude) {
        for (GeoLocationSource geoLocationSource : geoLocationSources) {
            if (geoLocationSource.getSourceName().equalsIgnoreCase(source)) {
                return geoLocationSource.getLocationId(latitude, longitude);
            }
        }
        return "";
    }

    @Override
    public long getRegion(double latitude, double longitude) {
        return geoGrid.getRegion(latitude, longitude);
    }

    @Override
    public Region getRegionInfo(long region) {
        return geoGrid.getEntry(region);
    }

    @Override
    public Set<Region> getRegions(double swlatitude, double swlongitude, double nelatitude, double nelongitude) {
        return geoGrid.getRegions(swlatitude, swlongitude, nelatitude, nelongitude);
    }

    public void setGeoLocationSources(List<GeoLocationSource> geoLocationSources) {
        this.geoLocationSources = geoLocationSources;
    }

}