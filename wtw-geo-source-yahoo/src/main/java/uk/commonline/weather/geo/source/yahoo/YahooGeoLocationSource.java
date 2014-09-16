package uk.commonline.weather.geo.source.yahoo;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import uk.commonline.weather.geo.source.GeoLocationSource;
import uk.commonline.weather.model.Location;

public class YahooGeoLocationSource implements GeoLocationSource {

    @Inject
    private YahooGeoLocationRetriever yahooGeoLocationRetriever;

    @Inject
    private YahooGeoLocationParser yahooGeoLocationParser;

    @Override
    public List<Location> findByType(String filter, String typeCode) {
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
    public Location getLocation(String id) {
        Location location = null;
        location = new Location();
        location.setType("Unknown");

        try {
            // Retrieve Data
            InputStream dataIn = yahooGeoLocationRetriever.retrieveById(id);

            // Parse DataSet
            location = yahooGeoLocationParser.parsePlace(dataIn);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return location;
    }

    @Override
    public String getLocationId(double latitude, double longitude) {
        Location location = null;
        location = new Location();
        location.setType("Unknown");

        try {
            // Retrieve Data
            InputStream dataIn = yahooGeoLocationRetriever.retrieveByCoords(latitude, longitude);

            // Parse DataSet
            location = yahooGeoLocationParser.parsePlaceQuery(dataIn);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return location.getSourceid();
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