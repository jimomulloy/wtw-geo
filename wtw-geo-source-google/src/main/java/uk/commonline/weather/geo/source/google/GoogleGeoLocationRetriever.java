package uk.commonline.weather.geo.source.google;

import java.io.InputStream;

import org.apache.log4j.Logger;

public class GoogleGeoLocationRetriever {

    private static Logger log = Logger.getLogger(GoogleGeoLocationRetriever.class);

    // private String yahooAppid =
    // "nHC8SgfV34FhrOczpZWEwOtNUU44RVqanKQjsAb2soBuM2LhXEt.gJVIkDzvU4sXvQ--";

    // private static String PLACEFINDER_URL =
    // "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20geo.placefinder%20where%20text%3D%22{lat}%2C{long}%22%20and%20gflags%3D%22R%22";

    public InputStream retrieveAllByType(String filter, String typeCode) throws Exception {
        return null;
    }

    public InputStream retrieveByCoords(double latitude, double longitude) throws Exception {
        return null;
    }

    public InputStream retrieveById(String id) throws Exception {
        return null;
    }

    public void setUrl(String url) {

    }

}
