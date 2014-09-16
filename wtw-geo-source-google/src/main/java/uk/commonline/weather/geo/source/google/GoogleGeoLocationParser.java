package uk.commonline.weather.geo.source.google;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import uk.commonline.weather.model.Location;

public class GoogleGeoLocationParser {

    private static Logger log = Logger.getLogger(GoogleGeoLocationParser.class);

    public Location parsePlace(InputStream inputStream) throws Exception {

        Location location = null;

        return location;
    }

    public List<Location> parsePlaces(InputStream inputStream) throws Exception {

        List<Location> locations = new ArrayList<Location>();

        return locations;
    }

}
