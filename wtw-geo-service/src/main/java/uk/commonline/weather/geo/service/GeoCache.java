package uk.commonline.weather.geo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class GeoCache {

    public class GeoLocation {

        public String id;
        public String name;
        public double latitude;
        public double longitude;

        public double lat;
        public double lon;
        public double sinlat;
        public double coslat;

        public GeoLocation(double latitude, double longitude) {
            this.lon = longitude * radian;
            this.coslat = Math.cos(this.lat);
            this.sinlat = Math.sin(this.lat);
            this.latitude = latitude;
            this.longitude = longitude;

            // private(ish)
            this.lat = latitude * radian;
            this.lon = longitude * radian;
            this.coslat = Math.cos(this.lat);
            this.sinlat = Math.sin(this.lat);
        }

        public GeoLocation(String id, String name, double latitude, double longitude) {
            this(latitude, longitude);
            this.id = id;
            this.name = name;
        }

        // return distance from this (standpoint) to forepoint in kilometres
        public double distance(GeoLocation forepoint) {
            // uses the Vincety formula to calculate the creat circle distance

            // difference - only used for this calculation so no point in
            // keeping
            double dlon = this.lon - forepoint.lon;
            double cosdlon = Math.cos(dlon);
            double sindlon = Math.sin(dlon);

            // central angle
            double dca = Math.atan2(
                    Math.sqrt(Math.pow(forepoint.coslat * sindlon, 2)
                            + Math.pow(this.coslat * forepoint.sinlat - this.sinlat * forepoint.coslat * cosdlon, 2)), this.sinlat * forepoint.sinlat
                            + this.coslat * forepoint.coslat * cosdlon);
            // distance is radius times central angle
            return (r * dca);
        }

        // return the rough distance from this (standpoint) to forepoint. no
        // specific distance, only relative to other points.
        // will return the correct closet point 90% of the time, otherwise
        // likely to be second closest
        public double roughDistance(GeoLocation forepoint) {

            return Math.pow(this.lat - forepoint.lat, 2) + Math.pow(this.lon - forepoint.lon, 2);

        }
    }

    public class GeoSite {
        public GeoLocation location;
        public double dist;

        GeoSite(double dist, GeoLocation location) {
            this.dist = dist;
            this.location = location;
        }
    }

    List<GeoLocation> forepoints = Collections.synchronizedList(new ArrayList<GeoLocation>());

    public static double r = 6371.01;

    public static double radian = Math.PI / 180;

    final ReentrantLock rl = new ReentrantLock();

    public GeoCache() {
    }

    public void addForepoint(String id, String name, double latitude, double longitude) {
        forepoints.add(new GeoLocation(id, name, latitude, longitude));
    }

    /*
     * return the Location object in forepointList that is closest to standpoint
     * 
     * [ 'dist': distance, 'location': Location ]
     */
    public GeoLocation getLocation(String id) {
        rl.lock();
        try {
            for (GeoLocation forepoint : forepoints) { // all of the sites
                if (forepoint.id.equalsIgnoreCase(id)) {
                    // n attempts to find where to insert this location in to
                    // the
                    return forepoint;
                }
            }
        } finally {
            rl.unlock();
        }
        return null;

    }

    public List<GeoLocation> getLocations(String filter, String typeCode) {
        List<GeoLocation> ls = new ArrayList<GeoLocation>();
        rl.lock();
        try {
            for (GeoLocation forepoint : forepoints) { // all of the sites
                if (forepoint.name.matches(filter)) {
                    // n attempts to find where to insert this location in to
                    // the
                    ls.add(forepoint);
                }
            }
        } finally {
            rl.unlock();
        }
        return ls;

    }

    /*
     * return the Location object in forepointList that is closest to standpoint
     * 
     * [ 'dist': distance, 'location': Location ]
     */
    public GeoSite getNearest(double latitude, double longitude) {
        return getNearest(getStandpoint(latitude, longitude));

    }

    /*
     * return the Location object in forepointList that is closest to standpoint
     * 
     * [ 'dist': distance, 'location': Location ]
     */
    public GeoSite getNearest(GeoLocation standpoint) {
        int i;
        GeoSite site = new GeoSite(Double.MAX_VALUE, null);
        double newDist;
        rl.lock();
        try {
            for (GeoLocation forepoint : forepoints) { // all of the sites
                newDist = standpoint.distance(forepoint);
                if (newDist < site.dist) { // is closer than we have seen before
                    site = new GeoSite(newDist, forepoint);
                }
            }
        } finally {
            rl.unlock();
        }
        return site;

    };

    /*
     * return an array of distances and Location objects n long that are closest
     * to standpoint [ [ 'dist': distance, 'location': Location ],... }
     */
    public List<GeoSite> getNNearest(GeoLocation standpoint, List<GeoLocation> forepointList, int n) {
        LinkedList<GeoSite> siteList = new LinkedList<GeoSite>();
        double newDist;
        int i = 0, len, j = 0;

        for (GeoLocation forepoint : forepointList) { // all of the sites

            // get the distance to the site
            newDist = standpoint.distance(forepoint);

            // large performance gains made by sorting sites as they are found

            // we don't have a full list or is closer than the nth distance
            if (siteList.size() < n || (newDist < siteList.get(n).dist)) {
                // n attempts to find where to insert this location in to the
                // found list
                for (j = 0; j < n; j++) {
                    if (siteList.size() < j || newDist < siteList.get(j).dist) {
                        siteList.add(j, new GeoSite(newDist, forepoint));
                        break;
                    } // endif
                } // endfor
            }
        }
        // just return the portion we are interested in
        ArrayList<GeoSite> sites = new ArrayList<GeoSite>();
        for (i = 0; i < siteList.size() || i < n; i++) {
            sites.add(siteList.get(i));
        }
        return sites;
    };

    public GeoLocation getStandpoint(double latitude, double longitude) {
        return new GeoLocation(latitude, longitude);
    };

    public void load(List<GeoLocation> forepoints) {
        rl.lock();
        try {
            this.forepoints.clear();
            for (GeoLocation forepoint : forepoints) {
                this.forepoints.add(forepoint);
            }
        } finally {
            rl.unlock();
        }
    };

}