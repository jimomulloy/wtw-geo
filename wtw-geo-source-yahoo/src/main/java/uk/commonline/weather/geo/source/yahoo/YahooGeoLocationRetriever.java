package uk.commonline.weather.geo.source.yahoo;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

public class YahooGeoLocationRetriever {

    private static Logger log = Logger.getLogger(YahooGeoLocationRetriever.class);

    private String yahooAppid = "nHC8SgfV34FhrOczpZWEwOtNUU44RVqanKQjsAb2soBuM2LhXEt.gJVIkDzvU4sXvQ--";
    
    private static String PLACEFINDER_URL = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20geo.placefinder%20where%20text%3D%22{lat}%2C{long}%22%20and%20gflags%3D%22R%22";
    	
    public InputStream retrieveByZip(String zipcode) throws Exception {
	log.info("Retrieving Weather Data");
	String url = "http://where.yahooapis.com/v1/places$and(.q('" + zipcode + "'),.type(7));count=1?appid=" + yahooAppid;
	URLConnection conn = new URL(url).openConnection();
	return conn.getInputStream();
    }

    public InputStream retrieveByTown(String town) throws Exception {
	log.info("Retrieving Weather Data");
	String url = "http://where.yahooapis.com/v1/places$and(.q('" + town + "'),.type(7));count=1?appid=" + yahooAppid;
	URLConnection conn = new URL(url).openConnection();
	return conn.getInputStream();
    }

    public InputStream retrieveByCoords(double latitude, double longitude) throws Exception {
	String url = (PLACEFINDER_URL.replaceAll("\\{lat\\}", Double.toString(latitude))).replaceAll("\\{long\\}", Double.toString(longitude));
	URLConnection conn = new URL(url).openConnection();
	return conn.getInputStream();
    }

    public InputStream retrieveAllByType(String filter, String typeCode) throws Exception {
	log.info("Retrieving Weather Data");
	String url = "http://where.yahooapis.com/v1/places$and(.q('" + filter + "'),.type(" + typeCode + "));count=0?appid=" + yahooAppid;
	URLConnection conn = new URL(url).openConnection();
	return conn.getInputStream();
    }

    public InputStream retrieve(String town) throws Exception {
	log.info("Retrieving Weather Data");
	String url = "http://where.yahooapis.com/v1/places$and(.q('" + town + "%2A'),.type(7));count=1?appid=" + yahooAppid;
	URLConnection conn = new URL(url).openConnection();
	return conn.getInputStream();
    }

    public InputStream retrieveById(String id) throws Exception {
	log.info("Retrieving Weather Data");
	String url = "http://where.yahooapis.com/v1/place/" + id + "?appid=" + yahooAppid;
	URLConnection conn = new URL(url).openConnection();
	return conn.getInputStream();
    }

    public void setUrl(String url) {

    }

}
