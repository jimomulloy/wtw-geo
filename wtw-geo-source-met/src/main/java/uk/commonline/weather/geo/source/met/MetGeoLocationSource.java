package uk.commonline.weather.geo.source.met;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import uk.commonline.weather.geo.service.GeoCache;
import uk.commonline.weather.geo.service.GeoCache.GeoLocation;
import uk.commonline.weather.geo.service.GeoCache.GeoSite;
import uk.commonline.weather.geo.source.GeoLocationSource;
import uk.commonline.weather.model.Location;

public class MetGeoLocationSource implements GeoLocationSource {

    private GeoCache geoCache = new GeoCache();

    @Inject
    private MetGeoLocationRetriever metGeoLocationRetriever;

    @Inject
    private MetGeoLocationParser metGeoLocationParser;

    @Override
    public List<Location> findByType(String filter, String typeCode) {
	List<Location> locations = new ArrayList<Location>();
	List<GeoLocation> gls = new ArrayList<GeoLocation>();
	Location location = null;
	location = new Location();
	location.setType("Unknown");

	try {
	    gls = geoCache.getLocations(filter, typeCode);
	    for (GeoLocation gl : gls) {
		location = new Location();
		location.setSourceid(gl.id);
		location.setName(gl.name);
		location.setLatitude(gl.latitude);
		location.setLongitude(gl.longitude);
		locations.add(location);
	    }

	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return locations;
    }

    public class Updater extends Thread {

	public void run() {
	    try {
		// Retrieve Data
		InputStream dataIn = MetGeoLocationSource.this.metGeoLocationRetriever.retrieveLocations();
		 if(dataIn == null){
	  		throw new Exception("metGeoLocationRetriever.retrieveLocations() null stream");
	  	    }
		// Parse DataSet
		System.out.println("!!Update run:");
		MetGeoLocationSource.this.metGeoLocationParser.parseLocations(dataIn, geoCache);

	    } catch (Exception ex) {
		ex.printStackTrace();
	    }
	}
    }

    @PostConstruct
    public void init() {
	Runnable r = new Updater();
	ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
	service.scheduleAtFixedRate(r, 0, 3, TimeUnit.HOURS);

    }

    @Override
    public String getSourceName() {
	return "met";
    }

    public MetGeoLocationParser getMetGeoLocationParser() {
	return metGeoLocationParser;
    }

    public MetGeoLocationRetriever getMetGeoLocationRetriever() {
	return metGeoLocationRetriever;
    }

    public void setMetGeoLocationParser(MetGeoLocationParser metGeoLocationParser) {
	this.metGeoLocationParser = metGeoLocationParser;
    }

    public void setMetGeoLocationRetriever(MetGeoLocationRetriever metGeoLocationRetriever) {
	this.metGeoLocationRetriever = metGeoLocationRetriever;
    }

    @Override
    public String getLocationId(double latitude, double longitude) {
	String locationId = "";
	try {
	    GeoSite site = geoCache.getNearest(latitude, longitude);
	    if(site == null || site.location == null){
		locationId = "3772";
	    }
	    else 
	    {
		locationId = site.location.id;

	    }

	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return locationId;
    }

    @Override
    public Location getLocation(String id) {
	Location location = null;
	location = new Location();
	location.setType("Unknown");
	GeoLocation gl = null;

	try {
	    gl = geoCache.getLocation(id);
	    location.setSourceid(gl.id);
	    location.setName(gl.name);
	    location.setLatitude(gl.latitude);
	    location.setLongitude(gl.longitude);

	} catch (Exception ex) {
	    ex.printStackTrace();
	}

	return location;
    }

}