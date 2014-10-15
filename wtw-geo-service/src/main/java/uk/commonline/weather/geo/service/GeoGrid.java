package uk.commonline.weather.geo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import uk.commonline.weather.model.Region;

public class GeoGrid {

    TreeMap<Double, NavigableSet<Region>> grid = new TreeMap<Double, NavigableSet<Region>>();

    TreeMap<Long, Region> regionMap = new TreeMap<Long, Region>();

    List<Region> regionList = new ArrayList<Region>();

    double radius;

    long regions = 1;

    private void createGrid() {
        double circLat0 = 24901.55;
        double circLong = 24859.82;
        double circLat = circLat0;
        double latitude = 0;
        double deltaLat = 360.00 * ((Math.sqrt((radius * radius) - (radius * radius / 4))) / (circLong));
        circLat = circLat0;
        double deltaLong = 360.00 * (radius) / (circLat0);
        double longOffset = 0;
        while (latitude <= 90.00 & deltaLong > 0) {
            NavigableSet<Region> listLong = getListLong(latitude, deltaLong, longOffset);
            grid.put(latitude, listLong);
            latitude += deltaLat;
            circLat = circLat0 * Math.cos(Math.toRadians(latitude));
            deltaLong = 360.00 * (radius) / (circLat);
            if (longOffset > 0) {
                longOffset = 0;
            } else {
                longOffset = deltaLong / 2.0;
            }
        }
        latitude = -deltaLat;
        circLat = circLat0;
        deltaLong = 360.00 * (radius) / (circLat0);
        longOffset = deltaLong / 2.0;
        while (latitude >= -90.00 & deltaLong > 0) {
            NavigableSet<Region> listLong = getListLong(latitude, deltaLong, longOffset);
            grid.put(latitude, listLong);
            latitude -= deltaLat;
            circLat = circLat0 * Math.cos(Math.toRadians(latitude));
            deltaLong = 360.00 * (radius) / (circLat);
            if (longOffset > 0) {
                longOffset = 0;
            } else {
                longOffset = deltaLong / 2.0;
            }
        }
    }

    public Region getEntry(long region) {
        Region entry = regionMap.get(region);
        return entry;
    }

    private NavigableSet<Region> getListLong(double latitude, double deltaLong, double longOffset) {
        double longitude = 0;
        Region entry = null;
        Comparator<Region> c = new Comparator<Region>() {
            @Override
            public int compare(Region entry1, Region entry2) {
                if (entry1.longitude < entry2.longitude)
                    return -1;
                if (entry1.longitude > entry2.longitude)
                    return 1;
                return 0;
            }
        };
        NavigableSet<Region> listLong = new TreeSet<Region>(c);
        longitude = longOffset;
        while (longitude <= 180.00) {
            entry = new Region();
            entry.longitude = longitude;
            entry.latitude = latitude;
            entry.region = regions;
            listLong.add(entry);
            regionMap.put(regions, entry);
            regionList.add(entry);
            regions++;
            longitude += deltaLong;
        }
        longitude = -longOffset;
        while (longitude >= -180.00) {
            entry = new Region();
            entry.longitude = longitude;
            entry.latitude = latitude;
            entry.region = regions;
            listLong.add(entry);
            regionMap.put(regions, entry);
            regionList.add(entry);
            regions++;
            longitude -= deltaLong;
        }
        return listLong;
    }

    public double getRadius() {
        return radius;
    }

    public long getRegion(double latitude, double longitude) {
        NavigableSet<Region> floorEntry = grid.floorEntry(latitude).getValue();
        Region e = new Region();
        e.longitude = longitude;
        e = floorEntry.floor(e);
        return e.region;
    }

    public double getRegions() {
        return regions;
    }

    public Set<Region> getRegions(double swlatitude, double swlongitude, double nelatitude, double nelongitude) {
        Set<Region> regionSet = new HashSet<Region>();
        if (swlatitude >= nelatitude || swlongitude >= nelongitude) {
            return regionSet;
        }
        for (Region r : regionList) {
            if (r.longitude >= swlongitude && r.longitude <= nelongitude && r.latitude >= swlatitude && r.latitude <= nelatitude) {
                regionSet.add(r);
            }
        }
        return regionSet;
    }

    public void init(double radius) {
        this.radius = radius;
        createGrid();
    }
}