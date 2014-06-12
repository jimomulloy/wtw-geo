package uk.commonline.weather.geo.source.yahoo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import uk.commonline.weather.geo.service.GeoLocationService;
import uk.commonline.weather.geo.source.GeoLocationSource;
import uk.commonline.weather.model.Location;

public class YahooGeoLocationSource implements GeoLocationSource, GeoLocationService {

    @Autowired
    private YahooGeoLocationRetriever yahooGeoLocationRetriever;

    @Autowired
    private YahooGeoLocationParser yahooGeoLocationParser;

    @Override
    public List<Location> findAllByType(String filter, String typeCode) {
	List<Location> locations = new ArrayList<Location>();
	try {
	    // Retrieve Data
	    InputStream dataIn = yahooGeoLocationRetriever.retrieveAllByType(filter, typeCode);

	    // Parse DataSet
	    locations = yahooGeoLocationParser.parsePlaces(dataIn);

	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return locations;
    }

    @Override
    public Location findByTown(String town) {
	Location location = null;
	location = new Location();
	location.setCity("Unknown");

	try {
	    // Retrieve Data
	    InputStream dataIn = yahooGeoLocationRetriever.retrieveByTown(town);

	    // Parse DataSet
	    List<Location> locations = yahooGeoLocationParser.parsePlaces(dataIn);

	    if (locations.size() > 0) {
		location = locations.get(0);
	    }

	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return location;
    }

    @Override
    public Location findByWoeid(String woeid) {
	Location location = null;
	location = new Location();
	location.setCity("Unknown");

	try {
	    // Retrieve Data
	    InputStream dataIn = yahooGeoLocationRetriever.retrieveByWoeid(woeid);

	    // Parse DataSet
	    location = yahooGeoLocationParser.parsePlace(dataIn);

	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return location;
    }

    public Location findByZip(final String zip) {

	Location location = null;
	location = new Location();
	location.setCity("Unknown");

	try {
	    // Retrieve Data
	    InputStream dataIn = yahooGeoLocationRetriever.retrieveByZip(zip);

	    // Parse DataSet
	    List<Location> locations = yahooGeoLocationParser.parsePlaces(dataIn);

	    if (locations.size() > 0) {
		location = locations.get(0);
	    }

	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return location;
    }

    @Override
    public GeoLocationService getGeoLocationService() {
	return this;
    }

    @Override
    public String getSourceName() {
	return "yahoo";
    }

    public YahooGeoLocationParser getYahooGeoLocationParser() {
	return yahooGeoLocationParser;
    }

    public YahooGeoLocationRetriever getYahooGeoLocationRetriever() {
	return yahooGeoLocationRetriever;
    }

    public void setYahooGeoLocationParser(YahooGeoLocationParser yahooGeoLocationParser) {
	this.yahooGeoLocationParser = yahooGeoLocationParser;
    }

    public void setYahooGeoLocationRetriever(YahooGeoLocationRetriever yahooGeoLocationRetriever) {
	this.yahooGeoLocationRetriever = yahooGeoLocationRetriever;
    }

}