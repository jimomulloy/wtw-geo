package uk.commonline.weather.geo.service;

import static org.junit.Assert.*;

import org.junit.Test;

public class GeoGridTest {

    @Test
    public void test() {
	GeoGrid geoGrid;
	geoGrid = new GeoGrid();
	System.out.println("geoGrid test");
	geoGrid.init(20);
	System.out.println("geoGrid regions:"+geoGrid.regions);
	long region1 = geoGrid.getRegion(51, 0);
	System.out.println("geoGrid Region(51, 0):"+geoGrid.getRegion(51, 0));
	long region = geoGrid.getRegion(0, 0);
	assertEquals("First region incorrect", 1, region);
	System.out.println("geoGrid First region correct");
	geoGrid = new GeoGrid();
	geoGrid.init(20);
	assertEquals("Region allocation inconsistent", region1, geoGrid.getRegion(51, 0));
	System.out.println("geoGrid Region allocation consistent");
    }

}
