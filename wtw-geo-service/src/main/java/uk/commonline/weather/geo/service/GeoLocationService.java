package uk.commonline.weather.geo.service;

import java.util.List;

import uk.commonline.weather.model.Location;

public interface GeoLocationService {

	Location findByZip(String zip);

	Location findByTown(String town);
	
	List<Location> findAllByType(String filter, String type);
	
	Location findByWoeid(String woeid);
}
