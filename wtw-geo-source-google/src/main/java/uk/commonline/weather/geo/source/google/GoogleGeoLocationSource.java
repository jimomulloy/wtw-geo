package uk.commonline.weather.geo.source.google;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import uk.commonline.weather.geo.source.GeoLocationSource;
import uk.commonline.weather.model.Location;

public class GoogleGeoLocationSource implements GeoLocationSource {

    @Inject
    private GoogleGeoLocationRetriever googleGeoLocationRetriever;

    @Inject
    private GoogleGeoLocationParser googleGeoLocationParser;

    @Override
    public List<Location> findByType(String filter, String typeCode) {
	List<Location> locations = new ArrayList<Location>();
	try {
	    // Retrieve Data
	    InputStream dataIn = googleGeoLocationRetriever.retrieveAllByType(filter, typeCode);

	    // Parse DataSet
	    locations = googleGeoLocationParser.parsePlaces(dataIn);

	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return locations;
    }

    @Override
    public String getSourceName() {
	return "google";
    }

    public GoogleGeoLocationParser getGoogleGeoLocationParser() {
	return googleGeoLocationParser;
    }

    public GoogleGeoLocationRetriever getGoogleGeoLocationRetriever() {
	return googleGeoLocationRetriever;
    }

    public void setGoogleGeoLocationParser(GoogleGeoLocationParser googleGeoLocationParser) {
	this.googleGeoLocationParser = googleGeoLocationParser;
    }

    public void setGoogleGeoLocationRetriever(GoogleGeoLocationRetriever googleGeoLocationRetriever) {
	this.googleGeoLocationRetriever = googleGeoLocationRetriever;
    }

    @Override
    public String getLocationId(double latitude, double longitude) {
	Location location = null;
	location = new Location();
	location.setType("Unknown");
	 location.setSourceid("Unknown");

	try {
	    // Retrieve Data
	    InputStream dataIn = googleGeoLocationRetriever.retrieveByCoords(latitude, longitude);

	    // Parse DataSet
	    if(dataIn != null){
		location = googleGeoLocationParser.parsePlace(dataIn);
	    }

	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return location.getSourceid();
    }

    @Override
    public Location getLocation(String id) {
	Location location = null;
	location = new Location();
	location.setType("Unknown");

	try {
	    // Retrieve Data
	    InputStream dataIn = googleGeoLocationRetriever.retrieveById(id);

	    // Parse DataSet
	    location = googleGeoLocationParser.parsePlace(dataIn);

	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return location;
    }

}