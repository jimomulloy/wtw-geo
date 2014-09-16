package uk.commonline.weather.geo.source.met;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import uk.commonline.weather.geo.service.GeoCache;
import uk.commonline.weather.geo.service.GeoCache.GeoLocation;

public class MetGeoLocationParser implements ErrorHandler {

    private static Logger log = Logger.getLogger(MetGeoLocationParser.class);

    @Override
    public void error(SAXParseException spe) {
        System.out.println("Error at line " + spe.getLineNumber());
        System.out.println(spe.getMessage());
    }

    @Override
    public void fatalError(SAXParseException spe) throws SAXException {
        System.out.println("Fatal error at line " + spe.getLineNumber());
        System.out.println(spe.getMessage());
        throw spe;
    }

    public void parseLocations(InputStream inputStream, GeoCache geoCache) throws Exception {

        // Process response
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        // factory.setValidating(true);
        DocumentBuilder builder;
        Document response = null;
        builder = factory.newDocumentBuilder();
        builder.setErrorHandler(this);
        response = builder.parse(inputStream);

        // create an XPathFactory
        XPathFactory xFactory = XPathFactory.newInstance();

        // create an XPath object
        XPath xPath = xFactory.newXPath();

        // normalize text representation
        response.getDocumentElement().normalize();
        System.out.println("!!Root element of the doc is " + response.getDocumentElement().getNodeName() + ", doc:"
                + response.getDocumentElement().toString());

        // Get all search Result nodes
        NodeList nodes = (NodeList) xPath.compile("//Location").evaluate(response, XPathConstants.NODESET);
        // Get all search Result nodes
        int nodeCount = nodes.getLength();
        List<GeoLocation> forepoints = new ArrayList<GeoLocation>();
        // iterate over search Result nodes
        for (int i = 0; i < nodeCount; i++) {
            // Get each xpath expression as a string
            String id = (String) xPath.evaluate("@id", nodes.item(i), XPathConstants.STRING);
            // String id2 =
            // nodes.item(i).getAttributes().getNamedItem("id").getNodeValue();
            String name = (String) xPath.evaluate("@name", nodes.item(i), XPathConstants.STRING);
            String latitude = (String) xPath.evaluate("@latitude", nodes.item(i), XPathConstants.STRING);
            String longitude = (String) xPath.evaluate("@longitude", nodes.item(i), XPathConstants.STRING);
            double lat = 0, lon = 0;
            try {
                lat = Double.parseDouble(latitude);
                lon = Double.parseDouble(longitude);
            } catch (Exception ex) {
                lat = 0;
                lon = 0;
            }
            forepoints.add(geoCache.new GeoLocation(id, name, lat, lon));
        }
        geoCache.load(forepoints);
    }

    @Override
    public void warning(SAXParseException spe) {
        System.out.println("Warning at line " + spe.getLineNumber());
        System.out.println(spe.getMessage());
    }

}
