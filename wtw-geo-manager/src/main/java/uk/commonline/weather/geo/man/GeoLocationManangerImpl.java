package uk.commonline.weather.geo.man;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import uk.commonline.weather.geo.service.GeoGrid;
import uk.commonline.weather.geo.service.GeoLocationManager;
import uk.commonline.weather.geo.source.GeoLocationSource;
import uk.commonline.weather.model.Location;
import uk.commonline.weather.model.Region;

public class GeoLocationManangerImpl implements GeoLocationManager {

    @Inject
    List<GeoLocationSource> geoLocationSources;

    private GeoGrid geoGrid;

    protected List<GeoLocationSource> getGeoLocationSources() {
	return geoLocationSources;
    }

    public void setGeoLocationSources(List<GeoLocationSource> geoLocationSources) {
	this.geoLocationSources = geoLocationSources;
    }

    GeoLocationManangerImpl() {
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
    public long getRegion(double latitude, double longitude) {
	return geoGrid.getRegion(latitude, longitude);
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
    public Region getRegionInfo(long region) {
	return geoGrid.getEntry(region);
    }

}