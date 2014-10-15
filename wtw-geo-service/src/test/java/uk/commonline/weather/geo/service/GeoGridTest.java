package uk.commonline.weather.geo.service;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;

import uk.commonline.weather.model.Region;

public class GeoGridTest {

    @Test
    public void test() {
        GeoGrid geoGrid;
        geoGrid = new GeoGrid();
        geoGrid.init(20);
        System.out.println("geoGrid regions:" + geoGrid.regions);
        long region1 = geoGrid.getRegion(51, 0);
        System.out.println("geoGrid Region(51, 0):" + geoGrid.getRegion(51, 0));
        long region = geoGrid.getRegion(0, 0);
        assertEquals("First region incorrect", 1, region);
        geoGrid = new GeoGrid();
        geoGrid.init(20);
        assertEquals("Region allocation inconsistent", region1, geoGrid.getRegion(51, 0));
        System.out.println("geoGrid Region allocation consistent");
        Set<Region> regions = geoGrid.getRegions(51.0, 0.82, 52.0, 0.0);
        System.out.println("geoGrid Regions count:" + regions.size());
    }

}
