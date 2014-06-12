package uk.commonline.weather.geo.source;

import uk.commonline.weather.geo.service.GeoLocationService;

public interface GeoLocationSource {

	GeoLocationService getGeoLocationService();
	
	String getSourceName();
}
