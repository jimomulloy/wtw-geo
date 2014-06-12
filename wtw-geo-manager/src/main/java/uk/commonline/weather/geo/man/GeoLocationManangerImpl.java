package uk.commonline.weather.geo.man;

import java.util.List;

import uk.commonline.weather.geo.service.GeoLocationManager;
import uk.commonline.weather.geo.source.GeoLocationSource;
import uk.commonline.weather.model.Location;

public class GeoLocationManangerImpl implements GeoLocationManager {

    // @Autowired
    List<GeoLocationSource> geoLocationSources;

    protected List<GeoLocationSource> getGeoLocationSources() {
	return geoLocationSources;
    }

    public void setGeoLocationSources(List<GeoLocationSource> geoLocationSources) {
	this.geoLocationSources = geoLocationSources;
    }

    @Override
    public Location findByZip(final String zip) {
	Location l = geoLocationSources.get(0).getGeoLocationService().findByZip(zip);
	if (l == null) {
	    l = new Location();
	    l.setCity("London");
	}
	return l;
    }

    @Override
    public Location findByTown(String town) {
	Location l = geoLocationSources.get(0).getGeoLocationService().findByTown(town);
	if (l == null) {
	    l = new Location();
	    l.setCity("London");
	}
	return l;
    }

    @Override
    public List<Location> findAllByType(String filter, String type) {
	List<Location> ls = geoLocationSources.get(0).getGeoLocationService().findAllByType(filter, type);
	return ls;
    }

    @Override
    public Location findByWoeid(String woied) {
	Location l = geoLocationSources.get(0).getGeoLocationService().findByWoeid(woied);
	if (l == null) {
	    l = new Location();
	    l.setCity("London");
	}
	return l;
    }
}