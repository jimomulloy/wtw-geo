package uk.commonline.weather.geo.source.met;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

public class MetGeoLocationRetriever {

    private static Logger log = Logger.getLogger(MetGeoLocationRetriever.class);

    private String metAppid = "b6bb5fb9-3846-46c1-8f3c-2e2ecd9c790e";
    private static String LOCATIONS_URL = "http://datapoint.metoffice.gov.uk/public/data/val/wxobs/all/xml/sitelist?key=";
    	
    public InputStream retrieveLocations() throws Exception {
	String url = LOCATIONS_URL + metAppid;
	URLConnection conn = new URL(url).openConnection();
	return conn.getInputStream();
    }

    public void setUrl(String url) {

    }

}
